package org.usfirst.frc.team2960.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class AngleControl implements PIDOutput {

	public Shooter shooter;
	
	public AngleControl(Shooter shoot)
	{
		shooter = shoot;
	}
	
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		shooter.adjustAngle(output);
	}
	
}
