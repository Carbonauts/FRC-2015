package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

public class JawClampCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	private ClampPIDAdapter pidAdapter;
	private PIDController pidController;
	
	private double p = 0.0;
	private double i = 0.0;
	private double d = 0.0;
	
	private double latestOutput = 0.0;
	private boolean pidEnabled = true;
	
	public JawClampCommand()
	{
		super("JawClampCommand");
		requires(jaw = Robot.getJaw());
		
		pidAdapter = new ClampPIDAdapter();
		pidController = new PIDController(p, i, d, pidAdapter, pidAdapter);
		
		pidController.setOutputRange(-0.5, 0.5);
	}
	
	public JawClampCommand(double duration)
	{
		super("JawClampCommand", duration);
		requires(jaw = Robot.getJaw());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
	}

	@Override
	protected void execute() 
	{
		if(pidEnabled)
		{
			jaw.set(latestOutput);
		}
		else
		{
			jaw.stop();
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
		
	}
	
	public class ClampPIDAdapter implements PIDSource, PIDOutput
	{
		public void pidWrite(double output) 
		{
			latestOutput = output;
		}

		public double pidGet() 
		{	
			return jaw.getExtent();
		}
		
	}
}
