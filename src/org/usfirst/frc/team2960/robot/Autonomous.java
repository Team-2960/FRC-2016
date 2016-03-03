package org.usfirst.frc.team2960.robot;

import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team2960.robot.commands.MoveForward;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

public class Autonomous {

	public static Timer timer = new Timer();
	public static AutonomousCommand currentCommand = new AutonomousCommand();
	public static int timerValue = 0;
	public static DriveTrain driveTrain;
	public static Shooter shooter;

	public static AutonomousCommand getAutonCommandAtIndex(int index)
	{
		return new MoveForward(driveTrain,shooter);
		//switch statement here (or an array of commands)
	}
	
	public static class UpdateLoopTask extends TimerTask
	{
		public void run()
		{
			timerValue++;
			currentCommand.updateLoop();
		}
	}
	
	public static void startCommand(AutonomousCommand cmd)
	{
		currentCommand = cmd;
		timer.scheduleAtFixedRate(new UpdateLoopTask(),0,10);
	}
	
	public static void stopAuton()
	{
		timerValue = 0;
		timer.cancel();
	}
	
	public static void resetCounter()
	{
		timerValue = 0;
	}
	
}
