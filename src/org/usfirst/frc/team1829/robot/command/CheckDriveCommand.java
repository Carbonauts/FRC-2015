package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Drive;

import com.team1829.library.CarbonTimer;

import edu.wpi.first.wpilibj.command.Command;

public class CheckDriveCommand extends Command
{	
	public enum Routine
	{
		Forward,
		Reverse,
		TurnLeft,
		TurnRight
	}
	
	private Drive drive;
	private Routine routine;
	private CarbonTimer timer;
	private boolean finished = false;
	private double power;
	private long delay;
	
	public CheckDriveCommand(Routine routine, double duration)
	{
		super("CheckDrive", duration);
		requires(drive = Robot.getDrive());
		this.routine = routine;
		this.delay = (long) (duration * 1000) / 10;
	}

	@Override
	protected void initialize() 
	{
		finished = false;
		power = 0.0;
		System.out.println("CheckDriveCommand: Power=" + power);
		timer = new CarbonTimer(delay);
	}

	@Override
	protected void execute() 
	{
		if(timer.isDone())
		{
			if(power >= 1.0)
			{
				finished = true;
				return;
			}
			
			power += 0.10;
			System.out.println("CheckDriveCommand: Routine=" + routine.toString() + " Power=" + power);
			timer.reset(delay);
		}
		
		switch(this.routine)
		{
		case Forward:
			drive.driveArcade(power, 0.0);
			break;
		case Reverse:
			drive.driveArcade(-power, 0.0);
			break;
		case TurnLeft:
			drive.driveArcade(0, power);
			break;
		case TurnRight:
			drive.driveArcade(0, -power);
			break;
		default:
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
		
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
