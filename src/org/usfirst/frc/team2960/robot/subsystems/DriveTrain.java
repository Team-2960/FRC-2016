package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	Victor frontLeft = new Victor(RobotMap.frontLeft);
	Victor frontRight = new Victor(RobotMap.frontRight);
	Victor backLeft = new Victor(RobotMap.backLeft);
	Victor backRight = new Victor(RobotMap.backRight);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	frontLeft.set(0);
    	frontRight.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    }
    
    public void move(double speed) {
    	speed = speed * 0.75;
    	frontLeft.set(-speed);
    	frontRight.set(-speed);
    	backLeft.set(speed);
    	backRight.set(speed);
    }
    
    public void turnLeft(double speed) {
    	speed = speed * 0.75;
    	frontRight.set(speed);
    	backRight.set(speed);
    }
    
    public void turnRight(double speed) {
    	speed = speed * 0.75;
    	frontLeft.set(-speed);
    	backLeft.set(-speed);
    }
}

