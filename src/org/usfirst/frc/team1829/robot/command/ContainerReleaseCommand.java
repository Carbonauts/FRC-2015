package org.usfirst.frc.team1829.robot.command;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ContainerReleaseCommand extends CommandGroup 
{
	public ContainerReleaseCommand()
	{
		super("Container Release Command");
		
		addSequential(new JawExtendCommand());
		double timeout = 2.0;
		addParallel(new FeedOutCommand(timeout));
		addParallel(new ConveyerOutCommand(timeout));
		
	}
}
