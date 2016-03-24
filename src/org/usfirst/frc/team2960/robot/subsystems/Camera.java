package org.usfirst.frc.team2960.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem implements PeriodicUpdate {
	NIVision.Range HUE_RANGE;
	NIVision.Range SAT_RANGE;
	NIVision.Range LUM_RANGE;
	final double SCORE_MIN = 75.0;
	//double VIEW_ANGLE = 58.08777;
	//final double ANGLE_PER_PIXEL = 0.1815242813;//0.045381; //new angle based on lower resolution
	final double CAMERA_ANGLE_OFFSET = 47.0*(Math.PI/180);
	final double CAMERA_PIXEL_OFFSET = 22.0;
	final double RESX = 320;
	final double RESY = 240;
	final double THETA_Y = 36.305; // THETAY/685 = 800/1509.437 H = sqrt(1280^2+800^2) = 1509.437
	final double THETA_X = 58.1;
	final double HEIGHT_GC = 70.75; //height of goal - robot camera - in inches
	final double ANGLE_PER_PIXEL = (THETA_Y/RESY);
	final double RAD_PER_PIXEL = ANGLE_PER_PIXEL*(Math.PI/180);
	private double distance = 0;
	private double angle = 0;
	private boolean particleVisible = false;
	USBCamera cam;
	Image frame;
	Image binaryFrame;
	NIVision.ParticleFilterCriteria2 criteria[];
	Scores scores;
	
	
	public Camera(){
		cam = new USBCamera("cam0");
		SAT_RANGE = new NIVision.Range(252, 255);
		HUE_RANGE = new NIVision.Range(88, 146);
		LUM_RANGE = new NIVision.Range(23, 255);
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_HSL, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		scores = new Scores();
		criteria = new NIVision.ParticleFilterCriteria2[1];
	}

	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    CameraServer server;
    public void takeSnapshot()
    {
    	//cam.setSize(1280, 800);
        cam.getImage(frame);
    }
    
    boolean shouldRun = true;
    public void findTarget()
    {
    	if(shouldRun == false)
    	{
    		shouldRun = true;
    		return;
    	}
    	takeSnapshot();
		//server.setImage(frame);
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSL, HUE_RANGE, SAT_RANGE, LUM_RANGE);
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		double minArea = 0.33;
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, minArea, 100.0, 0, 0);
		criteria[0].lower = (float) minArea;
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				particles.add(par);
			}
			particles.sort(null);
			//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
			//for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
			//about the location of the tote (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
			scores.Aspect = AspectScore(particles.elementAt(0));
		//	SmartDashboard.putNumber("Aspect", scores.Aspect);
			scores.Area = AreaScore(particles.elementAt(0));
		//	SmartDashboard.putNumber("Area", scores.Area);
			boolean isTarget = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;

			server.setImage(binaryFrame);
			//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
		//	SmartDashboard.putBoolean("isTarget", isTarget);
			ParticleReport report = particles.elementAt(0);
		//	SmartDashboard.putNumber("BoundingRectBottom", report.BoundingRectBottom);
		//	SmartDashboard.putNumber("BoundingRectTopp", report.BoundingRectTop);
		//	SmartDashboard.putNumber("BoundingRectLeft", report.BoundingRectLeft);
		//	SmartDashboard.putNumber("BoundingRectRight", report.BoundingRectRight);
			SmartDashboard.putNumber("Distance", computeDistance(report));
			SmartDashboard.putNumber("HorizontalAngle", computeHorizontalAngle(report));
		//	SmartDashboard.putString("isItWorking", "yes");
			distance = computeDistance(report);
			angle = computeHorizontalAngle(report);
			particleVisible = true;
		} else {
		//	SmartDashboard.putString("ImageString", frame.toString());
		//	SmartDashboard.putString("isItWorking", "no");
		//	SmartDashboard.putBoolean("isTarget", false);
			particleVisible = false;
		}
		System.gc();
		shouldRun = false;
    }
    
    public double getAngle()
    {
    	return angle;
    }
    
    public double getDistance()
    {
    	return distance;
    }
    
    public boolean withinTolerance()
    {
    	if(Math.abs(getAngle()) < 2.0)
		{
			return true;
		}
    	return false;
    }
    
    public boolean targetInView()
    {
    	return particleVisible;
    }
    
	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}

	double AreaScore(ParticleReport report)
	{
		double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
		//Tape is 7" edge so 49" bounding rect. With 2" wide tape it covers 24" of the rect.
		return ratioToScore(3*report.Area/boundingArea);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the retro-reflective target. Target is 7"x7" so aspect should be 1
	 */
	double AspectScore(ParticleReport report)
	{
		return ratioToScore((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)/1.6);
	}
    
	@Override
	public void update() {
		// TODO Auto-generated method stub
		findTarget();
	}

	@Override
	public void start() {
    	server = CameraServer.getInstance();
		cam.openCamera();
		cam.setExposureManual(0);
		cam.updateSettings();
		cam.startCapture();
		// TODO Auto-generated method stub
	}
	
	public double computeDistance (ParticleReport report) 
	{
		//NIVision.ImageInfo info = NIVision.imaqGetImageInfo(frame);
		//SmartDashboard.putString("Resolution", Integer.toString(info.xRes) + " x " + Integer.toString(info.yRes));
	//	SmartDashboard.putNumber("Radians Per Pixel", RAD_PER_PIXEL);
	//	SmartDashboard.putNumber("Bounding Bottom Value", (report.BoundingRectBottom));
		return HEIGHT_GC/Math.tan(((RESY/2)-report.BoundingRectBottom)*(RAD_PER_PIXEL)+CAMERA_ANGLE_OFFSET);
	}
	
	public double computeHorizontalAngle(ParticleReport report)
	{
		return (((report.BoundingRectRight+report.BoundingRectLeft)/2)-(((RESX)/2)+CAMERA_PIXEL_OFFSET))*ANGLE_PER_PIXEL;
	}
	
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	}
	
	public class Scores {
		double Area;
		double Aspect;
	}
}

