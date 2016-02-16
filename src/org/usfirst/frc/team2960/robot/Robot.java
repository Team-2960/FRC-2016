
package org.usfirst.frc.team2960.robot;

import org.usfirst.frc.team2960.robot.subsystems.Camera;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

    Command autonomousCommand;
    Joystick driveJoystick;
    Joystick operatorJoystick;
    DriveTrain driveTrain;
    Shooter shooter;
    Pickup pickup;
    Camera camera;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		camera = new Camera();
    	camera.start();
		driveTrain = new DriveTrain(camera);
		driveJoystick = new Joystick(0);
		operatorJoystick = new Joystick(1);
		//shooter = new Shooter();
		pickup = new Pickup();
	
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		camera.update();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
	
	public void periodicUpdate(){
		driveTrain.update();
		//shooter.update();
		pickup.update();
		camera.update();
		
	}
    public void autonomousInit() {
    	// schedule the autonomous command (example)
    	Autonomous.startCommand(Autonomous.getAutonCommandAtIndex(0));
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	AutonomousCommand command = Autonomous.getAutonCommandAtIndex(0);
    	if(command.isFinished()){
    		Autonomous.stopAuton();
    	}
    		
    }
    
    
    public void teleopInit() {
   }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	oi.driveRobot(driveJoystick, driveTrain);
    	oi.operateRobot(driveJoystick, shooter, pickup);
    	periodicUpdate();
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
}
