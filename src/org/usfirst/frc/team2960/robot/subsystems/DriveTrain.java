package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem implements PeriodicUpdate {
    
	
	VictorSP LtDriveMt1;
	VictorSP LtDriveMt2;
	VictorSP RtDriveMt1;
	VictorSP RtDriveMt2;
	AnalogGyro gyro;
	Encoder RightDriveEnc;
	Encoder LeftDriveEnc;
	TurnControl turn; 
	LinearDriveControl linear;
	PIDController turning;
	PIDController move;
	LinearDriveControlInput input;
	public boolean moveStop = false;
	boolean isEnadled;
	double angleSetpoint = 0;
	double lengthSetPoint = 0;
	final int tolerance = 2;
	final double angleSlowDown = 50;
	final double linearSlowDown = 5;
	final int slowDown = 10;
	final double rateTolerance = 5;
	int RateSetPoint = 50;
	int angleDirection;
	double Rate;
	double linearRate;
	final double FINALRATE = 200;
	final double FINALLINEARRATE = 100;
	public boolean TurnOnTheTurn =  false;
	Camera camera;
	public double encoderTotal;
	final int lengthTolerance = 5;
	final double distancePerPulse = ((12.0 * Math.PI) / (1.0)) * (15.0 / 16.0) * (1.0 / 2048.0);
	
	public DriveTrain(Camera Cam)
	{
		LtDriveMt1 = new VictorSP(RobotMap.LtDriveMt1);
		LtDriveMt2 = new VictorSP(RobotMap.LtDriveMt2);
		RtDriveMt1 = new VictorSP(RobotMap.RtDriveMt1);
		RtDriveMt2 = new VictorSP(RobotMap.RtDriveMt2);
		gyro = new AnalogGyro(RobotMap.gyro);
		turn = new TurnControl(this);
		turning = new PIDController(RobotMap.turnControlP, RobotMap.turnControlI, RobotMap.turnControlD, gyro, turn);
		gyro.setPIDSourceType(PIDSourceType.kRate);
		RightDriveEnc = new Encoder(RobotMap.RtDriveEncA, RobotMap.RtDriveEncB);
		RightDriveEnc.setDistancePerPulse(distancePerPulse); 
		LeftDriveEnc = new Encoder(RobotMap.LtDriveEncA, RobotMap.LtDriveEncB);
		LeftDriveEnc.setDistancePerPulse(distancePerPulse);
		LeftDriveEnc.setMaxPeriod(0.1);
		RightDriveEnc.setMaxPeriod(0.1);
		RightDriveEnc.reset();
		LeftDriveEnc.reset();
		camera = Cam;
		input = new LinearDriveControlInput(this);
		linear = new LinearDriveControl(this);
		move = new PIDController(RobotMap.moveP, RobotMap.moveI, RobotMap.moveD, input, linear);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	RtDriveMt1.set(0);
    	RtDriveMt2.set(0);
    	LtDriveMt1.set(0);
    	LtDriveMt2.set(0);
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
    	//RightDriveEnc.reset();
    }
    
    
    
    
    public void setSpeed(Double left, Double right){
    	System.out.print("Left: ");
    	System.out.print(left);
    	System.out.print(", Right: ");
    	System.out.print(right);
    	System.out.print("\n");
    	
    	LtDriveMt1.set(left);
    	LtDriveMt2.set(left);
    	RtDriveMt1.set(-right);
    	RtDriveMt2.set(-right);
    }
   
    public void gotoDistance(double distance){
    	lengthSetPoint = distance;
    	move.enable();
    	moveStop = true;
    }
   
    public void gotoAngle(double angle){
    	angleSetpoint = angle;
    	turning.enable();
    	moveStop = true;
    	
    }
    public void addAngle(double angle){
    	angleSetpoint = gyro.getAngle() + angle;
    	turning.enable();
    	moveStop = true;
    }
    
    public void disablePIDAngle(){
    	if(turning.isEnabled()){
    	turning.disable();
    	moveStop = false;
    	
    	}
    }
    
    public void disablePIDForward()
    {
    	if(move.isEnabled()){
    		move.disable();
    		moveStop = false;
    	}
    	
    }
    
    
    public void checkLinear(){
    	double error = lengthSetPoint - encoderTotal;
    	if(move.isEnabled()){
    		if(encoderTotal >= (lengthSetPoint - lengthTolerance) && encoderTotal <= (lengthSetPoint + lengthTolerance)){
    			move.setSetpoint(0);
    		}
    		else if(error > -linearSlowDown && error < linearSlowDown){
    			linearRate = error/linearSlowDown * FINALLINEARRATE;
    			move.setSetpoint(linearRate);
    		}
    		else if(encoderTotal > lengthSetPoint){
    			move.setSetpoint(-FINALLINEARRATE);
    		}
    		else if(encoderTotal < lengthSetPoint){
    			move.setSetpoint(FINALLINEARRATE);
    		}
    	}
    }
    public void checkAngle(){
    	
	    if(turning.isEnabled()){
		double error = angleSetpoint - gyro.getAngle(); 	
			
	    	if((gyro.getAngle() >= (angleSetpoint - tolerance)) && (gyro.getAngle() <= (angleSetpoint + tolerance))){
	    		turning.setSetpoint(0);
	    		
	    		
	    		if((gyro.getRate() >= (-rateTolerance)) && (gyro.getRate() <= (rateTolerance)) ){
	    			disablePIDAngle();
	    			}
	    	
	    		
	    		
	    	}
	    	else if(error > -angleSlowDown && error < angleSlowDown){
	    		Rate = error/angleSlowDown * FINALRATE;
	    		turning.setSetpoint(Rate);
	    	}
	    	else if(gyro.getAngle() > angleSetpoint){
	    		turning.setSetpoint(-FINALRATE);
	    	}
	    	else if(gyro.getAngle() < angleSetpoint){
	    		turning.setSetpoint(FINALRATE);
	    	}
	    	
	    	
	    		
	    }
	    
	    }
   
    
    
     public BuiltInAccelerometer accel = new BuiltInAccelerometer();
	@Override
	public void update() {
		encoderTotal = ((RightDriveEnc.getRate() + LeftDriveEnc.getRate()) / 2);
		if(TurnOnTheTurn){
			addAngle(camera.getAngle());
		}
		else if(TurnOnTheTurn == false && turning.isEnabled()){
			turning.disable();
			moveStop = false;
		}
		
		checkLinear();
		
		
		SmartDashboard.putBoolean("R Encoder direction", RightDriveEnc.getDirection());
		SmartDashboard.putString("R Encoder Distance", Double.toString(RightDriveEnc.getDistance()));
		SmartDashboard.putString("R Encoder count", Double.toString(RightDriveEnc.get()));
		SmartDashboard.putString("R Encoder Rate", Double.toString(RightDriveEnc.getRate()));
		SmartDashboard.putString("Encoder Distance Per Pulse", Double.toString(distancePerPulse));
		SmartDashboard.putBoolean("L Encoder direction", LeftDriveEnc.getDirection());
		SmartDashboard.putString("L Encoder Distance", Double.toString(LeftDriveEnc.getDistance()));
		SmartDashboard.putString("L Encoder count", Double.toString(LeftDriveEnc.get()));
		SmartDashboard.putString("L Encoder Rate", Double.toString(LeftDriveEnc.getRate()));
		/*
		SmartDashboard.putString("x", Double.toString(accel.getX()));
    	SmartDashboard.putString("y", Double.toString(accel.getY()));
    	SmartDashboard.putString("z", Double.toString(accel.getZ()));
    	SmartDashboard.putNumber("setpont", turning.getSetpoint());
		SmartDashboard.putNumber("error", turning.getError());
		*/
    
    	this.displayGyroValue();
    	checkAngle();
    	
    	
	}

	@Override
	public void start() {
		this.resetGyro();
		//this.calibrateGyro();
		
	}
}

