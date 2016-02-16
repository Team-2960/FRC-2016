package org.usfirst.frc.team2960.robot.subsystems;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;
import org.usfirst.frc.team2960.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pickup extends Subsystem implements PeriodicUpdate {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//1 motor for actual intake and another so we can adjust stuff
	public VictorSP roller = new VictorSP(4);
	public DoubleSolenoid intakeHood = new DoubleSolenoid(RobotMap.intakeHoodA,RobotMap.intakeHoodB);
	public DoubleSolenoid intakeHooks = new DoubleSolenoid(RobotMap.intakeHooksA,RobotMap.intakeHooksB);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public void intake(double speed) {
		roller.set(speed);
	}
	
	public void setHood(boolean open)
	{
		if(open)
			intakeHooks.set(DoubleSolenoid.Value.kForward);
		else
			intakeHooks.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setHook(boolean open)
	{
		if(open)
			intakeHood.set(DoubleSolenoid.Value.kForward);
		else
			intakeHood.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setRoller(double speed)
	{
		roller.set(speed);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}

