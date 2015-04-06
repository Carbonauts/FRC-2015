package org.usfirst.frc.team1829.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1829.robot.CarbonTalon;
import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.command.OperatorElevatorCommand;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import com.team1829.library.CarbonAnalogInput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem that controls the vertical movement of the
 * Container-grabbing arm (conveyer).  This implements a 
 * PID control system to allow smooth movement.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Elevator extends Subsystem implements Diagnosable
{
	public static final double MAX_SPEED = 0.5;
	
	DecimalFormat formatter = new DecimalFormat("000.00");
	
	/*
	 * These constants define preset ultrasonic locations
	 * of different scoring zones.
	 * TODO Measure and input actual positions.
	 */
	public static final int AUTO_POSITION = 0;
	public static final int LEVEL1_POSITION = 0;
	public static final int LEVEL2_POSITION = 0;
	public static final int LEVEL3_POSITION = 0;
	public static final int LEVEL4_POSITION = 0;
	public static final int LEVEL5_POSITION = 0;
	
	public enum Position
	{
		AUTO,
		LEVEL1,
		LEVEL2,
		LEVEL3,
		LEVEL4,
		LEVEL5
	}
	
	/**
	 * The Talon motor controller that we use for this
	 * subsystem.
	 */
	private CarbonTalon motor;
	
	/**
	 * The ultrasonic attached to the subsystem to provide
	 * feedback for the PID controller.
	 */
	private CarbonAnalogInput ultrasonic;
	
	/**
	 * Limit switch that triggers when the elevator is
	 * at maximum height.
	 */
	private DigitalInput topLimit;
	
	/**
	 * Limit switch that triggers when the elevator is
	 * at minimum height.
	 */
	private DigitalInput botLimit;
	
	/**
	 * PID Control loop for the elevator while in position mode.
	 */
	private PIDController pidController;
	
	/**
	 * PID input/output adapter for the elevator
	 * PIDController while in position mode.
	 */
	private ElevatorPIDAdapter pidAdapter;
	
	private String lastOperation = "";
	
	private boolean pidEnabled = false; //TODO Flag to remember to enable PID at some point.
	
	//TODO add actual PID values.
	private static double elevatorP = 1.0/800.0;
	private static double elevatorI = 0;
	private static double elevatorD = 0;
	
	/**
	 * Initializes the Elevator subsystem and sets
	 * default values.
	 */
	public Elevator()
	{
		super("Elevator");
		
		motor = new CarbonTalon(Robot.ELEVATOR_MOTOR, 0.025, 50);
		motor.setRampEnabled(true);
		ultrasonic = new CarbonAnalogInput(Robot.ELEVATOR_ULTRA, CarbonAnalogInput.SmoothingMode.AVERAGE, 8, 20);
		topLimit = new DigitalInput(Robot.ELEVATOR_LIMIT_TOP);
		botLimit = new DigitalInput(Robot.ELEVATOR_LIMIT_BOT);
		
		pidAdapter = new ElevatorPIDAdapter();
		pidController = new PIDController(elevatorP,
										  elevatorI,
										  elevatorD,
										  pidAdapter,
										  pidAdapter);
		
		//Limits the max speed of the PID outputs (and therefore the motor speed).
		pidController.setOutputRange(-0.5, 0.5);
		
		lastOperation = "Elevator() constructed";
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(new OperatorElevatorCommand());
	}
	
	/**
	 * Gets the limit switch reading of the top limit
	 * of the elevator.
	 * @return True if the elevator is at the top,
	 * false otherwise.
	 */
	public boolean isAtTop()
	{
		return !topLimit.get();
	}
	
	/**
	 * Gets the limit switch reading of the bottom
	 * limit of the elevator.
	 * @return True if the elevator is at the bottom,
	 * false otherwise.
	 */
	public boolean isAtBottom()
	{
		return !botLimit.get();
	}
	
	/**
	 * Sets the subsystem to move to the desired position.
	 * @param position The target position.
	 */
	public void moveToPosition(Position position)
	{
		setSetpoint(getCoordinatesFor(position));
		lastOperation = "moveToPosition(" + position.toString() + ")";
	}
	
	/**
	 * Set the setpoint for the elevator's PID Controller
	 * @param setpoint
	 */
	public void setSetpoint(double setpoint)
	{
		pidController.setSetpoint(setpoint);
		lastOperation = "setSetpoint(" + setpoint + ")";
	}
	
	/**
	 * Returns the current setpoint of the internal PID Controller
	 * @return
	 */
	public double getSetpoint()
	{
		return pidController.getSetpoint();
	}
	
	/**
	 * Returns the distance data from the ultrasonic sensor.
	 * @return Distance data.
	 */
	public double getPosition()
	{
		return ultrasonic.getAverageSmoothedValue();
	}
	
	/**
	 * Sets the motor power.  If being used manually,
	 * be sure to call setPIDEnabled(false) or you will
	 * conflict with the PID control.
	 * @param power
	 */
	public void set(double power)
	{		
		power = (power > MAX_SPEED) ? MAX_SPEED : power; //Speed top cap
		power = (power < -MAX_SPEED) ? -MAX_SPEED : power; //Speed bottom cap
		
		power = Robot.ELEVATOR_INVERTED ? -power : power;
		
		if(power < 0) //Going up
		{
			if(isAtTop()) //Safety for going up.
			{
				power = 0.0;
			}
		}
		else if(power > 0) //Going down
		{
			if(isAtBottom()) //Safety for going down.
			{
				power = 0.0;
			}
		}
		
		motor.set(power);
		lastOperation = "moveElevator(" + power + ")";
	}
	
	/**
	 * Turns off the PID control (so that the PID loops
	 * do not continue to set motor outputs) and manually
	 * sets the motor speed to 0.
	 */
	public void stop()
	{
		setPIDEnabled(false);
		motor.stopMotor();
		lastOperation = "stop()";
	}
	
	/**
	 * Sets whether the elevator is currently controlled by PID.
	 * @param enabled PID Enabled.
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
	 * Returns encoder position units for a particular position.
	 * @param position The position to retrieve units for.
	 * @return Encoder position units for a particular position.
	 */
	public int getCoordinatesFor(Position position)
	{
		int toReturn = -1;
		switch(position)
		{
		case AUTO:
			toReturn = AUTO_POSITION;
			break;
		case LEVEL1:
			toReturn = LEVEL1_POSITION;
			break;
		case LEVEL2:
			toReturn = LEVEL2_POSITION;
			break;
		case LEVEL3:
			toReturn = LEVEL3_POSITION;
			break;
		case LEVEL4:
			toReturn = LEVEL4_POSITION;
			break;
		case LEVEL5:
			toReturn = LEVEL5_POSITION;
			break;
		default:
			break;
		}
		return toReturn;
	}
	
	/**
	 * Acts as a middleman between the subsystem and the PIDController.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class ElevatorPIDAdapter implements PIDSource, PIDOutput
	{
		/**
		 * Receive the output from the PID loop.
		 */
		public void pidWrite(double output) 
		{
			if(pidEnabled)
			{
				set(output);
			}
		}

		/**
		 * Send sensor feedback to the PID loop.
		 */
		public double pidGet() 
		{	
			return getPosition();
		}
	}

	/**
	 * Returns a string representation of the status of this
	 * subsystem.
	 */
	public String getStatus() 
	{	
		StringBuffer feedback = new StringBuffer("");
		feedback.append("[" + getName() + "]");
		DecimalFormat ultraFormat = new DecimalFormat("0000.00");
		feedback.append(" Ultra:").append(ultraFormat.format(ultrasonic.getAverageSmoothedValue()));
		feedback.append(" TopLim:").append(isAtTop() ? "T" : "F");
		feedback.append(" BotLim:").append(isAtBottom() ? "T" : "F");
		return feedback.toString();
	}
	
	/**
	 * Updates the smartdashboard with data from the status
	 * of this subsystem.
	 */
	public void updateSmartDS()
	{
		SmartDashboard.putNumber("Elevator Ultrasonic Graph", ultrasonic.getRawValue());
		SmartDashboard.putNumber("Elevator Ultrasonic", ultrasonic.getRawValue());
		SmartDashboard.putNumber("Elevator Ultrasonic Smoothed Graph", ultrasonic.getAverageSmoothedValue());
		SmartDashboard.putNumber("Elevator Ultrasonic Smoothed", ultrasonic.getAverageSmoothedValue());
		SmartDashboard.putBoolean("Elevator Top Limit", isAtTop());
		SmartDashboard.putBoolean("Elevator Bottom Limit", isAtBottom());
		SmartDashboard.putString("Elevator Last Operation", lastOperation());
	}

	/**
	 * Returns a string representation of the last major
	 * operation this subsystem did.
	 */
	public String lastOperation() 
	{	
		return lastOperation;
	}
	
	public String getDIOFeedback()
	{
		StringBuffer feedback = new StringBuffer();
		feedback.append(topLimit.getChannel()).append(":").append(isAtTop() ? "T" : "F").append(" ");
		feedback.append(botLimit.getChannel()).append(":").append(isAtBottom() ? "T" : "F").append(" ");
		return feedback.toString();
	}
}