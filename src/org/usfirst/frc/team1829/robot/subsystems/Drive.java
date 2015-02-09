package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;
import org.usfirst.frc.team1829.robot.command.OperatorDriveCommand;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that maneuvers the robot around the field.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Drive extends Subsystem
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
	 * The default command for this subsystem.
	 */
	private OperatorDriveCommand defaultCommand;
	
	/**
	 * Analog input to read a value from our
	 * line-following device (an arduino
	 * that outputs the analog value).
	 */
	private AnalogInput lineFollower;
	
	/**
	 * RoboRio's on-board accelerometer.
	 */
	private BuiltInAccelerometer accelerometer;
	
	/**
	 * Drive gyro.
	 */
	private Gyro gyro;
	
	//TODO set real PID values
	/**
	 * Proportional value for the drive motors.
	 */
	private double driveP = 0;
	
	/**
	 * Integral value for the drive motors.
	 */
	private double driveI = 0;
	
	/**
	 * Derivative value for the drive motors.
	 */
	private double driveD = 0;
	
	/**
	 * Setup the Drive system.
	 */
	public Drive() 
	{
		motorFrontLeft = new CANTalon(Robot.DRIVE_FRONT_LEFT);
		motorRearLeft = new CANTalon(Robot.DRIVE_REAR_LEFT);
		motorFrontRight = new CANTalon(Robot.DRIVE_FRONT_RIGHT);
		motorRearRight = new CANTalon(Robot.DRIVE_REAR_RIGHT);
		driveTrain = new RobotDrive(motorFrontLeft, motorRearLeft, motorFrontRight, motorRearRight);
		
		defaultCommand = new OperatorDriveCommand();
		
		ultrasonic = new Ultrasonic(Robot.DRIVE_ULTRASONIC_A, Robot.DRIVE_ULTRASONIC_B);
		accelerometer = new BuiltInAccelerometer();
		gyro = new Gyro(Robot.DRIVE_GYRO);
		
		motorFrontLeft.changeControlMode(ControlMode.Speed);
		motorFrontLeft.setPID(driveP, driveI, driveD);
		
		motorRearLeft.changeControlMode(ControlMode.Speed);
		motorRearLeft.setPID(driveP, driveI, driveD);
		
		motorFrontRight.changeControlMode(ControlMode.Speed);
		motorFrontRight.setPID(driveP, driveI, driveD);
		
		motorRearRight.changeControlMode(ControlMode.Speed);
		motorRearRight.setPID(driveP, driveI, driveD);
		
		ultrasonic.setAutomaticMode(true); //Ensures that multiple ultrasonic sensors don't interfere
		
		//driveTrain.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
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
		setDefaultCommand(defaultCommand);
	}
	
	/**
	 * Returns a modifier that represents the needed
	 * rotation offset in order to follow a line.
	 */
	public double getLineFollowingFactor()
	{
		//TODO Add analog input reading + calculation.
		/*
		 * This method should convert the analog reading
		 * into a range close to [-1,1], which would then
		 * be added to the 'x' value in the arcade
		 * drive to influence rotation.
		 */
	}
	
	public void stop()
	{
		driveTrain.stopMotor();
	}
	
	public void driveArcade(double y, double x)
	{
		driveTrain.arcadeDrive(y, x);
	}
}