package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Cruisable;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyer is the subsystem that transports all CONTAINERS
 * from the jaw into the robot for storage.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Conveyer extends Subsystem implements Diagnosable, Cruisable
{
	/**
	 * Motor to move the conveyer system.
	 */
	private Talon motor;
	
	/**
	 * Default speed that the conveyer should move
	 * when switching positions.
	 */
	private double cruiseSpeed = 0.8;
	
	private String lastOperation = "";
	
	/**
	 * Constructs the conveyer.
	 */
	public Conveyer()
	{
		super("Conveyer");
		motor = new Talon(Robot.CONVEYOR_MOTOR);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Rolls the conveyer in at the cruise speed.
	 */
	public void rollIn()
	{
		setPower(this.cruiseSpeed);
		lastOperation = "rollIn()";
	}
	
	/**
	 * Rolls the conveyer out at the cruise speed.
	 */
	public void rollOut()
	{
		setPower(-this.cruiseSpeed);
		lastOperation = "rollIn()";
	}
	
	/**
	 * Stops the conveyer motor.
	 */
	public void stop()
	{
		motor.stopMotor();
		lastOperation = "stop()";
	}
	
	/**
	 * Directly sets the speed of the conveyer motor.
	 * @param speed The speed of the motor, from [-1, 1]
	 */
	public void setPower(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		if(speed < -1.0)
		{
			speed = -1.0;
		}
		
		speed = Robot.CONVEYOR_INVERTED ? -speed : speed;
		motor.set(speed);
	}
	
	/**
	 * Sets the cruise speed of the conveyer, or the 
	 * speed that it moves at by default.
	 * @param speed The new cruise speed, from [0, 1.0].
	 */
	public void setCruiseSpeed(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < 0)
		{
			speed = 0;
		}
		this.cruiseSpeed = speed;
	}
	
	public double getCruiseSpeed()
	{
		return this.cruiseSpeed;
	}

	
	public String getStatus() 
	{	
		return null;
	}
	

	public void updateSmartDS() 
	{
		
	}
	

	public String lastOperation() 
	{	
		return lastOperation;
	}
}