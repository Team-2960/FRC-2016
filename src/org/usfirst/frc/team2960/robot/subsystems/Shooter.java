// Eric Sung

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {

	private Victor lift;
	private Victor spring;

	private boolean isFalling;
	private boolean isRising;
	private boolean isInPosition;
	private boolean isRetracting;
	private boolean isOverwritten;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		check();
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
	}

	@Override
	public void start() {
		lift = new Victor(5);
		spring = new Victor(6);
		setFall(false);
		setRise(false);
		setPosition(false);
		setRetracting(false);
		setOverride(false);
	}

	public void check() {
		if(isRetracting && isOverwritten)
			setRetracting(false);
		else if (isRetracting && !isOverwritten)
			setRetracting(true);
	}

	public void setFall(boolean isActive) {isFalling = isActive;}

	public void setRise(boolean isActive) {isRising = isActive;}

	public void setPosition(boolean isReady) {isInPosition = isReady;}

	public void setRetracting(boolean isActive) {isRetracting = isActive;}

	public void setOverride(boolean isActive) {isOverwritten = isActive;}
}
