package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Conveyer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the conveyer motor if it's in motion.
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
		conveyer.stop();
		finished = true;
	}

	@Override
	protected void execute() 
	{
		//Finishes immediately
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
