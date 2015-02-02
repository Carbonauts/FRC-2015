package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Rotates the Turret subsystem so that the elevator, conveyor,
 * and jaw subsystems are in-line with the chassis of the robot
 * for transport and starting configurations.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class TurretToParallelCommand extends Command
{
	public TurretToParallelCommand()
	{
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
