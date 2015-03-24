package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns on the conveyor motor to bring the
 * CONTAINER into the robot from the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyerFeedInCommand extends Command
{
	/**
	 * Default constructor
	 */
	public ConveyerFeedInCommand()
	{
		super("ConveyorFeedInCommand");
		requires(Robot.getConveyer());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before the command "times out".
	 */
	public ConveyerFeedInCommand(double duration)
	{
		super("Conveyor Command", duration);
		requires(Robot.getConveyer());
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
