package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Closes the jaw.  Use when grabbing a CONTAINER or to
 * set up for releasing a CONTAINER.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class JawCloseCommand extends Command
{
	public JawCloseCommand()
	{
		requires(Robot.getJaw());
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
