package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.command.OperatorDriveCommand;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that maneuvers the robot around the field.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Drive extends Subsystem implements Diagnosable
{	
	/**
	 * Front-left drive motor as a Talon.
	 */
	private CANTalon motorFrontLeft;
	
	/**
	 * Rear-left drive motor as a Talon.
	 */
	private CANTalon motorRearLeft;
	
	/**
	 * Front-right drive motor as a Talon.
	 */
	private CANTalon motorFrontRight;
	
	/**
	 * Rear-right drive motor as a Talon.
	 */
	private CANTalon motorRearRight;
	
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
	
	/**
	 * Drive gyro.
	 */
	private Gyro gyro;
	
	//TODO set real PID values
	/**
	 * Proportional value for the drive motors.
	 */
	private double driveP = 5;
	
	/**
	 * Integral value for the drive motors.
	 */
	private double driveI = 0;
	
	/**
	 * Derivative value for the drive motors.
	 */
	private double driveD = 0;
	
	/**
	 * The last operation the drive subsystem did.
	 */
	private String lastOperation;
	
	/**
	 * Setup the Drive system.
	 */
	public Drive() 
	{
		motorFrontLeft = new CANTalon(Robot.DRIVE_FRONT_LEFT);
		motorRearLeft = new CANTalon(Robot.DRIVE_REAR_LEFT);
		motorFrontRight = new CANTalon(Robot.DRIVE_FRONT_RIGHT);
		motorRearRight = new CANTalon(Robot.DRIVE_REAR_RIGHT);
		
		setDriveMode(CANTalon.ControlMode.PercentVbus);
		setPID(driveP, driveI, driveD);

		driveTrain = new RobotDrive(motorFrontLeft, motorRearLeft, motorFrontRight, motorRearRight);
		
		gyro = new Gyro(Robot.DRIVE_GYRO);
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
	
	public void stop()
	{
		driveTrain.stopMotor();
	}
	
	public void driveArcade(double y, double x)
	{
		driveTrain.arcadeDrive(y, x);
	}
	
	public void setPID(double p, double i, double d)
	{
		motorFrontLeft.setPID(p, i, d);
		motorRearLeft.setPID(p, i, d);
		motorFrontRight.setPID(p, i, d);
		motorRearRight.setPID(p, i, d);
	}
	
	public void setDriveMode(CANTalon.ControlMode mode)
	{
		motorFrontLeft.changeControlMode(mode);
		motorRearLeft.changeControlMode(mode);
		motorFrontRight.changeControlMode(mode);
		motorRearRight.changeControlMode(mode);
	}

	public String getFeedback() 
	{	
		StringBuffer encoderReadings = new StringBuffer("[" + getName() + "] EncoderPos:");
		encoderReadings.append(" (FL-").append(motorFrontLeft.getEncPosition());
		encoderReadings.append(") (FR-").append(motorFrontRight.getEncPosition());
		encoderReadings.append(") (RL-").append(motorRearLeft.getEncPosition());
		encoderReadings.append(") (RR-").append(motorRearRight.getEncPosition()).append(")");
		return encoderReadings.toString();
	}

	public String lastOperation() 
	{	
		return lastOperation;
	}
}