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
 * Container-grabbing arm (boom).  This implements a 
 * PID control system to allow smooth movement.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 *
 */
public class Elevator extends Subsystem
{	
	/**
	 * Custom class that acts as a middle-man between
	 * the sensors, PID controllers, and the outputs.
	 */
	private ElevatorPIDAdapter elevatorAdapter;
	
	/**
	 * The PID controller for the elevator that controls
	 * the motor for vertical motion.
	 */
	private PIDController elevatorController;
	
	/**
	 * The Talon motor controller that we use for this
	 * subsystem.
	 */
	private Talon elevatorTalon;
	
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
	
	//TODO add actual PID values.
	private double elevatorP = 0;
	private double elevatorI = 0;
	private double elevatorD = 0;
	
	/**
	 * Initializes the Elevator subsystem and sets
	 * default values.
	 */
	public Elevator()
	{
		elevatorAdapter = new ElevatorPIDAdapter();
		elevatorController = new PIDController(elevatorP, 
											   elevatorI, 
											   elevatorD, 
											   elevatorAdapter, 
											   elevatorAdapter);
		elevatorTalon = new Talon(Robot.ELEVATOR_TALON);
		topLimit = new CarbonDigitalInput(Robot.ELEVATOR_LIMIT_TOP);
		botLimit = new CarbonDigitalInput(Robot.ELEVATOR_LIMIT_BOT);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		//TODO Initialize a default command for this subsystem.
	}
	
	/**
	 * Sets the PID constants for this subsystem.
	 * @param p Proportional value
	 * @param i Integral value
	 * @param d Derivative value
	 */
	public void setPID(double p, double i, double d)
	{
		elevatorP = p;
		elevatorI = i;
		elevatorD = d;
		
		elevatorController.setPID(elevatorP, elevatorI, elevatorD);
	}
	
	/**
	 * Sets the elevatorPIDController's setpoint.
	 * @param setpoint The new setpoint.
	 */
	public void setSetpoint(double setpoint)
	{
		elevatorController.setSetpoint(setpoint);
	}
	
	/**
	 * Returns the current P constant.
	 * @return The current P constant.
	 */
	public double getP()
	{
		return elevatorP = elevatorController.getP();
	}
	
	/**
	 * Returns the current I constant.
	 * @return The current I constant.
	 */
	public double getI()
	{
		return elevatorI = elevatorController.getI();
	}
	
	/**
	 * Returns the current D constant.
	 * @return The current D constant.
	 */
	public double getD()
	{
		return elevatorD = elevatorController.getD();
	}
	
	/**
	 * Implements both PIDSource and PIDOutput in order to 
	 * act as an interface to our PIDController.  Doing this
	 * allows us to have exact control over the feedback we
	 * give and the output we receive.
	 * @author Nick Mosher, Team 1829 Carbonauts Captain
	 *
	 */
	public class ElevatorPIDAdapter implements PIDSource, PIDOutput
	{

		/**
		 * The output of the PID calculations.  This is where
		 * we get the instructions to pass to a motor or
		 * other output object.
		 */
		public void pidWrite(double output) 
		{
			//TODO Receive PID output here.
		}

		/**
		 * The feedback input for the PID calculations.  This
		 * is where we pass sensor or other feedback data into
		 * the controller so it has a reference of how close
		 * it is to its goal.
		 */
		public double pidGet() 
		{	
			//TODO Send PID feedback here.
			return 0;
		}
		
	}
}
