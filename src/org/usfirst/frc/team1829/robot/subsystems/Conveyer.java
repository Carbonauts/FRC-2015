package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.util.Cruisable;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyer is the subsystem that transports all CONTAINERS
 * from the jaw into the robot for storage.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Conveyer extends Subsystem implements Cruisable
{
	/**
	 * Motor to move the conveyer system.
	 */
	private Talon conveyorMotor;
	
	/**
	 * Limit switch that triggers as a CONTAINER
	 * moves from the Jaw feeder into the conveyer.
	 */
	private DigitalInput enterLimit;
	
	/**
	 * Limit switch that triggers when a CONTAINER
	 * reaches the far back of the conveyer.
	 */
	private DigitalInput fullLimit;
	
	/**
	 * Default speed that the conveyer should move
	 * when switching positions.
	 */
	private double cruiseSpeed = 0.6;
	
	/**
	 * Constructs the conveyer.
	 */
	public Conveyer()
	{
		super("Conveyer");
		conveyorMotor = new Talon(Robot.CONVEYOR_MOTOR);
		/*conveyorEncoder = new Encoder(Robot.CONVEYOR_ENCODER_A,
									  Robot.CONVEYOR_ENCODER_B,
									  Robot.CONVEYOR_DIRECTION);*/
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
		conveyorMotor.set(this.cruiseSpeed);
	}
	
	/**
	 * Rolls the conveyer out at the cruise speed.
	 */
	public void rollOut()
	{
		conveyorMotor.set(-this.cruiseSpeed);
	}
	
	/**
	 * Returns the value of the entrance limit
	 * switch.
	 * @return Limit switch status.
	 */
	public boolean isEnterLimit()
	{
		return enterLimit.get();
	}
	
	/**
	 * Returns the value of the full limit
	 * switch.
	 * @return Limit switch status.
	 */
	public boolean isFullLimit()
	{
		return fullLimit.get();
	}
	
	/**
	 * Stops the conveyer motor.
	 */
	public void stop()
	{
		conveyorMotor.stopMotor();
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
		conveyorMotor.set(speed);
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
}
