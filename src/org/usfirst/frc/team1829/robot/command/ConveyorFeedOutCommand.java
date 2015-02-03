package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns on the conveyor motor to move the
 * CONTAINER from the robot to the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyorFeedOutCommand extends Command
{
	/**
	 * Default constructor
	 */
	public ConveyorFeedOutCommand()
	{
		super("ConveyorFeedOutCommand");
		requires(Robot.getConveyor());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before the command "times out".
	 */
	public ConveyorFeedOutCommand(double duration)
	{
		super("ConveyorFeedOutCommand", duration);
		requires(Robot.getConveyor());
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
