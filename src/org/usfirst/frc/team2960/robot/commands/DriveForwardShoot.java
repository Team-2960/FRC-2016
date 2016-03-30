package org.usfirst.frc.team2960.robot.commands;

import org.usfirst.frc.team2960.robot.Autonomous;
import org.usfirst.frc.team2960.robot.AutonomousCommand;
import org.usfirst.frc.team2960.robot.subsystems.Camera;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

public class DriveForwardShoot extends AutonomousCommand {
	public DriveForwardShoot(DriveTrain drive,Shooter shoot,Pickup pick,Camera cam)
	{
		driveTrain = drive;
		finalStage = 3;
		stage = 0;
		shooter = shoot;
		pickup = pick;
	}
	
	public void updateLoop()
	{
		super.updateLoop();
		switch(stage)
		{
		case 0:
			if(shooter.zeroing() == false)
			{
				shooter.setAngle(-80);
				pickup.setHood(true);
				Autonomous.resetCounter();
				finishStage();
			}
			break;
		case 1:
			if(this.getSecondsDone() > 1.0)
			{
				driveTrain.setSpeed(0.4, 0.4);
			}
			if(this.getSecondsDone() >= 2.0)
			{
				driveTrain.setSpeed(0.0, 0.0);
				finishStage();
			}
			break;
		case 2:
			
		case 3:
			//this won't actually run
			driveTrain.gotoDistance(30.0); // move 30 inches
			finish();
		}
	}
}
