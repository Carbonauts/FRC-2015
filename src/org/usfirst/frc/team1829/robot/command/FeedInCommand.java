package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the rollers on the jaw on to bring the CONTAINER
 * from the jaw to the conveyer.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class FeedInCommand extends Command
{
	private boolean finished = false;
	private Feeder feeder;
	
	private double speed;
	
	/**
	 * Default constructor
	 */
	public FeedInCommand(double speed)
	{
		super("FeedInCommand");
		requires(feeder = Robot.getFeeder());
		this.speed = speed > 0 ? speed : -speed;
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public FeedInCommand(double speed, double duration)
	{
		super("FeedInCommand", duration);
		requires(feeder = Robot.getFeeder());
		this.speed = speed > 0 ? speed : -speed;
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		feeder.set(speed);
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
