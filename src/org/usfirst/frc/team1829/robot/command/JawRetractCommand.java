package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Closes the jaw.  Use when grabbing a CONTAINER or to
 * set up for releasing a CONTAINER.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class JawRetractCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	/**
	 * Default constructor
	 */
	public JawRetractCommand()
	{
		super("JawCloseCommand");
		requires(jaw = Robot.getJaw());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public JawRetractCommand(double duration)
	{
		super("JawCloseCommand", duration);
		requires(jaw = Robot.getJaw());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		
		if(jaw.isFullyRetracted())
		{
			finished = true;
		}
	}

	@Override
	protected void execute() 
	{
		if(jaw.isFullyRetracted())
		{
			finished = true;
			return;
		}
		else
		{
			jaw.retract();
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
		jaw.stop();
	}

	@Override
	protected void interrupted() 
	{
		
	}
}
