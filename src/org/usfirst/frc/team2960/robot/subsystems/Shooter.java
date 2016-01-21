// Eric Sung

package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem implements PeriodicUpdate {
	
	private int pneumaticsPort;
	private boolean isActive;
	
	private Solenoid solenoid;
	
    public void initDefaultCommand() {}

	@Override
	public void update() {
		if(isActive)
			actuateSolenoid(solenoid);
	}

	@Override
	public void start() {
		isActive = false;
		solenoid = new Solenoid(pneumaticsPort);
	}
	
	public void setPneumatics(boolean isActive) {this.isActive = isActive;}
	
	public void setPort(int port) {pneumaticsPort = port;}
		
	public void actuateSolenoid(Solenoid solenoid) {solenoid.set(!solenoid.get());}
}
