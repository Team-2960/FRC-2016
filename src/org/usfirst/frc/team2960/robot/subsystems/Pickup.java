package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pickup extends Subsystem implements PeriodicUpdate {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//1 motor for actual intake and another so we can adjust stuff
	public VictorSP adjustmentMotor = new VictorSP(4);
	public VictorSP intakeMotor = new VictorSP(5);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void intake(double speed) {
		intakeMotor.set(speed);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}

