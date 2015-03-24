package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns on the feeder on the jaw to move a CONTAINER
 * from the conveyor into the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedOutCommand extends Command
{
	/**
	 * Default constructor
	 */
	public FeedOutCommand()
	{
		super("JawFeedOutCommand");
		requires(Robot.getJaw());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedOutCommand(double duration)
	{
		super("JawFeedOutCommand", duration);
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
