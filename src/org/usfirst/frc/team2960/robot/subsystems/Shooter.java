package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem implements PeriodicUpdate {
    
	boolean isActive;
	
	Solenoid solenoid;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		if(isActive)
			actuateSolenoid(solenoid);
	}

	@Override
	public void start() {
		isActive = false;
		solenoid = new Solenoid(0);
	}
	
	public void setPneumatics(boolean isActive) {
		this.isActive = isActive;
	}
		
	public void actuateSolenoid(Solenoid solenoid) {
    	solenoid.set(!solenoid.get());
    }
}
