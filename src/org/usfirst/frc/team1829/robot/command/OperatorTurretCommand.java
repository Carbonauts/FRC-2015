package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import com.team1829.library.Carbon;

import edu.wpi.first.wpilibj.command.Command;

public class OperatorTurretCommand extends Command
{
	private boolean finished = false;
	private Turret turret;
	
	public OperatorTurretCommand()
	{
		super("Operator Turret Command");
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
		if(Robot.getUI().getButtonState(Robot.UI_TURRET_AXIS_ENABLE))
		{
			double speed = Carbon.map(Robot.getUI().getAxisData(Robot.UI_TURRET_AXIS), -1.0, 1.0, -Turret.MAX_SPEED, Turret.MAX_SPEED);
			turret.set(speed);			
		}
		else
		{
			turret.set(0.0);
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
		turret.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}