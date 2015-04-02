package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the rollers on the jaw on to bring the CONTAINER
 * from the jaw to the conveyer.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedInCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	private Feeder feeder;
	
	private double speed;
	
	/**
	 * Default constructor
	 */
	public FeedInCommand(double speed)
	{
		super("JawFeedInCommand");
		requires(feeder = Robot.getFeeder());
		jaw = Robot.getJaw();
		this.speed = speed;
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedInCommand(double speed, double duration)
	{
		super("JawFeedInCommand", duration);
		requires(feeder = Robot.getFeeder());
		jaw = Robot.getJaw();
		this.speed = speed;
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		feeder.feed(speed);
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
