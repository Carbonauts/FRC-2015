package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns on the feeder on the jaw to move a CONTAINER
 * from the conveyor into the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedOutCommand extends Command
{
	private boolean finished = false;
	private Feeder feeder;
	
	/**
	 * Default constructor
	 */
	public FeedOutCommand()
	{
		super("JawFeedOutCommand");
		requires(feeder = Robot.getFeeder());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedOutCommand(double duration)
	{
		super("JawFeedOutCommand", duration);
		requires(feeder = Robot.getFeeder());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		feeder.feed(-0.3);
	}

	@Override
	protected void execute() 
	{
		//Never finishes execution
	}

	@Override
	protected boolean isFinished() 
	{
		return finished;
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
