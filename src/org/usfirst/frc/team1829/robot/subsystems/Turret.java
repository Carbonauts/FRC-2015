package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that rotates the entire upper structure of
 * the robot to face the row of CONTAINERS on the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Turret extends Subsystem
{
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
	private double cruiseSpeed = 0.6;
	
	/**
	 * Default turret constructor.
	 */
	public Turret()
	{
		turretMotor = new Talon(Robot.TURRET_MOTOR);
		parallelLimit = new CarbonDigitalInput(Robot.TURRET_LIMIT_PARALLEL, false);
		perpendicularLimit = new CarbonDigitalInput(Robot.TURRET_LIMIT_PERPENDICULAR, false);
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
		return parallelLimit.get();
	}
	
	/**
	 * Returns true if the turret is already
	 * fully turned sideways at a T from the drive train.
	 * @return
	 */
	public boolean isPerpendicular()
	{
		return perpendicularLimit.get();
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
		turretMotor.set(power);
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
	}
	
	/**
	 * Stops the turret motor.
	 */
	public void stop()
	{
		turretMotor.stopMotor();
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
	}
}
