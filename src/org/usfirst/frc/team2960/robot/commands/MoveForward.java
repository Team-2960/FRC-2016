package org.usfirst.frc.team2960.robot.commands;

import org.usfirst.frc.team2960.robot.AutonomousCommand;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;

public class MoveForward extends AutonomousCommand {
	
	DriveTrain driveTrain;
	
	public MoveForward(DriveTrain drive)
	{
		driveTrain = drive;
	}
	
	public void updateLoop()
	{
		finalStage = 1;
		super.updateLoop();
		switch(stage)
		{
		case 0:
			driveTrain.setSpeed(0.5, 0.5);
			if(this.getSecondsDone() >= 3.0)
			{
				driveTrain.setSpeed(0.0, 0.0);
				finish();
			}
			break;
		case 2:
			//this won't actually run
			driveTrain.gotoDistance(30.0); // move 30 inches
			finish();
		}
	}
	
}
