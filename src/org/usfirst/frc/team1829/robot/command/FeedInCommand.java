package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the rollers on the jaw on to bring the CONTAINER
 * from the jaw to the conveyor.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedInCommand extends Command
{
	private boolean finished = false;
	private Feeder feeder;
	
	/**
	 * Default constructor
	 */
	public FeedInCommand()
	{
		super("JawFeedInCommand");
		requires(feeder = Robot.getFeeder());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedInCommand(double duration)
	{
		super("JawFeedInCommand", duration);
		requires(feeder = Robot.getFeeder());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		feeder.feed(0.5);
	}

	@Override
	protected void execute() 
	{
		//Never finishes execution, must be interrupted or timed out.
	}

	@Override
	protected boolean isFinished() 
	{
		return finished;
	}

	@Override
	protected void end() 
	{
		feeder.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
