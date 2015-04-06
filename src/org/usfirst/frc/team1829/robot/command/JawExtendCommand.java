package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Opens the Jaw.  Use to set down a CONTAINER or to set up
 * for picking up a new CONTAINER.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class JawExtendCommand extends Command
{
	private boolean finished = false;
	private Jaw jaw;
	
	/**
	 * Default constructor
	 */
	public JawExtendCommand()
	{
		super("JawOpenCommand");
		requires(jaw = Robot.getJaw());
	}
	
	/**
	 * Constructor with timeout.
	 * @param duration The time (in seconds) before this command "times out".
	 */
	public JawExtendCommand(double duration)
	{
		super("JawOpenCommand", duration);
		requires(jaw = Robot.getJaw());
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		
		if(jaw.isFullyExtended())
		{
			finished = true;
		}
	}

	@Override
	protected void execute() 
	{
		if(jaw.isFullyExtended())
		{
			finished = true;
			return;
		}
		else
		{
			jaw.extend();
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
