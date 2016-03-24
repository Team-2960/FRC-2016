
package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
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
	DigitalInput limitSwitch;
	PIDController angleController;
	AngleControl angleControl;                                      
	final double DEGREES_PER_PULSE = 360.0*(1.0/2048.0);
	final double DEGREES_PER_SECOND = 55;
	final double ANGLE_SLOWDOWN = 30;
	final double LOWER_LIMIT = -90;
	final double UPPER_LIMIT = -10;
	final double CURRENT_LIMIT = 30;
	final double BALANCE_ANGLE = -60.0;
	final double BATTER_ANGLE = -70;
	double anglePosition;
	boolean zeroing;
	boolean moveWinch;
	boolean notTripped;
	boolean useAngle;
	boolean balance;
	public boolean doneZeroing;
	public boolean manualWinch;
	PowerDistributionPanel pdp;
	public BuiltInAccelerometer accel;

	public Shooter()
	{
		angleAdjust = new VictorSP(RobotMap.AngleAdjust);
		Winch1 = new VictorSP(RobotMap.WinchMt1);
		Winch2 = new VictorSP(RobotMap.WinchMt2);
		angleEncoder = new Encoder(RobotMap.ShooterAngleA,RobotMap.ShooterAngleB);		
		shooterPhotoeye = new DigitalInput(RobotMap.ShooterPhotoeye);
		anglePhotoeye = new DigitalInput(RobotMap.AnglePhotoEye);
		limitSwitch = new DigitalInput(RobotMap.AngleLimitSwitch);
		angleEncoder.setPIDSourceType(PIDSourceType.kRate);
		angleEncoder.setDistancePerPulse(DEGREES_PER_PULSE);
		angleControl = new AngleControl(this);
		angleController = new PIDController(RobotMap.angleP,RobotMap.angleI,RobotMap.angleD,angleEncoder, angleControl);
		//angleEncoder.setIndexSource(anglePhotoeye, Encoder.IndexingType.kResetOnFallingEdge);
		anglePosition = 0;
		zeroing = true;
		moveWinch = false;
		notTripped = false;
		manualWinch = false;
		System.out.println("Deg per pulse: " + DEGREES_PER_PULSE);
		pdp = new PowerDistributionPanel();
		balance = false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(zeroing)
		{
			zeroRoutine();
		}
		if(angleController.isEnabled() && useAngle) 
		{
			updateAngle();
		}
		if(shooterPhotoeye.get() == false)
		{
			notTripped = true;
		}
		if(notTripped == true && shooterPhotoeye.get() == true)
		{
			notTripped = false;
			moveWinch = false;
		}
		if(moveWinch == true)
		{
			moveWinch();
		}
		else if(manualWinch != true)
		{
			stopWinch();
		}
		if(balance == true)
		{
			this.setAngle(this.calculateBalanceAngle());
		}
		/*if(pdp.getCurrent(RobotMap.angleAdjustChannel) < CURRENT_LIMIT)
		{
			angleController.setSetpoint(0);
			angleAdjust.set(0);
		}*/
		SmartDashboard.putNumber("angleEncoder Rate", angleEncoder.getRate());
		//ShooteSmartDashboard.putNumber("ideal angle",anglePosition);*/
		SmartDashboard.putNumber("angleEncoder get", angleEncoder.get());
		SmartDashboard.putNumber("angleEncoder dist", angleEncoder.getDistance());
		SmartDashboard.putNumber("accelerometer getX", accel.getX());
		SmartDashboard.putNumber("accelerometer getY", accel.getY());
		SmartDashboard.putNumber("accel angle", calculateBalanceAngle());
		//SmartDashboard.putBoolean("angle limit switch dist", limitSwitch.get());
		SmartDashboard.putBoolean("zeroing", zeroing);
		SmartDashboard.putNumber("angleAdjust current", pdp.getCurrent(RobotMap.angleAdjustChannel));
		SmartDashboard.putBoolean("anglePhotoeye", anglePhotoeye.get());
		SmartDashboard.putBoolean("shooterPhotoeye", shooterPhotoeye.get());
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	public void startPID()
	{
		//angleController.enable();
	}

	public void stopPID()
	{
		//angleController.disable();
	}

	public void setAngle(double position) //pos in degrees
	{
		anglePosition = position;
		useAngle = true;
	}
	
	public void addAngle(double angle)
	{
		anglePosition = anglePosition + angle;
		useAngle = true;
	}
	
	public void setRate(double rate)
	{
		adjustAngle(rate);
		if(!useAngle)
		{
			if(rate > 0 && angleEncoder.getDistance() >= UPPER_LIMIT)
			{
				angleController.setSetpoint(0);
			}
			else if(rate < 0 && angleEncoder.getDistance() <= LOWER_LIMIT)
			{
				angleController.setSetpoint(0);
			}
			else
			{
				angleController.setSetpoint(DEGREES_PER_SECOND*rate);
			}
		}
	}
	
	public double calculateBalanceAngle()
	{
		return BALANCE_ANGLE - getAccelAngle();
	}
	
	public double getAccelAngle()
	{
		return Math.atan(accel.getY()/-accel.getZ());
	}

	public void adjustAngle(double speed)
	{
		angleAdjust.set(speed);
		
		/*if(speed > 0 && anglePhotoeye.get() == false)
		{
			angleAdjust.set(0);
			//angleController.setSetpoint(0);
		}
		else if(speed < 0 && limitSwitch.get() == false)
		{
			angleAdjust.set(0);
			//angleController.setSetpoint(0);
		}
		else
		{
			angleAdjust.set(speed);
		}*/
	}

	public void toggleWinch()
	{
		moveWinch = true;
	}
	
	public void balance()
	{
		balance = !balance;
	}
	
	public void stopBalance()
	{
		balance = false;
	}
	
	public void batterSetpoint()
	{
		setAngle(BATTER_ANGLE);
	}
	
	public void updateAngle()
	{
		double currentDist = angleEncoder.getDistance(); //in degrees
		double error = anglePosition - currentDist;
		double rate;
		if(currentDist < anglePosition + 2 && currentDist > anglePosition - 2)
		{
			angleController.setSetpoint(0);
			useAngle = false;
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
			angleAdjust.set(1.0);
		}
		else if(zeroing == true && anglePhotoeye.get() == false)
		{
			angleAdjust.set(0);
			angleEncoder.reset();
			startPID();
			zeroing = false;
		}
	}
	
	public boolean zeroing()
	{
		return zeroing;
	}

	//pullback that has photo eye
	//start in auton

	public void moveWinch()
	{
		Winch1.set(1.0);
		Winch2.set(1.0);
	}
	
	public void moveWinchManual()
	{
		manualWinch = true;
		moveWinch();
	}

	public void stopWinch()
	{
		manualWinch = false;
		Winch1.set(0);
		Winch2.set(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
