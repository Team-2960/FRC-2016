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
		drivetrain.initDefaultCommand();
		if(Math.abs(stick.getRawAxis(1)) > 0.1)
        	drivetrain.move(-stick.getRawAxis(1));
    	if(stick.getRawAxis(2) > 0.1)
    		drivetrain.turnRight(stick.getRawAxis(2));
    	if(stick.getRawAxis(2) < -0.1)
    		drivetrain.turnLeft(Math.abs(stick.getRawAxis(2)));
		if(stick.getRawButton(2) && resetBtnPressed == false)
		{
			drivetrain.resetGyro();
			resetBtnPressed = true;
		}
		else if(!stick.getRawButton(2))
		{
			resetBtnPressed = false;
		}
	}

	public void pickupShere(Joystick stick, Pickup pickup) {pickup.initDefaultCommand();}

	public void shootSphere(Joystick stick, Shooter shooter) {shooter.initDefaultCommand();}
}

