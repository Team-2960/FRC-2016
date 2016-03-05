
package org.usfirst.frc.team2960.robot;

import org.usfirst.frc.team2960.robot.subsystems.Camera;
import org.usfirst.frc.team2960.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2960.robot.subsystems.Pickup;
import org.usfirst.frc.team2960.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot { public int autonIndex = 

	/********************************************************************************************
	 * ____ _  _ ___ ____ _  _    _  _ ____ ___  ____    ____ ____ _    ____ ____ ___ ____ ____ *
     * |__| |  |  |  |  | |\ |    |\/| |  | |  \ |___   |___  |___ |    |___ |     |  |  | |__/ *
	 * |  | |__|  |  |__| | \|    |  | |__| |__/ |___   ____| |___ |___ |___ |___  |  |__| |  \ *
	 * -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
     * 
     * CHOOSE A NUMBER FROM THE LIST BELOW CORRESPONDING TO THE AUTONOMOUS MODE YOU WANT TO RUN.
     * 
     * AUTONOMOUS MODES LIST
     * -=-=-=-=-=-=-=-=-=-=-=-=-
     * 0 = DRIVE FORWARD
     * 1 = DRIVE BACKWARDS
     * 2 = DRIVE SHORT DISTANCE FORWARD - not working                                        
     *                                                    
     * CHANGE THIS NUMBER TO THE NUMBER OF YOUR CHOICE THAT CORRESPONDS WITH THE LIST ABOVE.                                                                                  
     * THIS NUMBER!!! ------>>>>____*/ 1 /*____<<<<------ THIS NUMBER HERE
     *                                                                 
     * DO NOT MODIFY ANY OTHER CODE IN THIS FILE OR
     * ANYWHERE ELSE UNLESS YOU KNOW WHAT YOU ARE DOING!
     * 
     * TO RUN THIS NEW PROGRAM, PRESS THE CIRCULAR GREEN PLAY BUTTON IN THE TOOLBAR ABOVE
     * AND SELECT "WPILib Java Deploy" WHILE CONNECTED TO ROBOT WIFI. IT SHOULD THEN
     * DOWNLOAD AND CHANGES WILL BE REFLECTED BY THE ROBOT.
     *                                                                                   
	 *********************************************************************************************/;
	
	public static OI oi;

    Command autonomousCommand;
    Joystick driveJoystick;
    Joystick operatorJoystick;
    DriveTrain driveTrain;
    Shooter shooter;
    Pickup pickup;
    Camera camera;
    PowerDistributionPanel pdp;
    BuiltInAccelerometer accel;
    
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
		shooter = new Shooter();
		pickup = new Pickup();
		accel = new BuiltInAccelerometer();
		shooter.accel = accel;
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
		//camera.update();
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
		shooter.update();
		pickup.update();
		camera.update();
		
	}
	
	public void periodicStart()
	{
		shooter.start();
    	camera.start();
    	driveTrain.start();
	}
	
    public void autonomousInit() {
    	// schedule the autonomous command (example)
    	periodicStart();
    	Autonomous.driveTrain = driveTrain;
    	Autonomous.shooter = shooter;
    	Autonomous.pickup = pickup;
    	Autonomous.startCommand(Autonomous.getAutonCommandAtIndex(autonIndex));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	periodicUpdate();
    }
    
    
    public void teleopInit() {
    	periodicStart();
   }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	oi.driveRobot(driveJoystick, driveTrain, shooter, pickup);
    	oi.operateRobot(operatorJoystick, shooter, pickup);
    	periodicUpdate();
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
}
