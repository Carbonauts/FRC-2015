package org.usfirst.frc.team1829.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Cruisable;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import com.team1829.library.CarbonAnalogInput;
import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem that grabs CONTAINERS and places them on the
 * conveyer, as well as takes them off of the conveyer and
 * deposits them on a tote.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Jaw extends Subsystem implements Diagnosable, Cruisable
{	
	/**
	 * Motor that pushes and retracts the Jaw linkage
	 * to compress the CONTAINER.
	 */
	private Talon motor;
	
	private CarbonDigitalInput extentLimit;
	
	private CarbonDigitalInput retractLimit;
	
	/**
	 * Senses how far extended or retracted the jaw is.
	 */
	private CarbonAnalogInput extentSensor;
	
	/**
	 * Senses how far away the jaw is from the next container.
	 */
	private CarbonAnalogInput containerSensor;
	
	/**
	 * Interfaces between the PIDController and this subsystem.
	 */
	private JawPIDAdapter pidAdapter;
	
	/**
	 * PID Control loop for this subsystem.
	 */
	private PIDController pidController;
	
	private double p = 1.0/1000.0;
	private double i = 0.0;
	private double d = 0.0;
	
	/**
	 * Stores the name of the last action taken
	 * by this subsystem.
	 */
	private String lastOperation = "";
	
	/**
	 * Whether PID is currently active in this subsystem.
	 */
	private boolean pidEnabled = false;
	
	/**
	 * The speed to resort to when operating in non-PID
	 * conditions.
	 */
	private double cruiseSpeed = 0.4; //TODO Find appropriate value for default.
	
	/**
	 * Constructor
	 */
	public Jaw()
	{
		super("Jaw");
		motor = new Talon(Robot.JAW_MOTOR);
		pidAdapter = new JawPIDAdapter();
		pidController = new PIDController(p, i, d, pidAdapter, pidAdapter);
		extentLimit = new CarbonDigitalInput(Robot.JAW_LIMIT_EXTENT);
		retractLimit = new CarbonDigitalInput(Robot.JAW_LIMIT_RETRACT);
		extentSensor = new CarbonAnalogInput(Robot.JAW_DISTANCE_EXTENT, CarbonAnalogInput.SmoothingMode.MEDIAN, 16, 10);
		containerSensor = new CarbonAnalogInput(Robot.JAW_DISTANCE_APPROACH, CarbonAnalogInput.SmoothingMode.MEDIAN, 8, 20);
		
		pidController.setOutputRange(-0.3, 0.3);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Actuates the jaw at the given speed.
	 * @param speed
	 */
	public void move(double speed)
	{
		speed = (speed > 1.0) ? 1.0 : speed; //Speed top cap
		speed = (speed < -1.0) ? -1.0 : speed; //Speed bottom cap
		
		speed = Robot.JAW_INVERTED ? -speed : speed;
		
		if(speed < 0) //Extending
		{
			if(isFullyExtended()) //Safety for extending.
			{
				speed = 0.0;
			}
		}
		else if(speed > 0) //Retracting
		{
			if(isFullyRetracted()) //Safety for retracting.
			{
				speed = 0.0;
			}
		}
		
		motor.set(speed);
		lastOperation = "moveJaw(" + speed + ")";
	}
	
	/**
	 * Pulls the container onto the conveyer.
	 */
	public void retract()
	{
		if(!isFullyRetracted())
		{
			move(getCruiseSpeed());
			lastOperation = "retract()";
		}
		else
		{
			stop();
		}
	}
	
	/**
	 * Moves the jaw toward its fully extended position.
	 */
	public void extend()
	{
		if(!isFullyExtended())
		{
			move(-getCruiseSpeed());
			lastOperation = "extend()";
		}
		else
		{
			stop();
		}
	}
	
	/**
	 * Stops the jaw motor and kills PID.
	 */
	public void stop()
	{
		setPIDEnabled(false);
		motor.stopMotor();
	}
	
	/**
	 * Returns true if the jaw is fully extended.
	 * @return True if the jaw is fully extended.
	 */
	public boolean isFullyExtended()
	{
		return !extentLimit.get();
	}
	
	/**
	 * Returns true if the jaw is fully retracted.
	 * @return True if the jaw is fully retracted.
	 */
	public boolean isFullyRetracted()
	{
		return !retractLimit.get();
	}
	
	/**
	 * Gets the analog value of the jaw extent,
	 * indicating how extended the jaw is.
	 * @return
	 */
	public double getExtent()
	{
		return extentSensor.getMedianSmoothedValue();
	}
	
	/**
	 * Returns the distance from the jaw to the next container.
	 * @return The distance from the jaw to the next container.
	 */
	public double getContainerDistance()
	{
		return containerSensor.getMedianSmoothedValue();
	}
	
	/**
	 * Sets the PID setpoint for use with PID mode.
	 * @param setpoint The PID setpoint.
	 */
	public void setSetpoint(double setpoint)
	{
		pidController.setSetpoint(setpoint);
	}
	
	/**
	 * Sets the PID to enabled or disabled.
	 * @param enabled Whether to enable or disable.
	 */
	public void setPIDEnabled(boolean enabled)
	{
		/*
		 * If the subsystem PID setting is already at
		 * whatever this method call is ordering, then
		 * ignore it.
		 */
		if(pidEnabled == enabled)
		{
			return;
		}

		pidController.reset();
		pidEnabled = enabled;		
		if(pidEnabled)
		{
			pidController.enable();
		}
		else
		{
			motor.stopMotor();
		}
		lastOperation = "setPIDEnabled(" + enabled + ")";
	}
	
	/**
	 * Interfaces between the PIDController and the Subsystem.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class JawPIDAdapter implements PIDSource, PIDOutput
	{
		public JawPIDAdapter()
		{
			
		}

		public void pidWrite(double output) 
		{
			if(pidEnabled)
			{
				move(output);
			}
		}

		public double pidGet() 
		{	
			return getExtent();
		}
	}

	/**
	 * Sets the speed for use with retract() and extend().  More
	 * precise control can be achieved with moveJaw(double speed).
	 */
	public void setCruiseSpeed(double speed) 
	{
		speed = (speed > 0) ? speed : -speed;
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		this.cruiseSpeed = speed;
	}

	/**
	 * Gets the speed to be used in retract() and extend().
	 */
	public double getCruiseSpeed() 
	{	
		return this.cruiseSpeed;
	}

	/**
	 * Diagnostic command to retrieve a status.
	 */
	public String getStatus() 
	{	
		DecimalFormat formatter = new DecimalFormat("000.00");
		StringBuffer response = new StringBuffer("[" + getName() + "]");
		response.append(" Extent: ").append(formatter.format(getExtent()));
		response.append(" Approach: ").append(formatter.format(getContainerDistance()));
		response.append(" FullExt:").append(isFullyExtended() ? "T" : "F");
		response.append(" FullRet:").append(isFullyRetracted() ? "T" : "F");
		return response.toString();
	}

	/**
	 * Displays critical statuses on the Dashboard.
	 */
	public void updateSmartDS() 
	{
		SmartDashboard.putNumber("Extent Sensor", getExtent());
		SmartDashboard.putNumber("Extent Graph", getExtent());
		SmartDashboard.putNumber("Container Sensor", getContainerDistance());
		SmartDashboard.putNumber("Container Graph", getContainerDistance());
		SmartDashboard.putBoolean("Jaw Extended", isFullyExtended());
		SmartDashboard.putBoolean("Jaw Retracted", isFullyRetracted());
		SmartDashboard.putString("Jaw Last Operation", lastOperation());
	}

	/**
	 * Tells the last action this subsystem did.
	 */
	public String lastOperation() 
	{	
		return lastOperation;
	}
}
