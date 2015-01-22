package org.usfirst.frc.team1829.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.team1829.library.CarbonUI;
import com.team1829.library.CarbonUI.ControlType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	//TODO add real PWM channel numbers.
	public static final int DRIVE_FRONT_LEFT = 0;
	public static final int DRIVE_FRONT_RIGHT = 0;
	public static final int DRIVE_REAR_LEFT = 0;
	public static final int DRIVE_REAR_RIGHT = 0;
	public static final int DRIVE_ENCODER_FRONT_LEFT_A = 0;
	public static final int DRIVE_ENCODER_FRONT_LEFT_B = 0;
	public static final int DRIVE_ENCODER_FRONT_RIGHT_A = 0;
	public static final int DRIVE_ENCDOER_FRONT_RIGHT_B = 0;
	public static final int DRIVE_ENCODER_REAR_LEFT_A = 0;
	public static final int DRIVE_ENCODER_REAR_LEFT_B = 0;
	public static final int DRIVE_ENCODER_REAR_RIGHT_A = 0;
	public static final int DRIVE_ENCDOER_REAR_RIGHT_B = 0;
	public static final int DRIVE_ULTRASONIC_A = 0;
	public static final int DRIVE_ULTRASONIC_B = 0;
	public static final int DRIVE_GYRO = 0;
	
	public static final int ELEVATOR_TALON = 0;
	public static final int ELEVATOR_LIMIT_TOP = 0;
	public static final int ELEVATOR_LIMIT_BOT = 0;
	
	/**
	 * The active UI element for the Robot.
	 */
	private static CarbonUI ui;

	/**
	 * Returns the active UI.
	 * @return the active UI.
	 */
	public static CarbonUI getUI()
    {
    	return ui;
    }
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        ui = new CarbonUI();
        ui.addControl("Shooter", ControlType.BUTTON, 1, 1);
        ui.addControl("Pickup", ControlType.AXIS, 1, 2);
    }
	
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}

    public void autonomousInit() 
    {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called at the beginning of teleop.
     */
    public void teleopInit() 
    {

    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
        LiveWindow.run();
    }
}
