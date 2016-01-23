package org.usfirst.frc.team2960.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static int frontRight = 1;
	public static int frontLeft = 0;
	public static int backRight = 2;
	public static int backLeft = 3;
	public static int gyro = 0;
	public static double turnControlP = .00005;
	public static double turnControlI = 0;
	public static double turnControlD = 0;
}
