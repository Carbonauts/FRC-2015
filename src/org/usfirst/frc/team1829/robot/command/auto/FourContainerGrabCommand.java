package org.usfirst.frc.team1829.robot.command.auto;

import org.usfirst.frc.team1829.robot.command.*;
import org.usfirst.frc.team1829.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FourContainerGrabCommand extends CommandGroup
{
	//Rotate turret to perpendicular
	//Lower elevator to auto-container height
	//----------------------------------------------------------------------------------------------------
	//Drive on Line until contact a container.
	//Clamp container and feed up and in >----------|---------> Start driving on line
	//Pull container onto conveyer					|			Stop on container contact
	//Roll conveyer back							|			Wait for container processing to finish ->+
	//Stop feeder and open jaw						|													  |
	// <--------<----------<-----------<------------<--------------<-------------<------------<----------<+
	//Repeat above steps three times.
	//
	//Turn robot base 90 degrees left
	//Drive (reverse) into auto zone
	
	public FourContainerGrabCommand()
	{
		super("FourContainerGrabCommand");
		
		addSequential(new TurretToPerpendicularCommand());
		addSequential(new ElevatorToPositionCommand(Elevator.Position.AUTO));
		
		addSequential(new DriveOnLineCommand()); //TODO make command stop on container encounter
		addParallel(new JawClampCommand()); //Start this command and execute the next ones in parallel.
		addSequential(new FeedInCommand(1.0));
		addSequential(new JawRetractCommand()); //Kills the JawClampCommand
		addParallel(new ConveyerFeedInCommand(4.0));
		addParallel(new JawExtendCommand());
		
		addSequential(new DriveOnLineCommand());
		addParallel(new JawClampCommand()); //Start this command and execute the next ones in parallel.
		addSequential(new FeedInCommand(1.0));
		addSequential(new JawRetractCommand()); //Kills the JawClampCommand
		addParallel(new ConveyerFeedInCommand(3.0));
		addParallel(new JawExtendCommand());
		
		addSequential(new DriveOnLineCommand());
		addParallel(new JawClampCommand()); //Start this command and execute the next ones in parallel.
		addSequential(new FeedInCommand(1.0));
		addSequential(new JawRetractCommand()); //Kills the JawClampCommand
		addParallel(new ConveyerFeedInCommand(2.0));
		addParallel(new JawExtendCommand());
		
		addSequential(new DriveOnLineCommand());
		addParallel(new JawClampCommand()); //Start this command and execute the next ones in parallel.
		addSequential(new FeedInCommand(1.0));
		addSequential(new JawRetractCommand()); //Kills the JawClampCommand
		addParallel(new ConveyerFeedInCommand(1.0));
		addParallel(new JawExtendCommand());
	}
}
