package org.usfirst.frc.team2960.robot.commands;

import org.usfirst.frc.team2960.robot.Autonomous;
import org.usfirst.frc.team2960.robot.AutonomousCommand;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

public class MoveForward extends AutonomousCommand {
		
	public MoveForward(DriveTrain drive,Shooter shoot)
	{
		driveTrain = drive;
		finalStage = 1;
		stage = 0;
		shooter = shoot;
	}
	
	public void updateLoop()
	{
		super.updateLoop();
		switch(stage)
		{
		case 0:
			shooter.setAngle(-40);
			Autonomous.resetCounter();
			finishStage();
			break;
		case 1:
			driveTrain.setSpeed(0.5, 0.5);
			
			if(this.getSecondsDone() >= 3.0)
			{
				driveTrain.setSpeed(0.0, 0.0);
				finish();
			}
			break;
		/*case 2:
			//this won't actually run
			driveTrain.gotoDistance(30.0); // move 30 inches
			finish();*/
		}
	}
	
}
