package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives the robot forward following a line until the jaw
 * contacts a CONTAINER, then stops.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class DriveOnLineCommand extends Command
{
	public DriveOnLineCommand()
	{
		requires(Robot.getDrive());
	}
	
	@Override
	protected void initialize() 
	{
		
	}

	@Override
	protected void execute() 
	{
		
	}

	@Override
	protected boolean isFinished() 
	{
		return false;
	}

	@Override
	protected void end() 
	{
		
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
