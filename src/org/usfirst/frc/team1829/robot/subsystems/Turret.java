package org.usfirst.frc.team1829.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem that rotates the entire upper structure of
 * the robot to face the row of CONTAINERS on the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Turret extends Subsystem implements Diagnosable
{
	DecimalFormat formatter = new DecimalFormat("000.00");
	
	/**
	 * Motor for this subsystem.
	 */
	private Talon turretMotor;
	
	/**
	 * Limit switch that triggers when the turret
	 * is aligned parallel to the drive train of
	 * the robot.
	 */
	private CarbonDigitalInput parallelLimit;
	
	/**
	 * Limit switch that triggers when the turret
	 * is aligned perpendicularly to the drive train
	 * of the robot.
	 */
	private CarbonDigitalInput perpendicularLimit;
	
	/**
	 * The default speed that methods in this subsystem
	 * cause the motor to turn at.
	 */
	private double cruiseSpeed = 0.4;
	
	private String lastOperation = "";
	
	/**
	 * Default turret constructor.
	 */
	public Turret()
	{
		super("Turret");
		turretMotor = new Talon(Robot.TURRET_MOTOR);
		parallelLimit = new CarbonDigitalInput(Robot.TURRET_LIMIT_PARALLEL, false);
		perpendicularLimit = new CarbonDigitalInput(Robot.TURRET_LIMIT_PERPENDICULAR, false);
		
		lastOperation = "Turret() constructed";
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Returns true if the turret is already
	 * fully turned in-line with the drive train.
	 * @return
	 */
	public boolean isParallel()
	{
		return !parallelLimit.get();
	}
	
	/**
	 * Returns true if the turret is already
	 * fully turned sideways at a T from the drive train.
	 * @return
	 */
	public boolean isPerpendicular()
	{
		return !perpendicularLimit.get();
	}
	
	/**
	 * Sets the power for the turret motor.
	 * @param power The power for the turret motor.
	 */
	public void setPower(double power)
	{
		if(power > 1.0)
		{
			power = 1.0;
		}
		else if(power < -1.0)
		{
			power = -1.0;
		}
		
		if(power > 0 && isParallel())
		{
			power = 0;
		}
		if(power < 0 && isPerpendicular())
		{
			power = 0;
		}
		turretMotor.set(power);
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
			setPower(cruiseSpeed);
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
			setPower(-cruiseSpeed);			
		}
		lastOperation = "turnPerpendicular()";
	}
	
	/**
	 * Stops the turret motor.
	 */
	public void stop()
	{
		turretMotor.stopMotor();
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

	public String getFeedback() 
	{	
		StringBuffer feedback = new StringBuffer("");
		feedback.append("[" + getName() + "] ParaLimit:").append(isParallel() ? "T" : "F");
		feedback.append(" PerpLimit:").append(isPerpendicular() ? "T" : "F");
		return feedback.toString();
	}
	
	public void updateSmartDS()
	{
		SmartDashboard.putBoolean("Turret Parallel Lmiit", isParallel());
		SmartDashboard.putBoolean("Turret Perpendicular Limit", isPerpendicular());
		SmartDashboard.putString("Turret Last Operation", lastOperation());
	}

	public String lastOperation() 
	{	
		return lastOperation;
	}
}
