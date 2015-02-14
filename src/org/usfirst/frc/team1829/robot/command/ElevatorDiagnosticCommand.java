package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to test the subsystem, such as taking sensor
 * readings or tweaking PID values.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ElevatorDiagnosticCommand extends Command
{
	private Elevator elevator;
	private boolean finished;
	
	/**
	 * Default constructor.
	 */
	public ElevatorDiagnosticCommand()
	{
		super("ElevatorDiagnosticCommand");
		requires(elevator = Robot.getElevator());
	}

	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		System.out.println("[" + getName() + "] Top: " + elevator.isAtTop() + 
						   ", Bottom: " + elevator.isAtBottom() + 
						   ", Position: " + elevator.getPosition());
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