
package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem implements PeriodicUpdate {

	//LIMIT 4 to -80
	
	//-1.5 
	
	VictorSP angleAdjust;
	VictorSP Winch1;
	VictorSP Winch2;
	Encoder angleEncoder;
	DigitalInput shooterPhotoeye;
	DigitalInput anglePhotoeye;
	PIDController angleController;
	AngleControl angleControl;
	final double DEGREES_PER_PULSE = 360.0*(1.0/2048.0);
	final double DEGREES_PER_SECOND = 20;
	final double ANGLE_SLOWDOWN = 10;
	final double LOWER_LIMIT = -57.0;
	final double UPPER_LIMIT = 16.0;
	double anglePosition;
	boolean zeroing;
	boolean isMovingBack; 

	public Shooter()
	{
		angleAdjust = new VictorSP(RobotMap.AngleAdjust);
		Winch1 = new VictorSP(RobotMap.WinchMt1);
		Winch2 = new VictorSP(RobotMap.WinchMt2);
		angleEncoder = new Encoder(RobotMap.ShooterAngleA,RobotMap.ShooterAngleB);		
		shooterPhotoeye = new DigitalInput(RobotMap.ShooterPhotoeye);
		anglePhotoeye = new DigitalInput(RobotMap.AnglePhotoEye);
		angleEncoder.setPIDSourceType(PIDSourceType.kRate);
		angleEncoder.setDistancePerPulse(DEGREES_PER_PULSE);
		angleControl = new AngleControl(this);
		angleController = new PIDController(RobotMap.angleP,RobotMap.angleI,RobotMap.angleD,angleEncoder, angleControl);
		//angleEncoder.setIndexSource(anglePhotoeye, Encoder.IndexingType.kResetOnFallingEdge);
		anglePosition = 0;
		zeroing = true;
		isMovingBack = false;
		System.out.println("Deg per pulse: " + DEGREES_PER_PULSE);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(zeroing)
		{
			zeroRoutine();
		}
		if(angleController.isEnabled())
		{
			updateAngle();
		}
		if(isMovingBack && shooterPhotoeye.get() == false)
		{
			stopMovingBack();
		}
		SmartDashboard.putNumber("angleEncoder Rate", angleEncoder.getRate());
		SmartDashboard.putNumber("angleEncoder get", angleEncoder.get());
		SmartDashboard.putNumber("angleEncoder dist", angleEncoder.getDistance());
		SmartDashboard.putBoolean("anglePhotoeye", anglePhotoeye.get());
		SmartDashboard.putBoolean("shooterPhotoeye", shooterPhotoeye.get());
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	public void startPID()
	{
		angleController.enable();
	}

	public void stopPID()
	{
		angleController.disable();
	}

	public void setAngle(double position) //pos in degrees
	{
		anglePosition = position;
	}

	public void adjustAngle(double speed)
	{
		if(speed > 0 && angleEncoder.getDistance() >= UPPER_LIMIT)
		{
			angleAdjust.set(0);
		}
		else if(speed < 0 && angleEncoder.getDistance() <= LOWER_LIMIT)
		{
			angleAdjust.set(0);
		}
		else
		{
			angleAdjust.set(speed);
		}
	}

	public void moveBack()
	{
		Winch1.set(0.5);
		Winch2.set(0.5);
	}

	public void stopMovingBack()
	{
		Winch1.set(0);
		Winch2.set(0);
		isMovingBack = false;
	}

	public void updateAngle()
	{
		double currentDist = angleEncoder.getDistance(); //in degrees
		double error = anglePosition - currentDist;
		double rate;
		if(currentDist < anglePosition + 2 && currentDist > anglePosition - 2)
		{
			angleController.setSetpoint(0);
		}
		else if(error > -ANGLE_SLOWDOWN && error < ANGLE_SLOWDOWN)
		{
			rate = error/ANGLE_SLOWDOWN * DEGREES_PER_SECOND;
			angleController.setSetpoint(rate);
		}
		else if(currentDist > anglePosition)
		{
			angleController.setSetpoint(-DEGREES_PER_SECOND);
		}
		else if(currentDist < anglePosition)
		{
			angleController.setSetpoint(DEGREES_PER_SECOND);
		}
	}

	public void zeroRoutine()
	{
		if(zeroing == true && anglePhotoeye.get() == true)
		{
			angleAdjust.set(0.5);
		}
		else if(zeroing == true && anglePhotoeye.get() == false)
		{
			angleAdjust.set(0);
			angleEncoder.reset();
			//startPID();
			zeroing = false;
		}
	}

	public void shooterPullback()
	{
		moveBack();
		isMovingBack = true;
	}

	//pullback that has photo eye
	//start in auton


	public void moveWinch()
	{
		Winch1.set(1.0);
		Winch2.set(1.0);
	}

	public void stopWinch()
	{
		Winch1.set(0);
		Winch2.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
