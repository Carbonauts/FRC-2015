package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the Turret subsystem so that the elevator, conveyor,
 * and jaw subsystems are facing toward the center of the field
 * and ready to grab the CONTAINERS from the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class TurretToPerpendicularCommand extends Command
{
	private boolean finished;
	private Turret turret;
	
	/**
	 * Default constructor
	 */
	public TurretToPerpendicularCommand()
	{
		super("TurretToPerpendicularCommand");
		requires(turret = Robot.getTurret());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public TurretToPerpendicularCommand(double duration)
	{
		super("TurretToPerpendicularCommand", duration);
		requires(turret = Robot.getTurret());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		if(turret != null)
		{
			if(turret.isPerpendicular())
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
			if(turret.isPerpendicular())
			{
				turret.stop();
				finished = true;
			}
			else
			{
				turret.turnPerpendicular();
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
