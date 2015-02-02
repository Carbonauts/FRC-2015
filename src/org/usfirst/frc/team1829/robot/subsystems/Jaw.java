package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import com.team1829.library.CarbonDigitalInput;

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
	private Talon jawMotor;
	private Talon feedMotor;
	private CarbonDigitalInput jawLimit;
	
	public Jaw()
	{
		jawMotor = new Talon(Robot.JAW_GRAB_MOTOR);
		feedMotor = new Talon(Robot.JAW_FEED_MOTOR);
		jawLimit = new CarbonDigitalInput(Robot.JAW_LIMIT);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
}
