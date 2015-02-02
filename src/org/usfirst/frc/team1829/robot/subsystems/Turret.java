package org.usfirst.frc.team1829.robot.subsystems;

import org.usfirst.frc.team1829.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that rotates the entire upper structure of
 * the robot to face the row of CONTAINERS on the step.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public class Turret extends Subsystem
{
	private TurretPIDAdapter turretAdapter;
	private PIDController turretController;
	private Talon turretMotor;
	private Encoder turretEncoder;
	
	private double turretP = 0;
	private double turretI = 0;
	private double turretD = 0;
	
	public Turret()
	{
		turretAdapter = new TurretPIDAdapter();
		turretController = new PIDController(turretP, turretI, turretD, turretAdapter, turretAdapter);
		turretMotor = new Talon(Robot.TURRET_MOTOR);
		turretEncoder = new Encoder(Robot.TURRET_ENCODER_A, Robot.TURRET_ENCODER_B);
	}
	
	@Override
	protected void initDefaultCommand() 
	{
		
	}
	
	public void setPID(double p, double i, double d)
	{
		turretP = p;
		turretI = i;
		turretD = d;
		
		turretController.setPID(turretP, turretI, turretD);
	}
	
	public void setSetpoint(double setpoint)
	{
		turretController.setSetpoint(setpoint);
	}
	
	public double getP()
	{
		return turretP = turretController.getP();
	}
	
	public double getI()
	{
		return turretI = turretController.getI();
	}
	
	public double getD()
	{
		return turretD = turretController.getD();
	}
	
	public class TurretPIDAdapter implements PIDSource, PIDOutput
	{
		public TurretPIDAdapter()
		{
			
		}

		public void pidWrite(double output) 
		{
			turretMotor.set(output);
		}

		public double pidGet() 
		{
			return 0;
		}
	}
}
