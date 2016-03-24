package org.usfirst.frc.team2960.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//PWM
	public static int RtDriveMt1 = 0;
	public static int RtDriveMt2 = 1;
	public static int LtDriveMt1 = 2;
	public static int LtDriveMt2 = 3;
	public static int WinchMt1 = 4;
	public static int WinchMt2 = 5;
	public static int AngleAdjust = 6;
	public static int Roller = 7;
	
	//Analog
	public static int gyro = 1;
	//public static int SelectorSwitch = 1;
	
	//DigitalIO
	public static int RtDriveEncA = 1;
	public static int RtDriveEncB = 0;
	public static int LtDriveEncA = 2;
	public static int LtDriveEncB = 3;
	public static int ShooterAngleA = 4;
	public static int ShooterAngleB = 5;
	public static int ShooterPhotoeye = 6;
	public static int AnglePhotoEye = 7;
	public static int AngleLimitSwitch = 8;
	public static int LeftRightSwitch = 9;
	//public static int BallDetectionPhotoeye = 8;
	
	//Break out
	public static int WinchEncA = 11;
	public static int WinchEncB = 13;
	
	//Pickup Solenoids
	public static int intakeHoodA = 2;
	public static int intakeHoodB = 3;
	public static int intakeHooksA = 0;
	public static int intakeHooksB = 1;
	
	//PDP channels
	public static int angleAdjustChannel = 2;
	
	//move pid
	public static double moveP = .001;
	public static double moveI = 0;
	public static double moveD = 0;
	
	//angle pid
	public static double angleP = .00175;
	public static double angleI = 0;
	public static double angleD = 0;
	
	
	//turn pid
	public static double turnControlP = .001;
	public static double turnControlI = 0;
	public static double turnControlD = 0;
	
	//relay 
	public static int light = 0;
}
