package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import com.team1829.library.LatchBoolean;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that grabs CONTAINERS and places them on the
 * conveyor, as well as takes them off of the conveyor and
 * deposits them on a tote.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Jaw extends Subsystem
{
	/**
	 * Motor that pushes and retracts the Jaw linkage
	 * to compress the CONTAINER.
	 */
	private Talon jawMotor;
	
	/**
	 * Motor that drives the gripping rollers that move
	 * CONTAINERS from the Jaw to the Conveyor.
	 */
	private Talon feedMotor;
	
	/**
	 * Limit switch that triggers when the Jaw is in its
	 * fully retracted position.
	 */
	private DigitalInput retractLimit;
	
	/**
	 * Limit switch that triggers when the Jaw has
	 * encountered a new CONTAINER (i.e. the robot
	 * has driven the Jaw into contact with a
	 * CONTAINER).
	 */
	private DigitalInput encounterLimit;
	
	private LatchBoolean hitContainer;
	
	/**
	 * Constructs the Jaw subsystem.
	 */
	public Jaw()
	{
		jawMotor = new Talon(Robot.JAW_GRAB_MOTOR);
		feedMotor = new Talon(Robot.JAW_FEED_MOTOR);
		retractLimit = new DigitalInput(Robot.JAW_LIMIT_RETRACT);
		encounterLimit = new DigitalInput(Robot.JAW_LIMIT_ENCOUNTER);
		hitContainer = new LatchBoolean();
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	public boolean encounteredContainer()
	{
		return hitContainer.onTrue(encounterLimit.get());
	}
}
