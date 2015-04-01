package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

public class JawClampCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	public JawClampCommand()
	{
		super("JawClampCommand");
		requires(jaw = Robot.getJaw());
	}
	
	public JawClampCommand(double duration)
	{
		super("JawClampCommand", duration);
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
