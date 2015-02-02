package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyor is the subsystem that transports all CONTAINERS
 * from the jaw into the robot for storage.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Conveyor extends Subsystem
{
	private Talon conveyorMotor;
	private Encoder conveyorEncoder;
	
	public Conveyor()
	{
		conveyorMotor = new Talon(Robot.CONVEYOR_MOTOR);
		conveyorEncoder = new Encoder(Robot.CONVEYOR_ENCODER_A, Robot.CONVEYOR_ENCODER_B);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
}
