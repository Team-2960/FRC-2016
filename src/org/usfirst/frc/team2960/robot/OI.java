package org.usfirst.frc.team2960.robot;

import org.usfirst.frc.team2960.robot.subsystems.Camera;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	boolean resetBtnPressed = false;
	boolean intakeOpen = false;
	public void driveRobot(Joystick stick, DriveTrain drivetrain, Shooter shooter) {
		if(drivetrain.moveStop == false)
		{
		drivetrain.setSpeed(-stick.getRawAxis(1), -stick.getRawAxis(5));
		}
		if(stick.getRawButton(7) && stick.getRawButton(8)){
			drivetrain.TurnOnTheTurn = true;
		}
		else{
			drivetrain.TurnOnTheTurn = false;
		}
		if(drivetrain.TurnOnTheTurn == true && stick.getRawAxis(3) > .1)
		{
			shooter.toggleWinch();
		}
		//if(stick.getRawButton(4)){
			//drivetrain.gotoAngle(-180);
		//}

	}
	boolean triggerPressed = false;

	public void operateRobot(Joystick stick, Shooter shooter, Pickup pickup){
		if(stick.getRawButton(9))
		{
			pickup.setRoller(1.0);
		}
		else if(stick.getRawButton(10))
		{
			pickup.setRoller(-1.0);
		}
		else
		{
			pickup.setRoller(0);
		}
		if(stick.getRawButton(7))
		{
			pickup.setHood(true);
		}
		if(stick.getRawButton(8))
		{
			pickup.setHood(false);
		}
		if(stick.getRawButton(5))
		{
			pickup.setHook(true);
		}
		if(stick.getRawButton(3))
		{
			pickup.setHook(false); 
		}
		if(stick.getRawButton(2))
		{
			if(stick.getRawButton(1))
			{
				shooter.toggleWinch();
				//shooter.moveWinch();
			}
			else
			{
				//shooter.stopWinch();
			}
			if(Math.abs(stick.getRawAxis(1)) > .1)
			{
				//shooter.stopPID();
				//shooter.adjustAngle(stick.getRawAxis(1));
				//shooter.addAngle(1.6*stick.getRawAxis(1));
				//shooter.setAngleRate(stick.getRawAxis(1)*50);
				shooter.setRate(stick.getRawAxis(1));
			}
			else
			{
				shooter.setRate(0);
			}
		}
		else
		{
			if(stick.getRawButton(1))
			{
				shooter.manualWinch = true;
				shooter.moveWinch();
			}
			else
			{
				shooter.stopWinch();
				shooter.setRate(0);
			}
		}
		
	}
}

