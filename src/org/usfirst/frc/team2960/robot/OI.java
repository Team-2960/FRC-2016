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
	public void driveRobot(Joystick stick, DriveTrain drivetrain) {
		if(drivetrain.moveStop == false)
		{
		drivetrain.setSpeed(-stick.getRawAxis(1), -stick.getRawAxis(5));
		}
		if(stick.getRawButton(3)){
			drivetrain.resetEncoder();
		}
		while(stick.getRawButton(7) && stick.getRawButton(8)){
			drivetrain.TurnOnTheTurn = true;
		}
		
	}
	public void operateRobot(Joystick stick, Shooter shooter, Pickup pickup){
		if(stick.getRawButton(4)){
			//drivetrain.gotoAngle(-180);
			pickup.setHood(false);
		}
		if(stick.getRawButton(1)){
			//drivetrain.disablePIDAngle();
			pickup.setHood(true);
		}
		if(stick.getRawButton(2))
		{
			shooter.moveWinch();
		}
		else
		{
			shooter.stopWinch();
		}
		if(stick.getRawAxis(2) > 0.1)
		{
			shooter.adjustAngle(1.0);
		}
		else if(stick.getRawAxis(3) > 0.1)
		{
			shooter.adjustAngle(-1.0);
		}
		else
		{
			shooter.adjustAngle(0);
		}
		if(stick.getRawButton(5))
		{
			pickup.setRoller(1.0);
		}
		else if(stick.getRawButton(6))
		{
			pickup.setRoller(-1.0);
		}
		else
		{
			pickup.setRoller(0);
		}
	}
}

