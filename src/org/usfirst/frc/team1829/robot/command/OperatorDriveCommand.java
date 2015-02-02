package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Grants control of the drive system to the drivers.
 * This should be the default command of the Drive subsystem.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class OperatorDriveCommand extends Command
{
	public OperatorDriveCommand()
	{
		requires(Robot.getDrive());
	}
	
	@Override
	protected void initialize() 
	{
		
	}

	@Override
	protected void execute() 
	{
		Robot.getDrive().driveArcade(Robot.getUI().getAxisData(Robot.UI_DRIVE_Y),
									 Robot.getUI().getAxisData(Robot.UI_DRIVE_R));
	}

	@Override
	protected boolean isFinished() 
	{
		return false;
	}

	@Override
	protected void end() 
	{
		Robot.getDrive().stop();
	}

	@Override
	protected void interrupted() 
	{
		Robot.getDrive().stop();
	}
}
