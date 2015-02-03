package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the Turret subsystem so that the elevator, conveyor,
 * and jaw subsystems are facing toward the center of the field
 * and ready to grab the CONTAINERS from the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class TurretToPerpendicularCommand extends Command
{
	/**
	 * Default constructor
	 */
	public TurretToPerpendicularCommand()
	{
		super("TurretToPerpendicularCommand");
		requires(Robot.getTurret());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public TurretToPerpendicularCommand(double duration)
	{
		super("TurretToPerpendicularCommand", duration);
		requires(Robot.getTurret());
	}
	
	@Override
	protected void initialize() 
	{
		
	}

	@Override
	protected void execute() 
	{
		
	}

	@Override
	protected boolean isFinished() 
	{
		return false;
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
