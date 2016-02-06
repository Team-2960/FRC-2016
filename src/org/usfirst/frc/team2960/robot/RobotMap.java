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
	public static int RtDriveMt2 = 2;
	public static int LtDriveMt1 = 1;
	public static int LtDriveMt2 = 3;
	public static int WinchMt1 = 4;
	public static int WinchMt2 = 5;
	public static int AngleAdjust = 6;
	public static int Roller = 7;
	
	//Analog
	public static int gyro = 0;
	public static int SelectorSwitch = 1;
	
	//DigitalIO
	public static int RtDriveEncA = 0;
	public static int RtDriveEncB = 1;
	public static int LtDriveEncA = 2;
	public static int LtDriveEncB = 3;
	public static int ShooterAngle1 = 4;
	public static int ShooterAngle2 = 5;
	public static int WinchPhotoeye = 6;
	public static int AnglePhotoeyeUp = 7;
	public static int AnglePhotoEyeDown = 8;
	public static int BallDetectionPhotoeye = 9;
	
	//Break out
	public static int WinchEncA = 11;
	public static int WinchEncB = 13;
	
	
	
	
	
	//other values
	public static double moveP = .0001;
	public static double moveI = 0;
	public static double moveD = 0;
	public static double turnControlP = .0001;
	public static double turnControlI = 0;
	public static double turnControlD = 0;
}
