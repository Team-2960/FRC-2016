package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
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
	Encoder encoder1;
	TurnControl turn; 
	PIDController turning;
	PIDController move;
	public  boolean moveStop;
	boolean hitTargetRate;
	boolean isEnadled;
	int angleSetpoint = 0;
	public DriveTrain()
	{
		frontLeft = new Victor(RobotMap.frontLeft);
		frontRight = new Victor(RobotMap.frontRight);
		backLeft = new Victor(RobotMap.backLeft);
		backRight = new Victor(RobotMap.backRight);
		gyro = new AnalogGyro(RobotMap.gyro);
		turn = new TurnControl(this);
		turning = new PIDController(RobotMap.turnControlP, RobotMap.turnControlI, RobotMap.turnControlD, gyro, turn);
		gyro.setPIDSourceType(PIDSourceType.kRate);
		encoder1 = new Encoder(RobotMap.encoder1, RobotMap.encoder1b);
		encoder1.setDistancePerPulse(.1); 
		//move = new PIDController(RobotMap.moveP, RobotMap.moveI, RobotMap.moveD, encoder1, pidWrite);
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
    public void resetEncoder()
    {
    	encoder1.reset();
    }
    public void move(double speed) {
    	speed = speed * 0.75;
    	frontLeft.set(-speed);
    	frontRight.set(-speed);
    	backLeft.set(speed);
    	backRight.set(speed);
    	//SmartDashboard.putString("Speed", Double.toString(speed));
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
    public void setSpeed(Double left, Double right){
    	frontLeft.set(left);
    	backLeft.set(left);
    	frontRight.set(-right);
    	backRight.set(-right);
    }
    public void move()
    {
    	
    }
    public void turn90(){
    	angleSetpoint = 90;
    	gyro.reset();
    	turning.enable();
    	turning.setSetpoint(50);
    	moveStop = true;
    }
    public void disablePID(){
    	if(turning.isEnabled()){
    	turning.disable();
    	moveStop = false;
    	}
    }
     public BuiltInAccelerometer accel = new BuiltInAccelerometer();
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		SmartDashboard.putBoolean("Encoder direction", encoder1.getDirection());
		SmartDashboard.putString("Encoder Distance", Double.toString(encoder1.getDistance()));
		SmartDashboard.putString("Encoder count", Double.toString(encoder1.get()));
		SmartDashboard.putString("Encoder Rate", Double.toString(encoder1.getRate()));
		SmartDashboard.putString("x", Double.toString(accel.getX()));
    	SmartDashboard.putString("y", Double.toString(accel.getY()));
    	SmartDashboard.putString("z", Double.toString(accel.getZ()));
    
    	this.displayGyroValue();
    	
    	if(gyro.getRate() > 50){
    			hitTargetRate = true;
    			//SmartDashboard.putBoolean("hit target rate",hitTargetRate);
    	}
    	if(gyro.getAngle() < -angleSetpoint && turning.isEnabled()){
    		
    		turning.disable();
    	}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		this.calibrateGyro();
		
	}
}

