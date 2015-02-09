package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyor is the subsystem that transports all CONTAINERS
 * from the jaw into the robot for storage.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Conveyor extends Subsystem
{
	/**
	 * Motor to move the conveyor system.
	 */
	private Talon conveyorMotor;
	
	/**
	 * Encoder to watch the conveyor motion.
	 */
	private Encoder conveyorEncoder;
	
	/**
	 * Limit switch that triggers as a CONTAINER
	 * moves from the Jaw feeder into the conveyor.
	 */
	private CarbonDigitalInput enterLimit;
	
	/**
	 * Limit switch that triggers when a CONTAINER
	 * reaches the far back of the conveyor.
	 */
	private CarbonDigitalInput fullLimit;
	
	/**
	 * Default speed that the conveyor should move
	 * when switching positions.
	 */
	private double cruiseSpeed = 0.6;
	
	/**
	 * Constructs the conveyor.
	 */
	public Conveyor()
	{
		conveyorMotor = new Talon(Robot.CONVEYOR_MOTOR);
		conveyorEncoder = new Encoder(Robot.CONVEYOR_ENCODER_A,
									  Robot.CONVEYOR_ENCODER_B,
									  Robot.CONVEYOR_DIRECTION);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	/**
	 * Rolls the conveyor in at the cruise speed.
	 */
	public void rollIn()
	{
		conveyorMotor.set(cruiseSpeed);
	}
	
	/**
	 * Rolls the conveyor out at the cruise speed.
	 */
	public void rollOut()
	{
		conveyorMotor.set(-cruiseSpeed);
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
	 * Stops the conveyor motor.
	 */
	public void stop()
	{
		conveyorMotor.stopMotor();
	}
	
	/**
	 * Directly sets the speed of the conveyor motor.
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
	 * Sets the cruise speed of the conveyor, or the 
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
		cruiseSpeed = speed;
	}
}
