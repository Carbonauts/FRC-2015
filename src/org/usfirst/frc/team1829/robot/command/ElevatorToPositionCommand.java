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
 */
public class ElevatorToPositionCommand extends Command
{
	private Elevator.Position targetPosition;
	
	public ElevatorToPositionCommand(Elevator.Position position)
	{
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
