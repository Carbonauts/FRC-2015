package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive90LeftCommand extends Command
{
	private boolean finished = false;

	public Drive90LeftCommand()
	{
		super("Drive90LeftCommand");
		requires(Robot.getDrive());
	}
	
	public Drive90LeftCommand(double duration)
	{
		super("Drive90LeftCommand", duration);
		requires(Robot.getDrive());
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
