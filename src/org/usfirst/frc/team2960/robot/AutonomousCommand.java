package org.usfirst.frc.team2960.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousCommand {
	
	public int stage = 0;
	int finalStage = 0;
	public DriveTrain driveTrain;
	public Shooter shooter;
	public Pickup pickup;
	
	public void updateLoop()
	{
		SmartDashboard.putString("autonTimer", Double.toString(getSecondsDone()));
		if(isFinished())
		{
			Autonomous.stopAuton();
			return;
		}
	}
	
	public double getSecondsDone()
	{
		return Autonomous.timerValue/100;
	}
	
	public void finish()
	{
		stage = finalStage + 1;
	}

	public boolean isFinished()
	{
		return stage > finalStage;
	}	
}