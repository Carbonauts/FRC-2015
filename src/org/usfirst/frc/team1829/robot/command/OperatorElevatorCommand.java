package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

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
		//elevator.setSetpoint(elevator.getSetpoint() + Robot.getUI().getAxisData(Robot.UI_ELEV_Y));
		elevator.setAbsolutePower(Robot.getUI().getAxisData(Robot.UI_ELEV_Y));
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
