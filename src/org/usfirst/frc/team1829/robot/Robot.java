package org.usfirst.frc.team1829.robot;

import org.usfirst.frc.team1829.robot.command.DriveDiagnosticCommand;
import org.usfirst.frc.team1829.robot.subsystems.Conveyor;
import org.usfirst.frc.team1829.robot.subsystems.Drive;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import com.team1829.library.CarbonUI;
import com.team1829.library.CarbonUI.ControlType;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
	//Drive CAN ID/DIOs
	public static final int DRIVE_FRONT_LEFT = 2;			//CAN
	public static final int DRIVE_FRONT_RIGHT = 3;			//CAN
	public static final int DRIVE_REAR_LEFT = 4;			//CAN
	public static final int DRIVE_REAR_RIGHT = 5;			//CAN
	public static final int DRIVE_GYRO = 0;					//DIO
	//Turret PWM/DIOs
	public static final int TURRET_MOTOR = 0;				//PWM
	public static final int TURRET_ENCODER_A = 1;			//DIO
	public static final int TURRET_ENCODER_B = 2;			//DIO
	public static final int TURRET_LIMIT_PARALLEL = 3;		//DIO
	public static final int TURRET_LIMIT_PERPENDICULAR = 4;	//DIO
	//Elevator PWM/DIOs
	public static final int ELEVATOR_MOTOR = 1;				//PWM
	public static final int ELEVATOR_ENCODER_A = 5;			//DIO
	public static final int ELEVATOR_ENCODER_B = 6;			//DIO
	public static final boolean ELEVATOR_DIRECTION = false; //Inversion constant for elevator.
	public static final int ELEVATOR_LIMIT_TOP = 7;			//DIO
	public static final int ELEVATOR_LIMIT_BOT = 8;			//DIO
	//Conveyor PWM/DIOs
	public static final int CONVEYOR_MOTOR = 2;				//PWM
	public static final int CONVEYOR_ENCODER_A = 9;			//DIO
	public static final int CONVEYOR_ENCODER_B = 10;		//DIO
	public static final boolean CONVEYOR_DIRECTION = false; //Inversion constant for conveyor.
	//Jaw PWMs
	public static final int JAW_GRAB_MOTOR = 3;				//PWM
	public static final int JAW_FEED_MOTOR = 4;				//PWM
	public static final int JAW_LIMIT_RETRACT = 11;			//DIO
	public static final int JAW_LIMIT_ENCOUNTER = 12;		//DIO
	
	/**
	 * Custom UI handler.
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
        System.out.println("Robot.robotInit();");
        carbonDrive = new Drive();
        carbonTurret = new Turret();
        carbonElevator = new Elevator();
        carbonConveyor = new Conveyor();
        carbonJaw = new Jaw();
        ui = new CarbonUI();
        
        ui.addControl(UI_DRIVE_Y, ControlType.AXIS, 0, 1);
        ui.addControl(UI_DRIVE_R, ControlType.AXIS, 0, 0);
    }

    /**
     * Runs once before autonomous.
     */
    public void autonomousInit() 
    {
    	System.out.println("Robot.autonomousInit();");
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
    	System.out.println("Robot.teleopInit();");
    	Scheduler.getInstance().add(new DriveDiagnosticCommand());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit()
    {
    	System.out.println("Robot.disabledInit();");
    }
    
    /**
     * Runs continuously during periodic.
     */
    public void disabledPeriodic() 
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
	
	/**
	 * Returns the active UI handler.
	 * @return The active UI handler.
	 */
	public static CarbonUI getUI()
	{
		return ui;
	}
}
