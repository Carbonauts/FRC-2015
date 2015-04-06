package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Cruisable;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem implements Diagnosable, Cruisable
{
	/**
	 * Motor that drives the gripping rollers that move
	 * CONTAINERS from the Jaw to the Conveyer.
	 */
	private Talon feedMotor;
	
	private double cruiseSpeed = 0.6;
	
	public Feeder()
	{
		super("Feeder");
		feedMotor = new Talon(Robot.FEED_MOTOR);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Sets the feeders to roll at the specified speed.
	 * @param speed
	 */
	public void set(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < -1.0)
		{
			speed = -1.0;
		}
		
		speed = Robot.FEED_INVERTED ? -speed : speed;
		feedMotor.set(speed);
	}
	
	/**
	 * Spins the feeder rollers to pull a container up and into
	 * the jaw.  Should be called after clamp().
	 */
	public void feedIn()
	{
		if(cruiseSpeed > 0)
		{
			set(this.cruiseSpeed);
		}
		else
		{
			set(-this.cruiseSpeed);
		}
	}
	
	/**
	 * Spins the feeder rollers to release a container from the
	 * jaw, lowering it out of the grasp.
	 */
	public void feedOut()
	{
		if(this.cruiseSpeed > 0)
		{
			set(-this.cruiseSpeed);
		}
		else
		{
			set(this.cruiseSpeed);
		}
	}
	
	public void stop()
	{
		feedMotor.stopMotor();
	}

	public void setCruiseSpeed(double speed) 
	{
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
		return null;
	}

	public String getDIOFeedback() 
	{	
		return null;
	}
}
