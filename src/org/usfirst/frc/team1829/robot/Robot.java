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
	public static final String UI_DRIVE_LINE = "0x020";
	public static final String UI_ELEV_Y = "0x03";
	public static final String UI_TURRET_PERPENDICULAR = "0x04";
	public static final String UI_TURRET_PARALLEL = "0x05";
	public static final String UI_TURRET_AXIS = "0x050";
	public static final String UI_TURRET_AXIS_ENABLE = "0x051";
		//public static final String UI_ELEVATOR_UP = "0x06";
		//public static final String UI_ELEVATOR_DOWN = "0x07";
	public static final String UI_ELEVATOR_AXIS = "0x070";
	public static final String UI_ELEVATOR_AXIS_ENABLE = "0x071";
	public static final String UI_CONVEYER_IN = "0x080";
	public static final String UI_CONVEYER_OUT = "0x081";
	public static final String UI_CONVEYER_AXIS = "0x082";
	public static final String UI_JAW_EXTEND = "0x09";
	public static final String UI_JAW_RETRACT = "0x0A";
	public static final String UI_JAW_AXIS = "0x0A0";
	public static final String UI_JAW_AXIS_ENABLE = "0x0A1";
	public static final String UI_FEED_IN = "Bannana";
	public static final String UI_FEED_OUT = "0x0B";
	public static final String UI_FEED_AXIS = "0x0C";
	
	//Drive CAN IDs
	public static final int DRIVE_FRONT_RIGHT = 1;			//CAN
	public static final int DRIVE_FRONT_LEFT = 2;			//CAN
		//public static final int DRIVE_REAR_RIGHT = 3;			//CAN <Removed for weight>
		//public static final int DRIVE_REAR_LEFT = 4;			//CAN <Removed for weight>
	
	//DIO Ports
	public static final int TURRET_LIMIT = 2;				//DIO
	public static final int TURRET_LIMIT_PERPENDICULAR = 3;	//DIO
	public static final int ELEVATOR_LIMIT_TOP = 4;			//DIO
	public static final int ELEVATOR_LIMIT_BOT = 5;			//DIO
	public static final int JAW_LIMIT_EXTENT = 0;			//DIO
	public static final int JAW_LIMIT_RETRACT = 1;			//DIO

	//PWM Ports
	public static final int TURRET_MOTOR = 3;				//PWM
	public static final int ELEVATOR_MOTOR = 2;				//PWM
	public static final int CONVEYOR_MOTOR = 0;				//PWM
	public static final int JAW_MOTOR = 1;					//PWM
	public static final int FEED_MOTOR = 4;					//PWM
	
	//Analog Ports
	public static final int DRIVE_LINE = 3;					//Analog
	public static final int ELEVATOR_ULTRA = 0;				//Analog
	public static final int JAW_DISTANCE_EXTENT = 2;		//Analog
	public static final int JAW_DISTANCE_APPROACH = 1;		//Analog
	
	//Inversion constants
	public static final boolean DRIVE_Y_INVERTED = true;	//Inversion constant for Drive Y axis
	public static final boolean DRIVE_R_INVERTED = true;	//Inversion constant for Drive R axis
	public static final boolean TURRET_INVERTED = true;	//Inversion constant for Turret
	public static final boolean ELEVATOR_INVERTED = true;	//Inversion constant for elevator.
	public static final boolean CONVEYOR_INVERTED = true;	//Inversion constant for conveyer.
	public static final boolean JAW_INVERTED = false;		//Inversion constant for jaw extent
	public static final boolean FEED_INVERTED = true;		//Inversion constant for feeder
	
	/**
	 * Custom UI handler.
	 */
	private static CarbonUI ui;
	
	//Instantiate subsystems
	private static Drive carbonDrive;
	private static Turret carbonTurret;
	private static Elevator carbonElevator;
	private static Conveyer carbonConveyer;
	private static Jaw carbonJaw;
	private static Feeder carbonFeeder;
	
	//Building Block Commands
	private DriveOnLineCommand lineCommand;
	private ConveyerInCommand conveyerInCommand;
	private ConveyerOutCommand conveyerOutCommand;
	private ConveyerStopCommand conveyerStopCommand;
	private JawExtendCommand jawExtendCommand;
	private JawRetractCommand jawRetractCommand;
	private JawStopCommand jawStopCommand;
	private FeedInCommand feedInCommand;
	private FeedOutCommand feedOutCommand;
	private FeedStopCommand feedStopCommand;
	
	//Advanced/Compound Commands
	private ContainerGrabCommand containerGrabCommand;
	
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
        
        //Initialize basic commands
        	//lineCommand = new DriveOnLineCommand();
        conveyerInCommand = new ConveyerInCommand();
        conveyerOutCommand = new ConveyerOutCommand();
        conveyerStopCommand = new ConveyerStopCommand();
        jawExtendCommand = new JawExtendCommand();
        jawRetractCommand = new JawRetractCommand();
        jawStopCommand = new JawStopCommand();
        feedInCommand = new FeedInCommand(0.6);
        feedOutCommand = new FeedOutCommand();
        feedStopCommand = new FeedStopCommand();
        
        //Initialize advanced commands
        containerGrabCommand = new ContainerGrabCommand();
        
        //Initialize control scheme(s)
        ui = new CarbonUI();
        
        //Add Axes to control scheme
        ui.addControl(UI_DRIVE_Y,				ControlType.Axis, 0, 1);
        ui.addControl(UI_DRIVE_R,				ControlType.Axis, 0, 2);
        ui.addControl(UI_TURRET_AXIS,			ControlType.Axis, 1, 0);
        ui.addControl(UI_ELEVATOR_AXIS,			ControlType.Axis, 1, 1);
        ui.addControl(UI_JAW_AXIS,				ControlType.Axis, 1, 3);
        
        //Add Buttons to control scheme
        ui.addControl(UI_DRIVE_LINE,			ControlType.Button, 0, 4); //USB 0, Button 4
        ui.addControl(UI_TURRET_AXIS_ENABLE,	ControlType.Button, 1, 4); //USB 1, Button 4
        ui.addControl(UI_ELEVATOR_AXIS_ENABLE,	ControlType.Button, 1, 3); //USB 1, Button 3
        ui.addControl(UI_CONVEYER_IN,			ControlType.Button, 1, 7); //USB 1, Button 7
        ui.addControl(UI_CONVEYER_OUT,			ControlType.Button, 1, 8); //USB 1, Button 8
        //ui.addControl(UI_JAW_EXTEND,			ControlType.Button, 1, 2); //USB 1, Button 2
        //ui.addControl(UI_JAW_RETRACT,			ControlType.Button, 1, 1); //USB 1, Button 1
        ui.addControl(UI_JAW_AXIS_ENABLE,		ControlType.Button, 1, 1);
        ui.addControl(UI_FEED_IN,				ControlType.Button, 1, 9); //USB 1, Button 9
        ui.addControl(UI_FEED_OUT,				ControlType.Button, 1, 10); //USB 1, Button 10
    }

    /**
     * Runs once before autonomous.
     */
    public void autonomousInit() 
    {
    	
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
    	
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
    	/*/////////////////////////////////////////////////////////////////////
    	 * Section dedicated to launching commands based on user button inputs.
    	 */////////////////////////////////////////////////////////////////////
    	//CONVEYER
    	boolean conveyerStopped = true;
    	if(getUI().getButtonState(UI_CONVEYER_IN) && !conveyerInCommand.isRunning()) //Conveyer-In Held
    	{
    		Scheduler.getInstance().add(conveyerInCommand);
    		conveyerStopped = false;
    	}
    	else if(getUI().getButtonState(UI_CONVEYER_OUT) && !conveyerOutCommand.isRunning()) //Conveyer-Out Held
    	{
    		Scheduler.getInstance().add(conveyerOutCommand);
    		conveyerStopped = false;
    	}
    	else //Neither Conveyer button
    	{
    		if(!conveyerStopped)
    		{
    			Scheduler.getInstance().add(conveyerStopCommand);    			
    		}
    	}
    	
    	//FEEDER
    	boolean feedStopped = true;
    	if(getUI().getButtonState(UI_FEED_IN) && !feedInCommand.isRunning()) //Feed-in button held
    	{
    		Scheduler.getInstance().add(feedInCommand);
    		feedStopped = false;
    	}
    	else if(getUI().getButtonState(UI_FEED_OUT) && !feedOutCommand.isRunning()) //Feed-out button held
    	{
    		Scheduler.getInstance().add(feedOutCommand);
    		feedStopped = false;
    	}
    	else
    	{
    		if(!feedStopped)
    		{
    			Scheduler.getInstance().add(feedStopCommand);
    			feedStopped = true;
    		}
    	}
    	/*/////////////////////////////////////////////////////////////////////
    	 * End dedicated command-launching section.
    	 */////////////////////////////////////////////////////////////////////
    	
        Scheduler.getInstance().run();
        updateSubsystemDSOutputs(); //Calls all updateDSOutput functions implemented in subsystems.
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
    	System.out.println(getSubsystemFeedback() + " " /*+getDSInputs()*/);
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
	 * Returns the instance of the conveyer system.
	 * @return The instance of the conveyer system.
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
    	feedback.append(carbonConveyer.getStatus()).append(" ");
    	feedback.append(carbonJaw.getStatus()).append(" ");
    	
    	return feedback.toString();
	}
	
	public static String getSubsystemDIOFeedback()
	{
		StringBuffer feedback = new StringBuffer();
		feedback.append(carbonDrive.getDIOFeedback());
		feedback.append(carbonTurret.getDIOFeedback());
		feedback.append(carbonElevator.getDIOFeedback());
		feedback.append(carbonConveyer.getDIOFeedback());
		feedback.append(carbonJaw.getDIOFeedback());
		return feedback.toString();
	}
	
	public static void updateSubsystemDSOutputs()
	{
		carbonDrive.updateSmartDS();
		carbonTurret.updateSmartDS();
		carbonElevator.updateSmartDS();
		carbonJaw.updateSmartDS();
		
		SmartDashboard.putString("DIO Feedback", getSubsystemDIOFeedback());
	}
}