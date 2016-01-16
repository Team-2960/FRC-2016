package org.usfirst.frc.team2960.robot;

import org.usfirst.frc.team2960.robot.commands.MoveForward;

import edu.wpi.first.wpilibj.command.Command;

public class Autonomous {

	public static Command getAutonCommandAtIndex(int index)
	{
		//switch statement here (or an array of commands)
		return new MoveForward();
	}
	
	public static void autonomousUpdate()
	{
		
	}
	
}
