package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Conveyer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns on the conveyer motor to move the
 * container from the robot to the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyerOutCommand extends Command
{
	public boolean finished = false;
	public Conveyer conveyer;
	
	/**
	 * Default constructor.  This will cause the conveyer
	 * to run continuously until another command takes
	 * over or ConveyerStopCommand() is called.
	 */
	public ConveyerOutCommand()
	{
		super("ConveyorFeedOutCommand");
		requires(conveyer = Robot.getConveyer());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before the command "times out".
	 */
	public ConveyerOutCommand(double duration)
	{
		super("ConveyorFeedOutCommand", duration);
		requires(conveyer = Robot.getConveyer());
	}

	@Override
	protected void initialize() 
	{
		finished = false;
		conveyer.rollOut();
	}

	@Override
	protected void execute() 
	{
		//Execution never ends unless a timeout is specified.
	}

	@Override
	protected boolean isFinished() 
	{	
		return finished;
	}

	@Override
	protected void end() 
	{
		conveyer.stop();
	}

	@Override
	protected void interrupted()
	{
		
	}
}