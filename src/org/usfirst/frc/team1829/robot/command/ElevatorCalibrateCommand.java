package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the elevator to the bottom extent of its range
 * until it hits the lower limit switch, then resets the
 * Elevator's encoder.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class ElevatorCalibrateCommand extends Command
{
	private Elevator elevator;
	private boolean finished = false;
	
	/**
	 * Default constructor.
	 */
	public ElevatorCalibrateCommand()
	{
		super("ElevatorCalibrateCommand");
		requires(elevator = Robot.getElevator());
	}
	
	/**
	 * Constructor with a timeout.
	 * @param timeout The time (in seconds) before this command "times out".
	 */
	public ElevatorCalibrateCommand(double timeout)
	{
		super("ElevatorCalibrateCommand", timeout);
		requires(elevator = Robot.getElevator());
	}
	
	@Override
	protected void initialize() 
	{
		if(elevator != null)
		{
			if(elevator.isAtBottom())
			{
				elevator.stop();
				elevator.resetPosition();
				finished = true;
			}
		}
	}

	@Override
	protected void execute() 
	{
		if(elevator.isAtBottom())
		{
			elevator.stop();
			elevator.resetPosition();
			finished = true;
		}
		else
		{
			elevator.moveDown();
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
		elevator.stop();
		finished = true;
	}
}
