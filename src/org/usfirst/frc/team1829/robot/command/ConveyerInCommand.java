package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Conveyer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns on the conveyor motor to bring the
 * CONTAINER into the robot from the jaw.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyerInCommand extends Command
{
	private boolean finished = false;
	private Conveyer conveyer;
	
	/**
	 * Default constructor.  This will cause the 
	 * conveyer to run continuously until it is
	 * interrupted or ConveyerStopCommand() is called.
	 */
	public ConveyerInCommand()
	{
		super("Conveyor Command");
		requires(conveyer = Robot.getConveyer());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before the command "times out".
	 */
	public ConveyerInCommand(double duration)
	{
		super("Conveyor Command", duration);
		requires(conveyer = Robot.getConveyer());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		conveyer.rollIn();
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