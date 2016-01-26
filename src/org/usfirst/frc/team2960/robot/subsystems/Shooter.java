// Eric Sung

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {

	private Victor lift;
	private Victor spring;

	private Encoder checkAngle;
	private Encoder checkPosition;

	private int currentAngle;
	private int currentPosition;

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

		newPosition();
		newAngle();
	}

	@Override
	public void start() {
		lift = new Victor();
		spring = new Victor();

		checkAngle = new Encoder();
		checkPosition = new Encoder();

		currentAngle = 0;
		currentPosition = 0;

		setFall(false);
		setRise(false);
		setPosition(false);
		setRetracting(false);
		setOverride(false);
	}

	public void check() {
		if(currentPosition == )
			setPosition(true);
		else if(isRetracting && isOverwritten)
			setRetracting(false);
		else if (isRetracting && !isOverwritten)
			setRetracting(true);
	}

	public void newAngle() {
		currentAngle = checkAngle.get();
	}

	public void newPosition() {
		currentPosition = checkPosition.get();
	}

	public void setFall(boolean isActive) {isFalling = isActive;}

	public void setRise(boolean isActive) {isRising = isActive;}

	public void setPosition(boolean isReady) {isInPosition = isReady;}

	public void setRetracting(boolean isActive) {isRetracting = isActive;}

	public void setOverride(boolean isActive) {isOverwritten = isActive;}
}
