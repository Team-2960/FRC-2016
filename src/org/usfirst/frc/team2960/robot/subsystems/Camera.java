package org.usfirst.frc.team2960.robot.subsystems;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team2960.robot.PeriodicUpdate;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class Camera extends Subsystem implements PeriodicUpdate {
    
	public Camera()
	{
		cam.openCamera();
		cam.setExposureManual(1);
		cam.startCapture();
	}
	NIVision.Range HUE_RANGE = new NIVision.Range(88, 146);
	NIVision.Range SAT_RANGE = new NIVision.Range(252, 255);
	NIVision.Range LUM_RANGE = new NIVision.Range(23, 255);
	final double SCORE_MIN = 75.0;
	//double VIEW_ANGLE = 58.08777;
	final double ANGLE_PER_PIXEL = 0.045381;
	final double HEIGHT_GC = 1; //height of goal - robot camera - change later
	
	USBCamera cam = new USBCamera();
	Image frame = NIVision.imaqCreateImage(ImageType.IMAGE_HSL, 0);
	Image binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	Scores scores = new Scores();
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void takeSnapshot()
    {
    	cam.getImage(frame);
    }
    
    public void findTarget()
    {
    	takeSnapshot();
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
			SmartDashboard.putNumber("Aspect", scores.Aspect);
			scores.Area = AreaScore(particles.elementAt(0));
			SmartDashboard.putNumber("Area", scores.Area);
			boolean isTarget = scores.Aspect > SCORE_MIN && scores.Area > SCORE_MIN;

			//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
			SmartDashboard.putBoolean("isTarget", isTarget);
			SmartDashboard.putNumber("Distance", computeDistance(particles.elementAt(0)));
			SmartDashboard.putNumber("HorizontalAngle", computeHorizontalAngle(particles.elementAt(0)));
		} else {
			SmartDashboard.putBoolean("IsTote", false);
		}
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
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	
	public double computeDistance (ParticleReport report) 
	{
		return HEIGHT_GC/Math.tan((400-report.BoundingRectBottom)*ANGLE_PER_PIXEL);
	}
	
	public double computeHorizontalAngle(ParticleReport report)
	{
		return (((report.BoundingRectRight+report.BoundingRectLeft)/2)-640)+ANGLE_PER_PIXEL;
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

