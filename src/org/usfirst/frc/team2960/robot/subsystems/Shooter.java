// Eric Sung

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {
	
	private double currentAngle;

	private boolean isFalling;
	private boolean isRising;
	private boolean isShooting;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		move();
		if(isShooting)
			extend();
		else
			retract();
	}

	@Override
	public void start() {
		currentAngle = 0.0;
		isFalling = false;
		isRising = false;
		isShooting = false;
	}

	public void move() {}

	public void extend() {}

	public void retract() {}

	public void setFall(boolean isActive) {isFalling = isActive;}

	public void setRise(boolean isActive) {isRising = isActive;}

	public void setShooter(boolean isActive) {isShooting = isActive;}
}
