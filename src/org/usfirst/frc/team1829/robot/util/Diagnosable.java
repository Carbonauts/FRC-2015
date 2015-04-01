package org.usfirst.frc.team1829.robot.util;

/**
 * Interface intended to be implemented by all subsystems
 * and possibly commands.
 * @author Nick Mosher, Team 1829 Carbonauts Captain
 */
public interface Diagnosable 
{
	/**
	 * Diagnosable objects return statuses or states here.
	 * Examples may be sensor values.
	 * @return Status or condition of diagnosable object.
	 */
	String getStatus();
	
	/**
	 * Gathers data the same way as getFeedback() but instead
	 * pushes the data to the SmartDashboard.
	 */
	void updateSmartDS();
	
	/**
	 * Diagnosable objects need to keep track of the last significant
	 * operation that they did and return it in this method.
	 * @return The name of the last operation this diagnosable
	 * object did.
	 */
	String lastOperation();
}