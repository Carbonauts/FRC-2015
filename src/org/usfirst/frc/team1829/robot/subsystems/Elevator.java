package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import com.team1829.library.CarbonDigitalInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls the vertical movement of the
 * Container-grabbing arm (conveyor).  This implements a 
 * PID control system to allow smooth movement.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Elevator extends Subsystem
{
	/*
	 * These constants define preset encoder locations
	 * of different scoring zones.
	 * TODO Measure and input actual positions.
	 */
	public static final int LEVEL1_POSITION = 0;
	public static final int LEVEL2_POSITION = 0;
	public static final int LEVEL3_POSITION = 0;
	public static final int LEVEL4_POSITION = 0;
	public static final int LEVEL5_POSITION = 0;
	
	public enum Mode
	{
		POSITION,
		SPEED
	}
	
	public enum Position
	{
		LEVEL1,
		LEVEL2,
		LEVEL3,
		LEVEL4,
		LEVEL5
	}
	
	/**
	 * The Talon motor controller that we use for this
	 * subsystem.
	 */
	private Talon elevatorMotor;
	
	/**
	 * The encoder attached to the subsystem to provide
	 * feedback for the PID controller.
	 */
	private Encoder elevatorEncoder;
	
	/**
	 * Limit switch that triggers when the elevator is
	 * at maximum height.
	 */
	private CarbonDigitalInput topLimit;
	
	/**
	 * Limit switch that triggers when the elevator is
	 * at minimum height.
	 */
	private CarbonDigitalInput botLimit;
	
	/**
	 * PID Control loop for the elevator while in position mode.
	 */
	private PIDController elevatorPositionController;
	
	/**
	 * PID Control loop for the elevator while in speed mode.
	 */
	private PIDController elevatorSpeedController;
	
	/**
	 * PID input/output adapter for the elevator
	 * PIDController while in position mode.
	 */
	private ElevatorPIDAdapter elevatorPositionAdapter;
	
	/**
	 * PID input/output adapter for the elevator
	 * PIDController while in speed mode.
	 */
	private ElevatorPIDAdapter elevatorSpeedAdapter;
	
	/**
	 * The mode that the elevator is operating in, either
	 * Mode.SPEED or Mode.POSITION.
	 */
	private Mode elevatorMode = Mode.POSITION;
	
	private boolean pidEnabled = false; //TODO Flag to remember to enable PID at some point.
	
	//TODO add actual PID values.
	private static double elevatorSpeedP = 0;
	private static double elevatorSpeedI = 0;
	private static double elevatorSpeedD = 0;
	
	private static double elevatorPositionP = 0;
	private static double elevatorPositionI = 0;
	private static double elevatorPositionD = 0;
	
	/**
	 * Initializes the Elevator subsystem and sets
	 * default values.
	 */
	public Elevator()
	{
		elevatorMotor = new Talon(Robot.ELEVATOR_MOTOR);
		elevatorEncoder = new Encoder(Robot.ELEVATOR_ENCODER_A, 
									  Robot.ELEVATOR_ENCODER_B,
									  Robot.ELEVATOR_DIRECTION);
		topLimit = new CarbonDigitalInput(Robot.ELEVATOR_LIMIT_TOP, false);
		botLimit = new CarbonDigitalInput(Robot.ELEVATOR_LIMIT_BOT, false);
		
		elevatorPositionAdapter = new ElevatorPIDAdapter(Mode.POSITION);
		elevatorSpeedAdapter = new ElevatorPIDAdapter(Mode.SPEED);
		elevatorPositionController = new PIDController(elevatorPositionP,
													   elevatorPositionI,
													   elevatorPositionD,
													   elevatorPositionAdapter,
													   elevatorPositionAdapter);
		elevatorSpeedController = new PIDController(elevatorSpeedP,
													elevatorSpeedI,
													elevatorSpeedD,
													elevatorSpeedAdapter,
													elevatorSpeedAdapter);
		
		//Limits the max speed of the PID outputs (and therefore the motor speed).
		elevatorPositionController.setOutputRange(-1.0, 1.0);
		elevatorSpeedController.setOutputRange(-1.0, 1.0);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		//TODO Initialize a default command for this subsystem.
	}
	
	/**
	 * Sets the mode that the elevator will operate in.
	 * @param mode Position or Speed mode.
	 */
	public void setMode(Mode mode)
	{
		/*
		 * If the mode of the elevator is already
		 * what this method call wants, then
		 * ignore it.
		 */
		if(mode == elevatorMode)
		{
			return;
		}
		
		elevatorPositionController.reset();
		elevatorSpeedController.reset();
		
		if(mode == Mode.POSITION)
		{
			elevatorPositionController.enable();
		}
		else if(mode == Mode.SPEED)
		{
			elevatorSpeedController.enable();
		}
		elevatorMode = mode;
	}
	
	/**
	 * Gets the limit switch reading of the top limit
	 * of the elevator.
	 * @return True if the elevator is at the top,
	 * false otherwise.
	 */
	public boolean isAtTop()
	{
		return topLimit.get();
	}
	
	/**
	 * Gets the limit switch reading of the bottom
	 * limit of the elevator.
	 * @return True if the elevator is at the bottom,
	 * false otherwise.
	 */
	public boolean isAtBottom()
	{
		return botLimit.get();
	}
	
	/**
	 * Moves the subsystem up using speed-based PID.
	 */
	public void moveUp()
	{
		moveAtSpeed(0.4); //TODO Replace with a speed unit
	}
	
	/**
	 * Moves the subsystem down using speed-based PID.
	 */
	public void moveDown()
	{
		moveAtSpeed(-0.4); //TODO Replace with a nice speed unit
	}
	
	/**
	 * Moves the subsystem using speed-based PID.
	 * @param speed
	 */
	public void moveAtSpeed(double speed)
	{
		setMode(Mode.SPEED);
		elevatorSpeedController.setSetpoint(speed);
	}
	
	/**
	 * Sets the subsystem to move to the desired position.
	 * @param position The target position.
	 */
	public void moveToPosition(Position position)
	{
		setMode(Mode.POSITION);
		elevatorPositionController.setSetpoint(getCoordinatesFor(position));
	}
	
	/**
	 * Turns off the PID control (so that the PID loops
	 * do not continue to set motor outputs) and manually
	 * sets the motor speed to 0.
	 */
	public void stop()
	{
		setPIDEnabled(false);
		elevatorMotor.set(0.0);
	}
	
	/**
	 * Resets the encoder position value to 0.  This has
	 * no effect on the rate measure
	 */
	public void resetPosition()
	{
		elevatorEncoder.reset();
	}
	
	/**
	 * Sets whether the elevator is currently controlled by PID.
	 * @param enabled PID Enabled.
	 */
	public void setPIDEnabled(boolean enabled)
	{
		/*
		 * If the subsystem PID setting is already at
		 * whatever this method call is ordering, then
		 * ignore it.
		 */
		if(pidEnabled == enabled)
		{
			return;
		}
		
		/*
		 * If we're disabling PID, we want to turn off
		 * the PID controllers (which reset does), if
		 * we're enabling PID, then we want to reset
		 * them anyway because of any built-up error
		 * processing they may have done.
		 */
		elevatorPositionController.reset();
		elevatorSpeedController.reset();
		
		/*
		 * Enable only the correct PID controller based
		 * on what mode the subsystem is in.  We do not
		 * need a case for disabled because the controllers
		 * are reset in this method anyway, which includes
		 * disabling them.
		 */
		if(enabled)
		{
			if(elevatorMode == Mode.POSITION)
			{
				elevatorPositionController.enable();
			}
			else if(elevatorMode == Mode.SPEED)
			{
				elevatorSpeedController.enable();
			}
		}
		pidEnabled = enabled;
	}
	
	/**
	 * Returns encoder position units for a particular position.
	 * @param position The position to retrieve units for.
	 * @return Encoder position units for a particular position.
	 */
	public int getCoordinatesFor(Position position)
	{
		int returnIs = -1;
		switch(position)
		{
		case LEVEL1:
			returnIs = LEVEL1_POSITION;
		case LEVEL2:
			returnIs = LEVEL2_POSITION;
		case LEVEL3:
			returnIs = LEVEL3_POSITION;
		case LEVEL4:
			returnIs = LEVEL4_POSITION;
		case LEVEL5:
			returnIs = LEVEL5_POSITION;
		}
		return returnIs;
	}
	
	/**
	 * Acts as a middleman between the subsystem and the PIDController.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 */
	public class ElevatorPIDAdapter implements PIDSource, PIDOutput
	{
		private Mode mode;
		
		public ElevatorPIDAdapter(Mode mode)
		{
			this.mode = mode;
		}
		
		/**
		 * Allows the PIDController to write to the motor ONLY IF:
		 * 1) The PID is enabled.
		 * 2) The PID MODE that this adapter serves is the currently enabled one.
		 */
		public void pidWrite(double output) 
		{
			if(pidEnabled && mode == elevatorMode)
			{
				elevatorMotor.set(output);
			}
		}

		public double pidGet() 
		{	
			double toReturn = 0.0;
			
			if(mode == Mode.POSITION)
			{
				toReturn = elevatorEncoder.getDistance();
			}
			else if(mode == Elevator.Mode.SPEED)
			{
				toReturn = elevatorEncoder.getRate();
			}
			return toReturn;
		}
	}
}