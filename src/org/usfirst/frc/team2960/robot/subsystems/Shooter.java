// Eric Sung

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {
	
	private Victor spring;

	private boolean isInPosition;
	private boolean isRetracting;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		override();
		if(isRetracting)
			spring.set(1.0);
		else
			spring.set(0.0);
		if(isInPosition)
			setInPosition(false);
	}

	@Override
	public void start() {
		spring = new Victor(/**/);
		isInPosition = false;
		isRetracting = false;
	}

	public void override() {
		if(isRetracting)
			setRetracting(false);
		else
			setRetracting(true);
	}

	public void setInPosition(boolean isReady) {isInPosition = isReady;}

	public void setRetracting(boolean isActive) {isRetracting = isActive;}
}
