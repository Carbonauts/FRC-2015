package org.usfirst.frc.team1829.robot;

import org.usfirst.frc.team1829.robot.command.DriveOnLineCommand;
import org.usfirst.frc.team1829.robot.command.ElevatorToPositionCommand;
import org.usfirst.frc.team1829.robot.command.OperatorElevatorCommand;
import org.usfirst.frc.team1829.robot.command.TurretToParallelCommand;
import org.usfirst.frc.team1829.robot.command.TurretToPerpendicularCommand;
import org.usfirst.frc.team1829.robot.subsystems.Conveyor;
import org.usfirst.frc.team1829.robot.subsystems.Drive;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;
import org.usfirst.frc.team1829.robot.subsystems.Jaw;
import org.usfirst.frc.team1829.robot.subsystems.Turret;

import com.team1829.library.CarbonUI;
import com.team1829.library.CarbonUI.ControlType;
import com.team1829.library.LatchBoolean;

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
	public static final String UI_ELEV_Y = "0x03";
	public static final String UI_TURRET_PERPENDICULAR = "0x04";
	public static final String UI_TURRET_PARALLEL = "0x05";
	public static final String UI_ELEVATOR_POS1 = "0x06";
	public static final String UI_ELEVATOR_POS2 = "0x07";
	public static final String UI_ELEVATOR_POS3 = "0x08";
	
	//TODO add real PWM channel numbers.
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
	public static final int ELEVATOR_MOTOR = 2;				//PWM <- CORRECT VALUE
	public static final int ELEVATOR_ULTRA = 1;				//Analog <- CORRECT VALUE
	public static final boolean ELEVATOR_DIRECTION = false; //Inversion constant for elevator.
	public static final int ELEVATOR_LIMIT_TOP = 0;			//DIO <- CORRECT VALUE
	public static final int ELEVATOR_LIMIT_BOT = 1;			//DIO <- CORRECT VALUE
	//Conveyor PWM/DIOs
	public static final int CONVEYOR_MOTOR = 5;				//PWM
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
	
	/*
	 * Initialize command references
	 */
	private TurretToPerpendicularCommand turretPerpCommand;
	private TurretToParallelCommand turretParaCommand;
	private ElevatorToPositionCommand elevPosCommand;
	
	/*
	 * Initialize latch references used to detect button clicks
	 */
	private LatchBoolean turretPerpLatch;
	private LatchBoolean turretParaLatch;
	private LatchBoolean elevatorPos1Latch;
	private LatchBoolean elevatorPos2Latch;
	private LatchBoolean elevatorPos3Latch;
	
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
        
        turretPerpCommand = new TurretToPerpendicularCommand();
        turretParaCommand = new TurretToParallelCommand();
        elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.AUTO);
        
        turretPerpLatch = new LatchBoolean();
        turretParaLatch = new LatchBoolean();
        elevatorPos1Latch = new LatchBoolean();
        elevatorPos2Latch = new LatchBoolean();
        elevatorPos3Latch = new LatchBoolean();
        
        ui.addControl(UI_DRIVE_Y, ControlType.AXIS, 0, 1);
        ui.addControl(UI_DRIVE_R, ControlType.AXIS, 0, 0);
        ui.addControl(UI_ELEV_Y, ControlType.AXIS, 0, 2);
        ui.addControl(UI_TURRET_PERPENDICULAR, ControlType.BUTTON, 0, 2);
        ui.addControl(UI_TURRET_PARALLEL, ControlType.BUTTON, 0, 4);
        ui.addControl(UI_ELEVATOR_POS1, ControlType.BUTTON, 0, 8);
        ui.addControl(UI_ELEVATOR_POS2, ControlType.BUTTON, 0, 7);
        ui.addControl(UI_ELEVATOR_POS3, ControlType.BUTTON, 0, 9);
    }

    /**
     * Runs once before autonomous.
     */
    public void autonomousInit() 
    {
    	System.out.println("Robot.autonomousInit();");
    	Scheduler.getInstance().add(new DriveOnLineCommand());
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
    	Scheduler.getInstance().add(new OperatorElevatorCommand());
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
    	/*//////////////////////////////////////////////////////////////////////////////////////////////////
    	 * Section dedicated to launching commands based on user button inputs.
    	 */
    	if(turretPerpLatch.onTrue(getUI().getButtonData(UI_TURRET_PERPENDICULAR)))
    	{
    		if(!turretPerpCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretPerpCommand);
    		}
    	}
    	
    	if(turretParaLatch.onTrue(getUI().getButtonData(UI_TURRET_PARALLEL)))
    	{
    		if(!turretParaCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretParaCommand);
    		}
    	}
    	
    	if(elevatorPos1Latch.onTrue(getUI().getButtonData(UI_ELEVATOR_POS1)))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL1);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	
    	if(elevatorPos2Latch.onTrue(getUI().getButtonData(UI_ELEVATOR_POS2)))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL2);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	
    	if(elevatorPos3Latch.onTrue(getUI().getButtonData(UI_ELEVATOR_POS3)))
    	{
    		if(!elevPosCommand.isRunning())
    		{
    			elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.LEVEL3);
    			Scheduler.getInstance().add(elevPosCommand);
    		}
    	}
    	/*
    	 * End dedicated command-launching section.
    	 *//////////////////////////////////////////////////////////////////////////////////////////////////
    	
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