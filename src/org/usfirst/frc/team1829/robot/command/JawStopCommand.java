package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

public class JawStopCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	/**
	 * Default constructor.
	 */
	public JawStopCommand()
	{
		super("JawStopCommand");
		requires(jaw = Robot.getJaw());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		jaw.stop();
	}

	@Override
	protected void execute() 
	{
		//Never stops execution
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
