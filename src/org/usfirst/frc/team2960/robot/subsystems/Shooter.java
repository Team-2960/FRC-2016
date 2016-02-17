// Eric Sung, Malcolm and Andrew

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {

	VictorSP angleAdjust;
	VictorSP Winch1;
	VictorSP Winch2;
	
	public Shooter()
	{
		angleAdjust = new VictorSP(RobotMap.AngleAdjust);
		Winch1 = new VictorSP(RobotMap.WinchMt1);
		Winch2 = new VictorSP(RobotMap.WinchMt2);	
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void adjustAngle(double speed)
	{
		angleAdjust.set(speed);
	}
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private Victor lift = new Victor(0); // Change
	private Victor spring = new Victor(0); // Change

	private Encoder checkAngle = new Encoder(null, null); // Change
	private Encoder checkPosition = new Encoder(null, null); // Change

	private PIDController changing;
	private PIDOutput output;

	private double angle;
	private double currentAngle;
	private double currentPosition;

	private boolean isFalling;
	private boolean isRising;
	private boolean isInPosition;
	private boolean isRetracting;
	private boolean isOverwritten;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		check();

		if(!changing.isEnabled())
			changing.enable();

		if(isRetracting)
			spring.set(1.0);
		else
			spring.set(0.0);

		if(isInPosition) {
			setPosition(false);
			setRetracting(false);
		}

		if(isFalling)
			lift.set(-1.0);
		else if(isRising)
			lift.set(1.0);
		else
			lift.set(0.0);

		newPosition();
		newAngle();
	}

	@Override
	public void start() {
		// double, double, double, double, PIDSource, PIDOutput
		changing = new PIDController(0.0, 0.0, 0.0, 0.0, checkAngle, lift); // Change

		currentAngle = 0;
		currentPosition = 0;

		setAngle(0);

		setFall(false);
		setRise(false);
		setPosition(false);
		setRetracting(false);
		setOverride(false);

		zero();

		checkAngle.reset();
		checkPosition.reset();
	}

	public void check() {
		if(currentPosition <= 0 && currentPosition >= 0) // Change
			setPosition(true);
		else if(isRetracting && isOverwritten)
			setRetracting(false);
		else if (isRetracting && !isOverwritten)
			setRetracting(true);
	}

	public void changeAngle() { // Change
		if(currentAngle < angle) {
			setFall(false);
			setRise(true);
		}
		else if (currentAngle > angle) {
			setFall(true);
			setRise(false);
		}
		else {
			setFall(false);
			setRise(false);
			changing.disable();
		}
	}

	public void newAngle() { // Change
		output.pidWrite(changing.get());
		currentAngle = checkAngle.get();
	}

	public void newPosition() { // Change
		currentPosition = checkPosition.get();
	}

	public void zero() { // Change
		changing.enable();
		setAngle(0);
		setRise(true);
		while(isFalling || isRising) {
			changeAngle();
		}
	}

	public double getCurrentAngle() {return currentAngle;}
	
	public void setAngle(int newAngle) {angle = newAngle;}

	public void setFall(boolean isActive) {isFalling = isActive;}

	public void setRise(boolean isActive) {isRising = isActive;}

	public void setPosition(boolean isReady) {isInPosition = isReady;}

	public void setRetracting(boolean isActive) {isRetracting = isActive;}

	public void setOverride(boolean isActive) {isOverwritten = isActive;}*/
}