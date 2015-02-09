package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to be used in testing the subsystem, such as taking
 * sensor readings or testing subsystem method calls.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ConveyorDiagnosticCommand extends Command
{
	private Conveyor conveyor;
	private boolean finished;
	
	/**
	 * Default constructor.
	 */
	public ConveyorDiagnosticCommand()
	{
		super("ConveyorDiagnosticCommand");
		requires(conveyor = Robot.getConveyor());
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