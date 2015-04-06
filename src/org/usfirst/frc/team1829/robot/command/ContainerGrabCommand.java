package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

public class ContainerGrabCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	private Feeder feeder;
	
	private boolean containerUpFlag = false;
	
	public ContainerGrabCommand()
	{
		super("Container Grab Command");
		requires(jaw = Robot.getJaw());
		requires(feeder = Robot.getFeeder());
	}

	@Override
	protected void initialize() 
	{
		finished = false;
		containerUpFlag = false;
	}

	@Override
	protected void execute() 
	{
		if(jaw.getContainerDistance() > 150)
		{
			containerUpFlag = true;
		}
		
		if(!containerUpFlag)
		{
			feeder.set(0.5);
		}
		else
		{
			feeder.set(0.2);
			jaw.retract();
			
			if(jaw.isFullyRetracted())
			{
				finished = true;
			}
		}
	}

	@Override
	protected boolean isFinished() 
	{
		return finished;
	}

	@Override
	protected void end() 
	{
		feeder.stop();
		jaw.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
