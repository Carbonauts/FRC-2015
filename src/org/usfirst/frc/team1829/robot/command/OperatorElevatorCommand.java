package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

import com.team1829.library.Carbon;

import edu.wpi.first.wpilibj.command.Command;

public class OperatorElevatorCommand extends Command
{
	private Elevator elevator;
	private boolean finished;
	
	public OperatorElevatorCommand()
	{
		super("OperatorElevatorCommand");
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
		System.out.println("ElevatorOperatorCommand");
		//Only set power if axis enable button is held
		if(Robot.getUI().getButtonState(Robot.UI_ELEVATOR_AXIS_ENABLE))
		{
			double speed = Carbon.map(Robot.getUI().getAxisData(Robot.UI_ELEVATOR_AXIS), -1.0, 1.0, -Elevator.MAX_SPEED, Elevator.MAX_SPEED);
			System.out.println("Elevator speed: " + speed);
			elevator.set(speed);
		}
		else
		{
			System.out.println("Elevator set power 0");
			elevator.set(0.0);
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
		elevator.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
