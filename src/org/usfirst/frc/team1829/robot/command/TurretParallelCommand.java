package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Rotates the Turret subsystem so that the elevator, conveyor,
 * and jaw subsystems are in-line with the chassis of the robot
 * for transport and starting configurations.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class TurretParallelCommand extends Command
{
	private Turret turret;
	private boolean finished;
	
	/**
	 * Default constructor
	 */
	public TurretParallelCommand()
	{
		super("TurretToParallelCommand");
		requires(turret = Robot.getTurret());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public TurretParallelCommand(double duration)
	{
		super("TurretToParallelCommand", duration);
		requires(turret = Robot.getTurret());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		if(turret != null)
		{
			if(turret.isParallel())
			{
				turret.stop();
				finished = true;
			}
		}
	}

	@Override
	protected void execute() 
	{
		if(turret != null)
		{
			if(turret.isParallel())
			{
				turret.stop();
				finished = true;
			}
			else
			{
				turret.turnParallel();
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
		
	}

	@Override
	protected void interrupted() 
	{
		turret.stop();
	}
}
