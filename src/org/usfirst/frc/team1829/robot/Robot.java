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
	public static final String UI_DRIVE_X = "0x01"; //Strafing on a holonomic drive
	public static final String UI_DRIVE_R = "0x02";
	public static final String UI_ELEV_Y = "0x03";
	public static final String UI_TURRET_PERPENDICULAR = "0x04";
	public static final String UI_TURRET_PARALLEL = "0x05";
		//public static final String UI_ELEVATOR_UP = "0x06";
		//public static final String UI_ELEVATOR_DOWN = "0x07";
	public static final String UI_CONVEYER_IN = "0x070";
	public static final String UI_CONVEYER_OUT = "0x071";
	public static final String UI_CONVEYER_AXIS = "0x072";
	public static final String UI_JAW_EXTEND = "0x09";
	public static final String UI_JAW_RETRACT = "0x0A";
	public static final String UI_JAW_AXIS = "0x0A0";
	public static final String UI_FEED_IN = "Bannana";
	public static final String UI_FEED_OUT = "0x0B";
	public static final String UI_FEED_AXIS = "0x0C";
	
	//Drive CAN ID/DIOs
	public static final int DRIVE_FRONT_RIGHT = 1;			//CAN
	public static final int DRIVE_FRONT_LEFT = 2;			//CAN
	public static final int DRIVE_REAR_RIGHT = 3;			//CAN
	public static final int DRIVE_REAR_LEFT = 4;			//CAN
	public static final int DRIVE_LINE = 3;					//Analog
	public static final boolean DRIVE_Y_INVERTED = false;	//Inversion constant for Drive Y axis
	public static final boolean DRIVE_X_INVERTED = false;	//Inversion constant for Drive X axis
	//Turret PWM/DIOs
	public static final int TURRET_MOTOR = 0;				//PWM
	public static final boolean TURRET_INVERTED = false;	//Inversion constant for Turret
	public static final int TURRET_LIMIT_PARALLEL = 0;		//DIO
	public static final int TURRET_LIMIT_PERPENDICULAR = 1;	//DIO
	//Elevator PWM/DIOs
	public static final int ELEVATOR_MOTOR = 2;				//PWM
	public static final int ELEVATOR_ULTRA = 2;				//Analog
	public static final boolean ELEVATOR_INVERTED = false;	//Inversion constant for elevator.
	public static final int ELEVATOR_LIMIT_TOP = 2;			//DIO
	public static final int ELEVATOR_LIMIT_BOT = 3;			//DIO
	//Conveyer PWM/DIOs
	public static final int CONVEYOR_MOTOR = 5;				//PWM
	public static final boolean CONVEYOR_INVERTED = true;	//Inversion constant for conveyer.
	//Jaw PWMs
	public static final int JAW_MOTOR = 3;					//PWM
	public static final int JAW_LIMIT_EXTENT = 4;			//DIO
	public static final int JAW_LIMIT_RETRACT = 5;			//DIO
	public static final int JAW_DISTANCE_EXTENT = 0;		//Analog
	public static final int JAW_DISTANCE_APPROACH = 1;		//Analog
	public static final boolean JAW_INVERTED = false;		//Inversion constant for jaw extent
	//Feeder PWMs
	public static final int FEED_MOTOR = 4;					//PWM
	public static final boolean FEED_INVERTED = true;		//Inversion constant for feeder
	
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
	
	//Initialize command references
		//private DriveOnLineCommand lineCommand;
	private TurretPerpendicularCommand turretPerpendicularCommand;
	private TurretParallelCommand turretParallelCommand;
		//private ElevatorToPositionCommand elevPosCommand;
	private ConveyerInCommand conveyerInCommand;
	private ConveyerOutCommand conveyerOutCommand;
	private ConveyerStopCommand conveyerStopCommand;
	private JawExtendCommand jawExtendCommand;
	private JawRetractCommand jawRetractCommand;
	private JawStopCommand jawStopCommand;
	private FeedInCommand feedInCommand;
	private FeedOutCommand feedOutCommand;
	private FeedStopCommand feedStopCommand;
	
	//Operator Commands
	private OperatorDriveCommand operatorDriveCommand;
	private OperatorElevatorCommand operatorElevatorCommand;
	private OperatorJawCommand operatorJawCommand;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	//Initialize static subsystems
        carbonDrive = new Drive();
        carbonTurret = new Turret();
        carbonElevator = new Elevator();
        carbonConveyer = new Conveyer();
        carbonJaw = new Jaw();
        carbonFeeder = new Feeder();
        
        //Initialize command references
        	//lineCommand = new DriveOnLineCommand();
        turretPerpendicularCommand = new TurretPerpendicularCommand();
        turretParallelCommand = new TurretParallelCommand();
        	//elevPosCommand = new ElevatorToPositionCommand(Elevator.Position.AUTO);
        conveyerInCommand = new ConveyerInCommand();
        conveyerOutCommand = new ConveyerOutCommand();
        conveyerStopCommand = new ConveyerStopCommand();
        jawExtendCommand = new JawExtendCommand();
        jawRetractCommand = new JawRetractCommand();
        jawStopCommand = new JawStopCommand();
        feedInCommand = new FeedInCommand();
        feedOutCommand = new FeedOutCommand();
        feedStopCommand = new FeedStopCommand();
        
        //Initialize Operator Commands
        operatorDriveCommand = new OperatorDriveCommand();
        operatorElevatorCommand = new OperatorElevatorCommand();
        operatorJawCommand = new OperatorJawCommand();
        
        //Initialize control scheme(s)
        ui = new CarbonUI();
        
        //Add Axes to control scheme
        	//ui.addControl(UI_DRIVE_Y,	 ControlType.AXIS, 0, 1);
        	//ui.addControl(UI_DRIVE_R,	 ControlType.AXIS, 0, 0);
        ui.addControl(UI_JAW_AXIS,	 ControlType.AXIS, 0, 1);
        
        //Add Buttons to control scheme
        ui.addControl(UI_TURRET_PERPENDICULAR,	 ControlType.BUTTON, 0, 2); //REASSIGN
        ui.addControl(UI_TURRET_PARALLEL,		 ControlType.BUTTON, 0, 4); //REASSIGN
        ui.addControl(UI_CONVEYER_IN,			 ControlType.BUTTON, 0, 7);
        ui.addControl(UI_CONVEYER_OUT,			 ControlType.BUTTON, 0, 8);
        ui.addControl(UI_JAW_EXTEND,			 ControlType.BUTTON, 0, 6);
        ui.addControl(UI_JAW_RETRACT,			 ControlType.BUTTON, 0, 5);
        ui.addControl(UI_FEED_IN,				 ControlType.BUTTON, 0, 9);
        ui.addControl(UI_FEED_OUT,				 ControlType.BUTTON, 0, 10);
    }

    /**
     * Runs once before autonomous.
     */
    public void autonomousInit() 
    {
    	System.out.println("Robot.autonomousInit();");
    	Scheduler.getInstance().add(feedInCommand);
    	Scheduler.getInstance().add(conveyerInCommand);
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
    	Scheduler.getInstance().add(operatorJawCommand);
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
    		if(!turretPerpendicularCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretPerpendicularCommand);
    		}
    	}
    	
    	if(getUI().getButtonPress(UI_TURRET_PARALLEL))
    	{
    		if(!turretParallelCommand.isRunning())
    		{
    			Scheduler.getInstance().add(turretParallelCommand);
    		}
    	}
    	
    	//CONVEYER
    	if(getUI().getButtonState(UI_CONVEYER_IN)) //Conveyer-In Held
    	{
    		System.out.println("Conveyer In Held!");
    		if(!conveyerInCommand.isRunning())
    		{
    			Scheduler.getInstance().add(conveyerInCommand);    			
    		}
    	}
    	else if(getUI().getButtonState(UI_CONVEYER_OUT)) //Conveyer-Out Held
    	{
    		System.out.println("Conveyer Out Held");
    		if(!conveyerOutCommand.isRunning())
    		{
    			System.out.println("Launched Conveyer Out Command!");
    			Scheduler.getInstance().add(conveyerOutCommand);    			
    		}
    	}
    	else //Neither Conveyer button
    	{
    		if(!conveyerStopCommand.isRunning())
    		{
    			System.out.println("Launched Conveyer Stop Command!");
    			Scheduler.getInstance().add(conveyerStopCommand);    			
    		}
    	}
    	
    	//JAW
    	if(getUI().getButtonState(UI_JAW_EXTEND)) //Jaw extend button held
    	{
    		System.out.println("Jaw Extend Button Held!");
    		if(!jawExtendCommand.isRunning())
    		{
    			System.out.println("Launched Jaw Extend Command!");
    			Scheduler.getInstance().add(jawExtendCommand);
    		}
    	} 
    	else if(getUI().getButtonState(UI_JAW_RETRACT)) //Jaw retract button held
    	{
    		System.out.println("Jaw Retract Button Held!");
    		if(!jawRetractCommand.isRunning())
    		{
    			System.out.println("Launched Jaw Retract Command!");
    			Scheduler.getInstance().add(jawRetractCommand);
    		}
    	}
    	else //Neither jaw button held
    	{
    		if(/*!jawStopCommand.isRunning() &&*/ !operatorJawCommand.isRunning())
    		{
    			Scheduler.getInstance().add(operatorJawCommand);
    		}
    	}
    	
    	//FEEDER
    	if(getUI().getButtonState(UI_FEED_IN)) //Feed-in button held
    	{
    		System.out.println("Feed In Held!");
    		if(!feedInCommand.isRunning())
    		{
    			System.out.println("Launched Feed In Command!");
    			Scheduler.getInstance().add(feedInCommand);
    		}
    	}
    	else if(getUI().getButtonState(UI_FEED_OUT)) //Feed-out button held
    	{
    		System.out.println("Feed Out Held!");
    		if(!feedOutCommand.isRunning())
    		{
    			System.out.println("Launched Feed Out Command!");
    			Scheduler.getInstance().add(feedOutCommand);    			
    		}
    	}
    	else
    	{
    		if(!feedStopCommand.isRunning())
    		{    			
    			System.out.println("Launched Feed Stop Command!");
    			Scheduler.getInstance().add(feedStopCommand);
    		}
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
    	feedback.append(carbonDrive.getStatus()).append(" ");
    	feedback.append(carbonTurret.getStatus()).append(" ");
    	feedback.append(carbonElevator.getStatus()).append(" ");
    	feedback.append(carbonJaw.getStatus()).append(" ");
    	return feedback.toString();
	}
	
	public static void updateSubsystemDSOutputs()
	{
		carbonDrive.updateSmartDS();
		carbonTurret.updateSmartDS();
		carbonElevator.updateSmartDS();
		carbonJaw.updateSmartDS();
	}
}