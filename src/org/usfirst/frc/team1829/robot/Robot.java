package org.usfirst.frc.team1829.robot;

import org.usfirst.frc.team1829.robot.subsystems.Conveyor;
import org.usfirst.frc.team1829.robot.subsystems.Drive;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

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
	//Unique control identifiers
	public static final String UI_DRIVE_Y = "0x00";
	public static final String UI_DRIVE_X = "0x01";
	public static final String UI_DRIVE_R = "0x02";
	
	//TODO add real PWM channel numbers.
	//Drive PWMs
	public static final int DRIVE_FRONT_LEFT = 0;
	public static final int DRIVE_FRONT_RIGHT = 0;
	public static final int DRIVE_REAR_LEFT = 0;
	public static final int DRIVE_REAR_RIGHT = 0;
	public static final int DRIVE_ULTRASONIC_A = 0;
	public static final int DRIVE_ULTRASONIC_B = 0;
	public static final int DRIVE_GYRO = 0;
	//Turret PWMs
	public static final int TURRET_MOTOR = 0;
	public static final int TURRET_ENCODER_A = 0;
	public static final int TURRET_ENCODER_B = 0;
	public static final int TURRET_LIMIT_LEFT = 0;
	public static final int TURRET_LIMIT_RIGHT = 0;
	//Elevator PWMs
	public static final int ELEVATOR_MOTOR = 0;
	public static final int ELEVATOR_LIMIT_TOP = 0;
	public static final int ELEVATOR_LIMIT_BOT = 0;
	//Conveyor PWMs
	public static final int CONVEYOR_MOTOR = 0;
	public static final int CONVEYOR_ENCODER_A = 0;
	public static final int CONVEYOR_ENCODER_B = 0;
	//Jaw PWMs
	public static final int JAW_GRAB_MOTOR = 0;
	public static final int JAW_FEED_MOTOR = 0;
	public static final int JAW_LIMIT = 0;
	
	/**
	 * The active UI element for the Robot.
	 */
	private static CarbonUI ui;
	
	/**
	 * The active instance of the Drive system.
	 */
	private static Drive carbonDrive;
	
	/**
	 * The active instance of the Turret system.
	 */
	private static Turret carbonTurret;
	
	/**
	 * The active instance of the Elevator system.
	 */
	private static Elevator carbonElevator;
	
	/**
	 * The active instance of the Conveyor system.
	 */
	private static Conveyor carbonConveyor;
	
	/**
	 * The active instance of the Jaw system.
	 */
	private static Jaw carbonJaw;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
        ui = new CarbonUI();
        ui.addControl(UI_DRIVE_Y, ControlType.AXIS, 2, 1);
        ui.addControl(UI_DRIVE_R, ControlType.AXIS, 3, 1);
        
        carbonDrive = new Drive();
        carbonTurret = new Turret();
        carbonElevator = new Elevator();
        carbonConveyor = new Conveyor();
        carbonJaw = new Jaw();
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
    
    /**
	 * Returns the active UI.
	 * @return the active UI.
	 */
	public static CarbonUI getUI()
    {
    	return ui;
    }
	
	/**
	 * Returns the instance of the drive system.
	 * @return The instance of the drive system.
	 */
	public static Drive getDrive()
	{
		return carbonDrive;
	}
	
	/**
	 * Returns the instance of the turret system.
	 * @return The instance of the turret system.
	 */
	public static Turret getTurret()
	{
		return carbonTurret;
	}
	
	/**
	 * Returns the instance of the elevator system.
	 * @return The instance of the elevator system.
	 */
	public static Elevator getElevator()
	{
		return carbonElevator;
	}
	
	/**
	 * Returns the instance of the conveyor system.
	 * @return The instance of the conveyor system.
	 */
	public static Conveyor getConveyor()
	{
		return carbonConveyor;
	}
	
	/**
	 * Returns the instance of the jaw system.
	 * @return The instance of the jaw system.
	 */
	public static Jaw getJaw()
	{
		return carbonJaw;
	}
}
