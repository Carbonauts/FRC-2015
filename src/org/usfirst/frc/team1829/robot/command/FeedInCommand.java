package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the rollers on the jaw on to bring the CONTAINER
 * from the jaw to the conveyor.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedInCommand extends Command
{
	/**
	 * Default constructor
	 */
	public FeedInCommand()
	{
		super("JawFeedInCommand");
		requires(Robot.getFeeder());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedInCommand(double duration)
	{
		super("JawFeedInCommand", duration);
		requires(Robot.getFeeder());
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
