package org.usfirst.frc.team1829.robot.command;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CheckSubsystemsCommand extends CommandGroup
{	
	public CheckSubsystemsCommand()
	{
		super("SubsystemCheck");
		addSequential(new CheckDriveCommand(CheckDriveCommand.Routine.Forward, 2.0));
		addSequential(new CheckDriveCommand(CheckDriveCommand.Routine.Reverse, 2.0));
		addSequential(new CheckDriveCommand(CheckDriveCommand.Routine.TurnLeft, 2.0));
		addSequential(new CheckDriveCommand(CheckDriveCommand.Routine.TurnRight, 2.0));
		addSequential(new TurretToPerpendicularCommand());
		addSequential(new TurretToParallelCommand());
	}	
}
