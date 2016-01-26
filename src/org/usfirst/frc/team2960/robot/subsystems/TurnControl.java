package org.usfirst.frc.team2960.robot.subsystems;


import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnControl implements PIDOutput {
	DriveTrain drive;
	TurnControl(DriveTrain drive){
		this.drive = drive;
	}
	@Override
	public void pidWrite(double output) {
		drive.setSpeed(output, -output);
		SmartDashboard.putString("PID output", Double.toString(output));
	}

	
	
}
