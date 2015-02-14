package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to test the subsystem, such as taking sensor
 * readings or tweaking parameters.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class TurretDiagnosticCommand extends Command
{
	private Turret turret;
	private boolean finished;
	
	/**
	 * Default constructor.
	 */
	public TurretDiagnosticCommand()
	{
		super("TurretDiagnosticCommand");
		requires(turret = Robot.getTurret());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		System.out.println("[" + getName() + "] Parallel: " + turret.isParallel() + 
						   ", Perpendicular: " + turret.isPerpendicular());
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
