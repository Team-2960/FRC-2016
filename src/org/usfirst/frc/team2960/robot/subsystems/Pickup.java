package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pickup extends Subsystem implements PeriodicUpdate {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//1 motor for actual intake and another so we can adjust stuff
	public Talon adjustStuffMotor = new Talon(4);
	public Talon actualIntakeMotor = new Talon(5);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}

