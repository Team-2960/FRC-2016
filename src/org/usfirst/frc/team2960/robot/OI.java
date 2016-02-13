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
	
	public void driveRobot(Joystick stick, DriveTrain drivetrain) {
		if(drivetrain.moveStop == false)
		{
		drivetrain.setSpeed(stick.getRawAxis(1), stick.getRawAxis(5));
		}
		if(stick.getRawButton(2) && resetBtnPressed == false)
		{
			drivetrain.resetGyro();
			resetBtnPressed = true;
		}
		else if(!stick.getRawButton(2))
		{
			resetBtnPressed = false;
		}
		if(stick.getRawButton(4)){
			drivetrain.gotoAngle(-180);
		}
		if(stick.getRawButton(1)){
			drivetrain.disablePIDAngle();
		}
		if(stick.getRawButton(3)){
			drivetrain.resetEncoder();
		}
		while(stick.getRawButton(7) && stick.getRawButton(8)){
			drivetrain.TurnOnTheTurn = true;
		}
		
	}
	public void operateRobot(Joystick stick, Shooter shooter){
		if(stick.getRawButton(11))
		{
			shooter.check();
			
		}
	}
	public void pickupShere(Joystick stick, Pickup pickup) {pickup.initDefaultCommand();}

	public void shootSphere(Joystick stick, Shooter shooter) {shooter.initDefaultCommand();}
}

