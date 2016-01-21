package org.usfirst.frc.team2960.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousCommand {
	
	private int stage = 0;
	private int finalStage = 0;
	Timer timer = new Timer();
	int timerValue = 0;
	
	class UpdateLoopTask extends TimerTask
	{
		public void run()
		{
			timerValue++;
			updateLoop();
		}
	}
	
	public void updateLoop()
	{
		SmartDashboard.putString("autonTimer", Double.toString(getSecondsDone()));
		if(isFinished())
		{
			stop();
			return;
		}
	}
	
	public void startExecute()
	{
		timer.scheduleAtFixedRate(new UpdateLoopTask(),0,10);
	}
	
	public double getSecondsDone()
	{
		return timerValue/100;
	}
	
	public void finish()
	{
		stage = finalStage + 1;
	}
	
	public void stop()
	{
		timer.cancel();
	}
	
	public boolean isFinished()
	{
		return stage > finalStage;
	}
	
	
}
