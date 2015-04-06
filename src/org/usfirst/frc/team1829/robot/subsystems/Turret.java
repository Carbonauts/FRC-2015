package org.usfirst.frc.team1829.robot.subsystems;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.command.OperatorTurretCommand;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import com.team1829.library.CarbonDigitalInput;
import com.team1829.library.CarbonTalon;
import com.team1829.library.LatchBoolean;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem that rotates the entire upper structure of
 * the robot to face the row of CONTAINERS on the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Turret extends Subsystem implements Diagnosable
{
	public static final double MAX_SPEED = 0.5;
	
	DecimalFormat formatter = new DecimalFormat("000.00");
	
	/**
	 * Motor for this subsystem.
	 */
	private CarbonTalon motor;
	
	/**
	 * Limit switch that triggers when the turret
	 * is aligned parallel to the drive train of
	 * the robot.
	 */
	private CarbonDigitalInput turnLimit;
	
	private Timer directionTimer;
	
	private LatchBoolean limitLatch;
	
	private boolean parallel = false;
	
	private boolean perpendicular = false;
	
	/**
	 * The default speed that methods in this subsystem
	 * cause the motor to turn at.
	 */
	private double cruiseSpeed = 0.4;
	
	private String lastOperation = "";
	
	private boolean lastMovementPerpendicular = false;
	
	private boolean lastMovementParallel = false;
	
	/**
	 * Default turret constructor.
	 */
	public Turret()
	{
		super("Turret");
		motor = new CarbonTalon(Robot.TURRET_MOTOR, 0.025, 50);
		motor.setRampEnabled(true);
		turnLimit = new CarbonDigitalInput(Robot.TURRET_LIMIT, false);
		directionTimer = new Timer();
		limitLatch = new LatchBoolean();
		
		parallel = true;
		perpendicular = false;
		
		directionTimer.schedule(new LimitTask(), 0, 20);
		
		lastOperation = "Turret() constructed";
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		this.setDefaultCommand(new OperatorTurretCommand());
	}
	
	/**
	 * Returns true if the turret is already
	 * fully turned in-line with the drive train.
	 * @return
	 */
	public boolean isParallel()
	{
		return parallel;
	}
	
	/**
	 * Returns true if the turret is already
	 * fully turned sideways at a T from the drive train.
	 * @return
	 */
	public boolean isPerpendicular()
	{
		return perpendicular;
	}
	
	/**
	 * Sets the power for the turret motor.
	 * @param power The power for the turret motor.
	 */
	public void set(double power)
	{
		power = (power > MAX_SPEED) ? MAX_SPEED : power;
		power = (power < -MAX_SPEED) ? -MAX_SPEED : power;
		
		power = Robot.TURRET_INVERTED ? -power : power;
		System.out.println("Power=" + power);
		/*if(power > 0 && isParallel())
		{
			power = 0;
		}
		if(power < 0 && isPerpendicular())
		{
			power = 0;
		}*/
		
		motor.set(power);
		lastOperation = "setPower(" + formatter.format(power) + ")";
	}
	
	/**
	 * Sets the motor to turn the direction that
	 * puts the turret parallel to the drive train.
	 */
	public void turnParallel()
	{
		if(!isParallel())
		{			
			set(cruiseSpeed);
		}
		lastOperation = "turnParallel()";
	}
	
	/**
	 * Sets the motor to turn the direction that
	 * puts the turret perpendicular to the drive train.
	 */
	public void turnPerpendicular()
	{
		if(!isPerpendicular())
		{
			set(-cruiseSpeed);			
		}
		lastOperation = "turnPerpendicular()";
	}
	
	/**
	 * Stops the turret motor.
	 */
	public void stop()
	{
		motor.stopMotor();
		lastOperation = "stop()";
	}
	
	/**
	 * Sets the default speed of this subsystem.
	 */
	public void setCruiseSpeed(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < -1.0)
		{
			speed = -1.0;
		}
		cruiseSpeed = speed;
		lastOperation = "setCruiseSpeed(" + formatter.format(speed) + ")";
	}

	public String getStatus() 
	{	
		StringBuffer feedback = new StringBuffer("[" + getName() + "]");
		feedback.append(" ParaLim:").append(isParallel() ? "T" : "F");
		feedback.append(" PerpLim:").append(isPerpendicular() ? "T" : "F");
		feedback.append(" LastMovementPerpendicular:").append(lastMovementPerpendicular);
		return feedback.toString();
	}
	
	public void updateSmartDS()
	{
		SmartDashboard.putBoolean("Turret Parallel Lmiit", isParallel());
		SmartDashboard.putBoolean("Turret Perpendicular Limit", isPerpendicular());
		SmartDashboard.putString("Turret Last Operation", lastOperation());
		SmartDashboard.putBoolean("Turret Sensor", !turnLimit.get());
		SmartDashboard.putBoolean("LastMovementPerpendicular", lastMovementPerpendicular);
	}

	public String lastOperation() 
	{	
		return lastOperation;
	}
	
	public String getDIOFeedback()
	{
		StringBuffer feedback = new StringBuffer();
		feedback.append(turnLimit.getChannel()).append(":").append(isParallel() ? "T" : "F").append(" ");
		return feedback.toString();
	}
	
	public class LimitTask extends TimerTask
	{
		@Override
		public void run() 
		{
			if(limitLatch.onTrue(!turnLimit.get()))
			{
				lastMovementPerpendicular = motor.get() > 0;
				parallel = !lastMovementPerpendicular;
				perpendicular = lastMovementPerpendicular;
			}
			else if(limitLatch.onFalse(!turnLimit.get()))
			{
				parallel = false;
				perpendicular = false;
			}
		}
	}
}
