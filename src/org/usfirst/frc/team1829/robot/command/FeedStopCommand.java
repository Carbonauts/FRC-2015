package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class FeedStopCommand extends Command
{
	private boolean finished = false;
	private Feeder feeder;
	
	public FeedStopCommand()
	{
		super("FeedStopCommand");
		requires(feeder = Robot.getFeeder());
	}
	
	@Override
	protected void initialize() 
	{
		feeder.stop();
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
