package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem implements PeriodicUpdate {
    
	Victor frontLeft;
	Victor frontRight;
	Victor backLeft;
	Victor backRight;
	AnalogGyro gyro;
	
	public DriveTrain()
	{
		frontLeft = new Victor(RobotMap.frontLeft);
		frontRight = new Victor(RobotMap.frontRight);
		backLeft = new Victor(RobotMap.backLeft);
		backRight = new Victor(RobotMap.backRight);
		gyro = new AnalogGyro(RobotMap.gyro);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	frontLeft.set(0);
    	frontRight.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    }
    
    public void displayGyroValue() {
    	SmartDashboard.putString("gyroAngle", Double.toString(gyro.getAngle()));
    	SmartDashboard.putString("gyroRate", Double.toString(gyro.getRate()));
    }
    
    public void calibrateGyro()
    {
    	gyro.calibrate();
    }
    
    public void resetGyro()
    {
    	gyro.reset();
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
    BuiltInAccelerometer accel = new BuiltInAccelerometer();
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		SmartDashboard.putString("x", Double.toString(accel.getX()));
    	SmartDashboard.putString("y", Double.toString(accel.getY()));
    	SmartDashboard.putString("z", Double.toString(accel.getZ()));
    	this.displayGyroValue();
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		this.calibrateGyro();
		
	}
}

