package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

public class OperatorJawCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	/**
	 * Default constructor.
	 */
	public OperatorJawCommand()
	{
		super("Operator Drive Command");
		requires(jaw = Robot.getJaw());
	}
	
	/**
	 * Constructor with timeout.
	 * @param timeout Time in seconds before the command times out.
	 */
	public OperatorJawCommand(double timeout)
	{
		super("Operator Drive Command", timeout);
		requires(jaw = Robot.getJaw());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		jaw.move(Robot.getUI().getAxisData(Robot.UI_JAW_AXIS));
	}

	@Override
	protected boolean isFinished() 
	{	
		return finished;
	}

	@Override
	protected void end() 
	{
		jaw.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
