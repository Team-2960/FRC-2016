package org.usfirst.frc.team2960.robot.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;

public class LinearDriveControl implements PIDOutput{
	DriveTrain drive;
	LinearDriveControl(DriveTrain drive){
		this.drive = drive;
	}
	@Override
	public void pidWrite(double output) {
		drive.setSpeed(output, output);
		
	}

}
