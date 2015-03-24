package org.usfirst.frc.team1829.robot;

import org.usfirst.frc.team1829.robot.command.*;
import org.usfirst.frc.team1829.robot.subsystems.Conveyer;
import org.usfirst.frc.team1829.robot.subsystems.Drive;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;
import org.usfirst.frc.team1829.robot.subsystems.Feeder;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import com.team1829.library.CarbonUI;
import com.team1829.library.CarbonUI.ControlType;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	//Start temp stuff
	public static final String UI_DRIVE_LINE = "0x002";
	public static final String UI_DRIVE_DAMPENER = "0x0021";
	public static final String UI_CANCEL_LINE = "0x0022";
	public static final String UI_START_LINE = "0x0023";
	//End temp stuff
	public static final String UI_ELEV_Y = "0x03";
	public static final String UI_TURRET_PERPENDICULAR = "0x04";
	public static final String UI_TURRET_PARALLEL = "0x05";
	public static final String UI_ELEVATOR_POS1 = "0x06";
	public static final String UI_ELEVATOR_POS2 = "0x07";
	public static final String UI_ELEVATOR_POS3 = "0x08";
	
	//Drive CAN ID/DIOs
	public static final int DRIVE_FRONT_RIGHT = 1;			//CAN
	public static final int DRIVE_FRONT_LEFT = 2;			//CAN
	public static final int DRIVE_REAR_RIGHT = 3;			//CAN
	public static final int DRIVE_REAR_LEFT = 4;			//CAN
	public static final int DRIVE_LINE = 0;					//Analog  <- CORRECT VALUE
	//Turret PWM/DIOs
	public static final int TURRET_MOTOR = 0;				//PWM
	public static final int TURRET_LIMIT_PARALLEL = 3;		//DIO
	public static final int TURRET_LIMIT_PERPENDICULAR = 4;	//DIO
	//Elevator PWM/DIOs
	public static final int ELEVATOR_MOTOR = 2;				//PWM     <- CORRECT VALUE
	public static final int ELEVATOR_ULTRA = 1;				//Analog  <- CORRECT VALUE
	public static final boolean ELEVATOR_DIRECTION = false; //Inversion constant for elevator.
	public static final int ELEVATOR_LIMIT_TOP = 0;			//DIO     <- CORRECT VALUE
	public static final int ELEVATOR_LIMIT_BOT = 1;			//DIO     <- CORRECT VALUE
	//Conveyor PWM/DIOs
	public static final int CONVEYOR_MOTOR = 5;				//PWM
	public static final int CONVEYOR_ENCODER_A = 9;			//DIO
	public static final int CONVEYOR_ENCODER_B = 10;		//DIO
	public static final boolean CONVEYOR_DIRECTION = false; //Inversion constant for conveyor.
	//Jaw PWMs
	public static final int JAW_GRAB_MOTOR = 3;				//PWM
	public static final int JAW_FEED_MOTOR = 4;				//PWM
	public static final int JAW_LIMIT_RETRACT = 11;			//DIO
	public static final int JAW_LIMIT_EXTENT = 7;                    //TODO set DIO
	public static final int JAW_LIMIT_CLAMP = 8;                     //TODO set DIO
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
	 * The active instance of the Conveyer system.
	 */
	private static Conveyer carbonConveyer;
	
	/**
	 * The active instance of the Jaw system.
	 */
	private static Jaw carbonJaw;
	
	/**
	 * The active instance of the Feeder system.
	 */
	private static Feeder carbonFeeder;
	
	/*
	 * Initialize command references
	 */
	private TurretToPerpendicularCommand turretPerpCommand;
	private TurretToParallelCommand turretParaCommand;
	private ElevatorToPositionCommand elevPosCommand;
	private DriveOnLineCommand lineCommand;
	
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
        carbonConveyer = new Conveyer();
        carbonJaw = new Jaw();
        ui = new CarbonUI();
        
        turretPerpCommand = new TurretToPerpendicularCommand();
        turretParaCommand = new TurretToParallelCommand();
        elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.AUTO);
        lineCommand = new DriveOnLineCommand();
        
        ui.addControl(UI_DRIVE_Y, ControlType.AXIS, 0, 1);
        ui.addControl(UI_DRIVE_R, ControlType.AXIS, 0, 0);
        ui.addControl(UI_ELEV_Y, ControlType.AXIS, 0, 2);
        ui.addControl(UI_TURRET_PERPENDICULAR, ControlType.BUTTON, 0, 2);
        ui.addControl(UI_TURRET_PARALLEL, ControlType.BUTTON, 0, 4);
        //ui.addControl(UI_ELEVATOR_POS1, ControlType.BUTTON, 0, 8);
        ui.addControl(UI_ELEVATOR_POS2, ControlType.BUTTON, 0, 7);
        //ui.addControl(UI_ELEVATOR_POS3, ControlType.BUTTON, 0, 9);
        ui.addControl(UI_DRIVE_LINE, ControlType.BUTTON, 0, 8);
        ui.addControl(UI_DRIVE_DAMPENER, ControlType.AXIS, 0, 3);
        ui.addControl(UI_CANCEL_LINE, ControlType.BUTTON, 0, 10);
        ui.addControl(UI_START_LINE, ControlType.BUTTON, 0, 9);
    }

    /**
     * Runs once before autonomous.
     */
    public void autonomousInit() 
    {
    	System.out.println("Robot.autonomousInit();");
    	Scheduler.getInstance().add(new CheckSubsystemsCommand());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
        Scheduler.getInstance().run();
        updateSubsystemDSOutputs();
    }
    
    /**
     * This function is called at the beginning of teleop.
     */
    public void teleopInit() 
    {
    	System.out.println("Robot.teleopInit();");
    	Scheduler.getInstance().add(new DriveOnLineCommand());
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
    	/*/////////////////////////////////////////////////////////////////////
    	 * Section dedicated to launching commands based on user button inputs.
    	 */////////////////////////////////////////////////////////////////////
    	if(getUI().getButtonPress(UI_TURRET_PERPENDICULAR))
    	{
    		if(!turretPerpCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretPerpCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_TURRET_PARALLEL))
    	{
    		if(!turretParaCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretParaCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_ELEVATOR_POS1))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL1);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_ELEVATOR_POS2))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL2);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_ELEVATOR_POS3))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL3);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_START_LINE))
    	{
    		Scheduler.getInstance().add(lineCommand);
    	}
    	/*/////////////////////////////////////////////////////////////////////
    	 * End dedicated command-launching section.
    	 */////////////////////////////////////////////////////////////////////
    	
        Scheduler.getInstance().run();
        updateSubsystemDSOutputs();
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
    	System.out.println(getSubsystemFeedback());
    	SmartDashboard.putString("Log", getSubsystemFeedback());
    	updateSubsystemDSOutputs();
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
	public static Conveyer getConveyer()
	{
		return carbonConveyer;
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
	 * Returns the instance of the feeder system.
	 * @return The instance of the feeder system.
	 */
	public static Feeder getFeeder()
	{
		return carbonFeeder;
	}
	
	/**
	 * Returns the active UI handler.
	 * @return The active UI handler.
	 */
	public static CarbonUI getUI()
	{
		return ui;
	}
	
	public static String getSubsystemFeedback()
	{
		StringBuffer feedback = new StringBuffer("");
    	feedback.append(carbonDrive.getFeedback()).append(" ");
    	feedback.append(carbonTurret.getFeedback()).append(" ");
    	feedback.append(carbonElevator.getFeedback()).append(" ");
    	return feedback.toString();
	}
	
	public static void updateSubsystemDSOutputs()
	{
		carbonDrive.updateSmartDS();
		carbonTurret.updateSmartDS();
		carbonElevator.updateSmartDS();
	}
}