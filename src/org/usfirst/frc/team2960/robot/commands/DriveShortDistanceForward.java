package org.usfirst.frc.team2960.robot.commands;

import org.usfirst.frc.team2960.robot.Autonomous;
import org.usfirst.frc.team2960.robot.AutonomousCommand;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

public class DriveShortDistanceForward extends AutonomousCommand {
	public DriveShortDistanceForward(DriveTrain drive,Shooter shoot,Pickup pick)
	{
		driveTrain = drive;
		finalStage = 1;
		stage = 0;
		shooter = shoot;
		pickup = pick;
	}
	
	boolean hoodClosed = false;
	
	public void updateLoop()
	{
		super.updateLoop();
		//SmartDashboard.putNumber("stage", stage);
		//SmartDashboard.putBoolean("zeroin method", shooter.zeroing());
		switch(stage)
		{
		case 0:
			if(shooter.zeroing() == false)
			{
				shooter.setAngle(-40);
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
