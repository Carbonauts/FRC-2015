package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator to  a specified position, then stops.
 * The positions are preset in the elevator subsystem as
 * an enumeration, Elevator.Position with values LEVEL1,
 * LEVEL2, LEVEL3, LEVEL4, and LEVEL5.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 * TODO This command may not be necessary, look into replacing the
 * entire command with a simple Elevator.setSetpoint() statement.
 */
public class ElevatorToPositionCommand extends Command
{
	private Elevator.Position targetPosition;
	
	/**
	 * Default constructor.
	 * @param position The target position for the elevator.
	 */
	public ElevatorToPositionCommand(Elevator.Position position)
	{
		super("ElevatorToPositionCommand");
		requires(Robot.getElevator());
		targetPosition = position;
	}
	
	/**
	 * Constructor with timeout.
	 * @param position The target position for the elevator.
	 * @param duration The time (in seconds) before the command "times out".
	 */
	public ElevatorToPositionCommand(Elevator.Position position, double duration)
	{
		super("ElevatorToPositionCommand", duration);
		requires(Robot.getElevator());
		targetPosition = position;
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
