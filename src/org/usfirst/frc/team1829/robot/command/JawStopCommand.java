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
		jaw.stop();
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
