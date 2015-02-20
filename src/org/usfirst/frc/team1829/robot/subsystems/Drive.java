package org.usfirst.frc.team1829.robot.subsystems;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.command.OperatorDriveCommand;
import org.usfirst.frc.team1829.robot.util.Diagnosable;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem that maneuvers the robot around the field.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Drive extends Subsystem implements Diagnosable
{	
	DecimalFormat formatter = new DecimalFormat("0000.00");
	
	/**
	 * Front-left drive motor as a Talon.
	 */
	private CANTalon motorLeftMaster;
	
	/**
	 * Rear-left drive motor as a Talon.
	 */
	private CANTalon motorLeftSlave;
	
	/**
	 * Front-right drive motor as a Talon.
	 */
	private CANTalon motorRightMaster;
	
	/**
	 * Rear-right drive motor as a Talon.
	 */
	private CANTalon motorRightSlave;
	
	/**
	 * RobotDrive object.
	 */
	private RobotDrive driveTrain;
	
	/**
	 * Analog input to read a value from our
	 * line-following device (an arduino
	 * that outputs the analog value).
	 */
	private AnalogInput lineFollower;
	
	private Timer driveUpkeepTimer;
	
	/**
	 * Drive gyro.
	 */
	//private Gyro gyro;
	
	//TODO set real PID values
	/**
	 * Proportional value for the drive motors.
	 */
	private double driveP = .005;
	
	/**
	 * Integral value for the drive motors.
	 */
	private double driveI = 0;
	
	/**
	 * Derivative value for the drive motors.
	 */
	private double driveD = 0;
	
	/**
	 * Max rate that the drive system will change speed at.
	 * Measured in volts/second of change.  This is only
	 * active in a closed-loop control mode, such as Speed
	 * or Position.
	 */
	private double driveRampRate = 3.0;
	
	/**
	 * The last operation the drive subsystem did.
	 */
	private String lastOperation = "";
	
	/**
	 * Setup the Drive system.
	 */
	public Drive() 
	{
		super("Drive");
		
		motorLeftMaster = new CANTalon(Robot.DRIVE_FRONT_LEFT);
		motorLeftSlave = new CANTalon(Robot.DRIVE_REAR_LEFT);
		motorRightMaster = new CANTalon(Robot.DRIVE_FRONT_RIGHT);
		motorRightSlave = new CANTalon(Robot.DRIVE_REAR_RIGHT);
		
		lineFollower = new AnalogInput(Robot.DRIVE_LINE);
		driveUpkeepTimer = new Timer();
		
		/*
		 * Run the dampening task every 100 ms, or 10 times/sec
		 * that updates the drive ramp based on the height of
		 * the elevator.
		 */
		driveUpkeepTimer.schedule(new DampeningTask(), 0L, 100);
		
		motorLeftSlave.changeControlMode(CANTalon.ControlMode.Follower);
		motorLeftSlave.set(motorLeftMaster.getDeviceID());
		
		motorRightSlave.changeControlMode(CANTalon.ControlMode.Follower);
		motorRightSlave.set(motorRightMaster.getDeviceID());
		
		motorLeftMaster.setCloseLoopRampRate(3.0); //Sets the motor to change at a max of X Volts/sec
		motorRightMaster.setCloseLoopRampRate(3.0);
		
		setDriveMode(CANTalon.ControlMode.PercentVbus);
		setBrakeMode(true);
		setPID(driveP, driveI, driveD);

		driveTrain = new RobotDrive(motorLeftMaster, motorRightMaster);
	}
	
	/**
	 * Sets a Command that will be launched whenever
	 * the Drive subsystem is at rest, i.e. there is
	 * no other Command running that 'requires' it.
	 * Usually the default drive Command reads operator
	 * input.
	 */
	protected void initDefaultCommand() 
	{
		setDefaultCommand(new OperatorDriveCommand());
	}
	
	/**
	 * Returns the value retrieved from the off-board
	 * line following calculator.
	 */
	public int getLineFollowingFactor()
	{
		return lineFollower.getValue();
	}
	
	/**
	 * Stops the drive train
	 */
	public void stop()
	{
		driveTrain.stopMotor();
		lastOperation = "stop()";
	}
	
	/**
	 * Drives based on a forward/backward and rotation basis.
	 * @param y Forward/backward value
	 * @param x Rotation value
	 */
	public void driveArcade(double y, double x)
	{
		driveTrain.arcadeDrive(y, x);
		lastOperation = "driveArcade(" + formatter.format(y) + ", " + formatter.format(x) + ")";
	}
	
	/**
	 * Set PID constants for the drive motors.
	 * @param p Proportional
	 * @param i Integral
	 * @param d Derivative
	 */
	public void setPID(double p, double i, double d)
	{
		motorLeftMaster.setPID(p, i, d);
		motorRightMaster.setPID(p, i, d);
		lastOperation = "setPID(" + formatter.format(p) + ", " + formatter.format(i) + ", " + formatter.format(d) + ")";
	}
	
	/**
	 * Changes the control mode of the Talon SRXs, between 
	 * Speed, Position, PercentVBus, etc.
	 * @param mode 
	 */
	public void setDriveMode(CANTalon.ControlMode mode)
	{
		motorLeftMaster.changeControlMode(mode);
		motorRightMaster.changeControlMode(mode);
		lastOperation = "setDriveMode(" + mode.toString() + ")";
	}
	
	/**
	 * Sets the drive motors to brake or coast.
	 * @param brake True to brake, false to coast.
	 */
	public void setBrakeMode(boolean brake)
	{
		motorLeftMaster.enableBrakeMode(brake);
		motorLeftSlave.enableBrakeMode(brake);
		motorRightMaster.enableBrakeMode(brake);
		motorRightSlave.enableBrakeMode(brake);
		lastOperation = "setBrakeMode(" + (brake ? "T" : "F") + ")";
	}

	/**
	 * Returns a string representation of the status
	 * of the drive subsystem.
	 */
	public String getFeedback() 
	{	
		StringBuffer feedback = new StringBuffer("[" + getName() + "]");
		feedback.append(" FL:").append(formatter.format(motorLeftMaster.getEncPosition()));
		feedback.append(" FR:").append(formatter.format(motorRightMaster.getEncPosition()));
		feedback.append(" RL:").append(formatter.format(motorLeftSlave.getEncPosition()));
		feedback.append(" RR:").append(formatter.format(motorRightSlave.getEncPosition()));
		DecimalFormat lineFormat = new DecimalFormat("0000.##");
		feedback.append(" Line:").append(lineFormat.format(getLineFollowingFactor()));
		feedback.append(lastOperation());
		return feedback.toString();
	}
	
	/**
	 * Sends the status of this subsystem to the SmartDashboard
	 */
	public void updateSmartDS()
	{
		SmartDashboard.putNumber("Drive Front Left Encoder", motorLeftMaster.getEncPosition());
		SmartDashboard.putNumber("Drive Front Right Encoder", motorRightMaster.getEncPosition());
		SmartDashboard.putNumber("Drive Rear Left Encoder", motorLeftSlave.getEncPosition());
		SmartDashboard.putNumber("Drive Rear Right Encoder", motorRightSlave.getEncPosition());
		SmartDashboard.putNumber("Drive Line Following Factor", getLineFollowingFactor());
		SmartDashboard.putNumber("Drive Line Following Graph", getLineFollowingFactor());
		SmartDashboard.putString("Drive Last Operation", lastOperation());
	}

	/**
	 * String description of the last operation done
	 * by the subsystem
	 */
	public String lastOperation() 
	{	
		return lastOperation;
	}
	
	/**
	 * Task that runs periodically to update the speed
	 * ramp on the Talon SRXs based on the current value
	 * of the Elevator's height.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class DampeningTask extends TimerTask
	{
		@Override
		public void run() 
		{
			//TODO make fancy calculation that dampens ramp based on elevator.
			motorLeftMaster.setCloseLoopRampRate(driveRampRate);
			motorRightMaster.setCloseLoopRampRate(driveRampRate);
		}	
	}
}