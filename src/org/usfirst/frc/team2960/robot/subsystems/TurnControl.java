package org.usfirst.frc.team2960.robot.subsystems;


import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnControl implements PIDOutput {
	DriveTrain drive;
	final double DEADBAND = .2;
	TurnControl(DriveTrain drive){
		this.drive = drive;
	}
	@Override
	public void pidWrite(double output) {
		if (output > 0){
			output = output * ( 1 - DEADBAND) + DEADBAND; 
		}
		else if(output < 0){
			output = output * (1 - DEADBAND) - DEADBAND;
		}
		else{
			output = 0;
		}
		drive.setSpeed(-output, output);
		SmartDashboard.putString("PID output", Double.toString(output));
	}

	
	
}
