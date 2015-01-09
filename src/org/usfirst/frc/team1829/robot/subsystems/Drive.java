package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Drive subsystem that is controlled by three unique PIDControllers
 * for X, Y, and Rotation motion.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Drive
{
	/**
	 * Used to differentiate the purpose of the PID data being
	 * passed through the DrivePIDAdapters.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public enum DrivePIDType 
	{
		X,
		Y,
		ROTATE
	}
	
	/**
	 * Front-left drive motor as a Talon.
	 */
	private Talon motorFrontLeft;
	
	/**
	 * Rear-left drive motor as a Talon.
	 */
	private Talon motorRearLeft;
	
	/**
	 * Front-right drive motor as a Talon.
	 */
	private Talon motorFrontRight;
	
	/**
	 * Rear-right drive motor as a Talon.
	 */
	private Talon motorRearRight;
	
	/**
	 * RobotDrive object.
	 */
	private RobotDrive driveTrain;
	
	/**
	 * Front-left drive encoder.
	 */
	private Encoder encoderFrontLeft;
	
	/**
	 * Rear-left drive encoder.
	 */
	private Encoder encoderRearLeft;
	
	/**
	 * Front-right drive encoder.
	 */
	private Encoder encoderFrontRight;
	
	/**
	 * Rear-right drive encoder.
	 */
	private Encoder encoderRearRight;
	
	/**
	 * Ultrasonic sensor.
	 * TODO find out if we're using ultrasonic.
	 */
	private Ultrasonic ultrasonic;
	
	/**
	 * RoboRio's on-board accelerometer.
	 */
	private BuiltInAccelerometer accelerometer;
	
	/**
	 * Drive gyro.
	 */
	private Gyro gyro;
	
	/**
	 * PID Controller that controls left-to-right motion in the
	 * case that a holonomic drive train is used.
	 */
	private PIDController xController;
	
	/**
	 * PID Controller that controls front-to-back motion.
	 */
	private PIDController yController;
	
	/**
	 * PID Controller that controls rotation motion.
	 */
	private PIDController rotateController;
	
	/**
	 * Gets X feedback data and sets X write data for the
	 * X PID Controller.
	 */
	private DrivePIDAdapter xAdapter;
	
	/**
	 * Gets Y feedback data and sets Y write data for the
	 * Y PID Controller.
	 */
	private DrivePIDAdapter yAdapter;
	
	/**
	 * Gets Rotation feedback data and sets Rotation write
	 * data for the Rotation PID Controller.
	 */
	private DrivePIDAdapter rotateAdapter;
	
	private double xP = 0.0;
	private double xI = 0.0;
	private double xD = 0.0;
	
	private double yP = 0.0;
	private double yI = 0.0;
	private double yD = 0.0;
	
	private double rotateP = 0.0;
	private double rotateI = 0.0;
	private double rotateD = 0.0;
	
	/**
	 * Setup the Drive system.
	 */
	public Drive() 
	{
		motorFrontLeft = new Talon(Robot.DRIVE_FRONT_LEFT);
		motorRearLeft = new Talon(Robot.DRIVE_REAR_LEFT);
		motorFrontRight = new Talon(Robot.DRIVE_FRONT_RIGHT);
		motorRearRight = new Talon(Robot.DRIVE_REAR_RIGHT);
		driveTrain = new RobotDrive(motorFrontLeft, motorRearLeft, motorFrontRight, motorRearRight);
		
		encoderFrontLeft = new Encoder(Robot.DRIVE_ENCODER_FRONT_LEFT_A, Robot.DRIVE_ENCODER_FRONT_LEFT_B);
		encoderFrontRight = new Encoder(Robot.DRIVE_ENCODER_FRONT_RIGHT_A, Robot.DRIVE_ENCDOER_FRONT_RIGHT_B);
		encoderRearLeft = new Encoder(Robot.DRIVE_ENCODER_REAR_LEFT_A, Robot.DRIVE_ENCODER_REAR_LEFT_B);
		encoderRearRight = new Encoder(Robot.DRIVE_ENCODER_REAR_RIGHT_A, Robot.DRIVE_ENCDOER_REAR_RIGHT_B);
		ultrasonic = new Ultrasonic(Robot.DRIVE_ULTRASONIC_A, Robot.DRIVE_ULTRASONIC_B);
		accelerometer = new BuiltInAccelerometer();
		gyro = new Gyro(Robot.DRIVE_GYRO);
		
		xAdapter = new DrivePIDAdapter(DrivePIDType.X);
		yAdapter = new DrivePIDAdapter(DrivePIDType.Y);
		rotateAdapter = new DrivePIDAdapter(DrivePIDType.ROTATE);
		
		xController = new PIDController(xP, xI, xD, xAdapter, xAdapter);
		yController = new PIDController(yP, yI, yD, yAdapter, yAdapter);
		rotateController = new PIDController(rotateP, rotateI, rotateD, rotateAdapter, rotateAdapter);
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
		//setDefaultCommand(new Command());
	}
	
	/**
	 * Sets the PID values for the X PIDController.
	 * @param p P for X.
	 * @param i I for X.
	 * @param d D for X.
	 */
	public void setXPID(double p, double i, double d)
	{
		xP = p;
		xI = i;
		xD = d;
		
		xController.setPID(xP, xI, xD);
	}
	
	/**
	 * Sets the X PIDController's target.
	 * @param setpoint X PIDController target.
	 */
	public void setXSetpoint(double setpoint)
	{
		xController.setSetpoint(setpoint);
	}
	
	/**
	 * Returns the current P value of the X PIDController.
	 * @return the current P value of the X PIDController.
	 */
	public double getXP()
	{
		return xP = xController.getP();
	}
	
	/**
	 * Returns the current I value of the X PIDController.
	 * @return the current I value of the X PIDController.
	 */
	public double getXI()
	{
		return xI = xController.getI();
	}
	
	/**
	 * Returns the current D value of the X PIDController.
	 * @return the current D value of the X PIDController.
	 */
	public double getXD()
	{
		return xD = xController.getD();
	}
	
	/**
	 * Sets the PID values for the Y PIDController.
	 * @param p P for Y.
	 * @param i I for Y.
	 * @param d D for Y.
	 */
	public void setYPID(double p, double i, double d)
	{
		yP = p;
		yI = i;
		yD = d;
		
		yController.setPID(yP, yI, yD);
	}
	
	/**
	 * Sets the Y PIDController's target.
	 * @param setpoint Y PIDController target.
	 */
	public void setYSetpoint(double setpoint)
	{
		yController.setSetpoint(setpoint);
	}
	
	/**
	 * Returns the current P value of the Y PIDController.
	 * @return the current P value of the Y PIDController.
	 */
	public double getYP()
	{
		return yP = yController.getP();
	}
	
	/**
	 * Returns the current I value of the Y PIDController.
	 * @return the current I value of the Y PIDController.
	 */
	public double getYI()
	{
		return yI = yController.getI();
	}
	
	/**
	 * Returns the current D value of the Y PIDController.
	 * @return the current D value of the Y PIDController.
	 */
	public double getYD()
	{
		return yD = yController.getD();
	}
	
	/**
	 * Sets the PID values for the Rotation PIDController.
	 * @param p P for Rotation.
	 * @param i I for Rotation.
	 * @param d D for Rotation.
	 */
	public void setRotatePID(double p, double i, double d)
	{
		rotateP = p;
		rotateI = i;
		rotateD = d;
		
		rotateController.setPID(rotateP, rotateI, rotateD);
	}
	
	/**
	 * Sets the Rotation PIDController's target.
	 * @param setpoint Rotation PIDController target.
	 */
	public void setRotateSetpoint(double setpoint)
	{
		rotateController.setSetpoint(setpoint);
	}
	
	/**
	 * Returns the current P value of the Rotation PIDController.
	 * @return the current P value of the Rotation PIDController.
	 */
	public double getRotateP()
	{
		return rotateP = rotateController.getP();
	}
	
	/**
	 * Returns the current I value of the Rotation PIDController.
	 * @return the current I value of the Rotation PIDController.
	 */
	public double getRotateI()
	{
		return rotateI = rotateController.getI();
	}
	
	/**
	 * Returns the current D value of the Rotation PIDController.
	 * @return the current D value of the Rotation PIDController.
	 */
	public double getRotateD()
	{
		return rotateD = rotateController.getD();
	}
	
	/**
	 * Implements both PIDSource and PIDOutput in order to make
	 * an exchange point for feeding data to a PIDController and
	 * reading data back from one.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class DrivePIDAdapter implements PIDSource, PIDOutput
	{	
		private DrivePIDType type;
		
		/**
		 * Constructs a DrivePIDAdapter that gets and sets data
		 * for the PIDController that corresponds to 'type'.
		 * @param type The type of PID data being exchanged.
		 */
		public DrivePIDAdapter(DrivePIDType type)
		{
			this.type = type;
		}

		/**
		 * The output for the calculations of the PIDControllers.  This
		 * is where a motor speed, voltage, position, etc. might be
		 * returned to us to forward to the motors.  The PIDController
		 * that the data originates from is specified by the DrivePIDType
		 * enumeration.
		 * @param output The returned value from a PIDController.
		 */
		public void pidWrite(double output) 
		{
			if(type == DrivePIDType.X)
			{
				//TODO Do whatever it is you do with X instructions.
			}
			else if(type == DrivePIDType.Y)
			{
				//TODO Do whatever it is you do with Y instructions.
			}
			else if(type == DrivePIDType.ROTATE)
			{
				//TODO Do whatever it is you do with Rotate instructions.
			}
		}

		/**
		 * This is where we pass our feedback data into the PIDControllers.
		 * If one aspect of motion is directly tied to a sensor (e.g. the
		 * rotation to a gyro), then we pass that sensor reading back to
		 * the PIDController here.  If readings from multiple sensors play
		 * into consideration for one aspect of motion, then calculations
		 * that balance those values into the desired feedback happen here
		 * (e.g. use two ultrasonic sensors to drive parallel to a wall,
		 * so take the difference of one range minus the other and pass
		 * the result as feedback).
		 * @return The feedback variable for the respective PIDController.
		 */
		public double pidGet() 
		{
			if(type == DrivePIDType.X)
			{
				//TODO Return feedback variable for X.
			}
			else if(type == DrivePIDType.Y)
			{
				//TODO Return feedback variable for y.
			}
			else if(type == DrivePIDType.ROTATE)
			{
				//TODO Return feedback variable for rotate.
			}
			return 0;
		}
	}
}