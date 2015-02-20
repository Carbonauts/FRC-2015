package org.usfirst.frc.team1829.robot.command;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.subsystems.Drive;
//import org.usfirst.frc.team1829.robot.subsystems.Jaw;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives the robot forward following a line until the jaw
 * contacts a CONTAINER, then stops.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class DriveOnLineCommand extends Command
{
	private Drive drive;
	//private Jaw jaw;
	private PIDController rotationController;
	private RotationPIDAdapter rotationAdapter;
	
	private double rotationP = 1.0/2000.0;
	private double rotationI = 0;
	private double rotationD = 0;
	private double latestOutput = 0;
	private boolean finished;
	
	/**
	 * Default constructor.
	 */
	public DriveOnLineCommand()
	{
		super("DriveOnLineCommand");
		requires(drive = Robot.getDrive());
		//jaw = Robot.getJaw();
		
		rotationAdapter = new RotationPIDAdapter();
		rotationController = new PIDController(rotationP, rotationI, rotationD, rotationAdapter, rotationAdapter);
	}
	
	/**
	 * Constructor with timeout.
	 * @param timeout The duration (in seconds) before the command "times out".
	 */
	public DriveOnLineCommand(double duration)
	{
		super("DriveOnLineCommand", duration);
		requires(drive = Robot.getDrive());
		//jaw = Robot.getJaw();
		
		rotationAdapter = new RotationPIDAdapter();
		rotationController = new PIDController(rotationP, rotationI, rotationD, rotationAdapter, rotationAdapter);
	}
	
	@Override
	protected void initialize() 
	{
		finished = false;
		rotationController.setSetpoint(drive.getLineFollowingFactor());
		System.out.println("Setpoint:" + rotationController.getSetpoint());
		
		rotationController.reset();
		rotationController.enable();
	}

	@Override
	protected void execute() 
	{
		//if(jaw.encounteredContainer()) {}  //Stop when we hit a new container
		drive.driveArcade(0, latestOutput);
		System.out.println("LatestOutput:" + latestOutput);
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
	
	public class RotationPIDAdapter implements PIDSource, PIDOutput
	{
		public void pidWrite(double output) 
		{
			latestOutput = output;
		}

		public double pidGet() 
		{	
			return drive.getLineFollowingFactor();
		}
	}
}
