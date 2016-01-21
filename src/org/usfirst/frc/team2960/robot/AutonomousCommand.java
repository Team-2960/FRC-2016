package org.usfirst.frc.team2960.robot;

public class AutonomousCommand {
	
	private int stage = 0;
	private int finalStage = 0;
	
	public void updateLoop()
	{
		if(isFinished())
		{
			return;
		}
	}
	
	public void startExecute()
	{
		
	}
	
	public boolean isFinished()
	{
		return stage == finalStage;
	}
}
