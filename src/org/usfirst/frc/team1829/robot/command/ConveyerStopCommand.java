package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Conveyer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the conveyor motor if it's in motion.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyerStopCommand extends Command
{
	private boolean finished = false;
	private Conveyer conveyer;
	
	/**
	 * Default constructor
	 */
	public ConveyerStopCommand()
	{
		super("ConveyorFeedStopCommand");
		requires(conveyer = Robot.getConveyer());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		conveyer.stop();
	}

	@Override
	protected void execute() 
	{
		//Never finishes execution.
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
