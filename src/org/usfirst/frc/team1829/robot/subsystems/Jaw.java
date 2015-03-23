package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Cruisable;

import com.team1829.library.LatchBoolean;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that grabs CONTAINERS and places them on the
 * conveyer, as well as takes them off of the conveyer and
 * deposits them on a tote.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Jaw extends Subsystem implements Cruisable
{
	/**
	 * Motor that pushes and retracts the Jaw linkage
	 * to compress the CONTAINER.
	 */
	private Talon jawMotor;
	
	/**
	 * Motor that drives the gripping rollers that move
	 * CONTAINERS from the Jaw to the Conveyer.
	 */
	private Talon feedMotor;
	
	/**
	 * Limit switch that triggers when the Jaw is in its
	 * fully retracted position.
	 */
	private DigitalInput retractLimit;
	
	/**
	 * Limit switch that triggers when the Jaw is in its
	 * fully extended position.
	 */
	private DigitalInput extendLimit;
	
	/**
	 * Limit switch that triggers when the Jaw has a container
	 * firmly grasped in its rollers.
	 */
	private DigitalInput clampLimit;
	
	/**
	 * Limit switch that triggers when the Jaw has
	 * encountered a new CONTAINER (i.e. the robot
	 * has driven the Jaw into contact with a
	 * CONTAINER).
	 */
	private DigitalInput encounterLimit;
	
	private LatchBoolean hitContainer;
	
	private double cruiseSpeed = 0.6; //TODO Find appropriate value for default.
	
	private double feedSpeed = 0.6; //TODO Find appropriate value for default.
	
	/**
	 * Constructs the Jaw subsystem.
	 */
	public Jaw()
	{
		jawMotor = new Talon(Robot.JAW_GRAB_MOTOR);
		feedMotor = new Talon(Robot.JAW_FEED_MOTOR);
		retractLimit = new DigitalInput(Robot.JAW_LIMIT_RETRACT);
		extendLimit = new DigitalInput(Robot.JAW_LIMIT_EXTENT);
		clampLimit = new DigitalInput(Robot.JAW_LIMIT_CLAMP);
		encounterLimit = new DigitalInput(Robot.JAW_LIMIT_ENCOUNTER);
		hitContainer = new LatchBoolean();
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Sets the feeders to roll at the specified speed.
	 * @param speed
	 */
	public void feed(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < -1.0)
		{
			speed = -1.0;
		}
		feedMotor.set(speed);
	}
	
	/**
	 * Spins the feeder rollers to pull a container up and into
	 * the jaw.  Should be called after clamp().
	 */
	public void feedIn()
	{
		if(feedSpeed > 0)
		{
			feed(this.feedSpeed);
		}
		else
		{
			feed(-this.feedSpeed);
		}
	}
	
	/**
	 * Spins the feeder rollers to release a container from the
	 * jaw, lowering it out of the grasp.
	 */
	public void feedOut()
	{
		if(this.feedSpeed > 0)
		{
			feedMotor.set(-this.feedSpeed);
		}
		else
		{
			feedMotor.set(this.feedSpeed);
		}
	}
	
	/**
	 * Actuates the jaw at the given speed.
	 * @param speed
	 */
	public void moveJaw(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < -1.0)
		{
			speed = -1.0;
		}
		
		if(speed > 0)
		{
			if(fullyExtended()) //Safety for extending.
			{
				speed = 0.0;
			}
		}
		else if(speed < 0)
		{
			if(fullyRetracted()) //Safety for retracting.
			{
				speed = 0.0;
			}
		}
		jawMotor.set(speed);
	}
	
	/**
	 * Pulls the container onto the conveyer.
	 */
	public void retract()
	{
		if(!fullyRetracted())
		{
			if(this.cruiseSpeed > 0)
			{
				moveJaw(-this.cruiseSpeed);
			}
			else
			{
				moveJaw(this.cruiseSpeed);
			}
		}
		else
		{
			jawMotor.stopMotor();
		}
	}
	
	/**
	 * Retracts the jaw just enough to clamp the container between the
	 * jaw and the feed rollers.
	 */
	public void clamp()
	{
		if(!containerClamped())
		{
			if(this.cruiseSpeed > 0)
			{
				jawMotor.set(-this.cruiseSpeed);
			}
			else
			{
				jawMotor.set(this.cruiseSpeed);
			}
		}
	}
	
	/**
	 * Moves the jaw toward its fully extended position.
	 */
	public void extend()
	{
		if(!fullyExtended())
		{
			if(this.cruiseSpeed > 0)
			{
				jawMotor.set(this.cruiseSpeed);
			}
			else
			{
				jawMotor.set(-this.cruiseSpeed);
			}
		}
	}
	
	public boolean encounteredContainer()
	{
		return hitContainer.onTrue(encounterLimit.get());
	}
	
	/**
	 * True if the jaw is fully retracted.
	 * @return
	 */
	public boolean fullyRetracted()
	{
		return retractLimit.get(); //TODO invert if necessary.
	}
	
	/**
	 * True if the jaw is fully extended.
	 * @return
	 */
	public boolean fullyExtended()
	{
		return extendLimit.get();
	}
	
	/**
	 * True if a container is fully clamped inside the jaw.
	 * @return
	 */
	public boolean containerClamped()
	{
		return clampLimit.get(); //TODO invert if necessary.
	}

	/**
	 * Sets the speed for use with retract() and extend().  More
	 * precise control can be achieved with moveJaw(double speed).
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

	/**
	 * Gets the speed to be used in retract() and extend().
	 */
	public double getCruiseSpeed() 
	{	
		return this.cruiseSpeed;
	}
	
	/**
	 * Sets the speed to be used in feedIn() and feedOut().
	 * More precise control can be achieved with feed(double speed).
	 * @param speed The cruise speed for the feeder.
	 */
	public void setFeedSpeed(double speed)
	{
		if(speed > 1.0)
		{
			speed = 1.0;
		}
		else if(speed < 0)
		{
			speed = 0;
		}
		this.feedSpeed = speed;
	}
	
	/**
	 * Returns the speed of the feeder while in use with feedIn() and feedOut().
	 * @return
	 */
	public double getFeedSpeed()
	{
		return this.feedSpeed;
	}
}
