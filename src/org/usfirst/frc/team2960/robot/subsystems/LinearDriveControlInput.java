package org.usfirst.frc.team2960.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LinearDriveControlInput implements PIDSource {
	DriveTrain drive;
	
	LinearDriveControlInput(DriveTrain drive){
		this.drive = drive;
	}
	
	
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		double output;
		output = ((drive.RightDriveEnc.getRate() + drive.LeftDriveEnc.getRate()) / 2);
		return output;
	}

}
