import java.awt.geom.Ellipse2D;

import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	
	//first ball
	private double x1, y1, velocityX1, velocityY1, radius1, mass1;
	//second ball
	private double x2, y2, velocityX2, velocityY2, radius2, mass2;
	
	private double gravity, deltaX, deltaY, hypotenuse, alpha,
		 collisionX, collisionY;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x1 = 1;
		y1 = 2;
		velocityX1 = 14;
		velocityY1 = 16;
		velocityX2 = 15;
		velocityY2 = 12;
		radius1 = 1;
		radius2 = 1; 
		x2 = 3;
		y2 = 4;
		gravity = -9.82;
		mass1 = 20;
		mass2 = 3;
		
	}

	public void tick(double deltaT) {
		double nextX = (x1 + velocityX1 * deltaT);
		double nextY = (y1 + velocityY1 * deltaT);
		double nextA = (x2 + velocityX2 * deltaT);
		double nextB = (y2 + velocityY2 * deltaT);
		
		deltaX = (x1-x2);
		deltaY = (y1-y2);
		hypotenuse = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		
		if(nextX <= radius1 || nextX >= areaWidth - radius1) {
			velocityX1 *= -1;
		}
		if(nextY <= radius1 || nextY >= areaHeight - radius1) {
			
			velocityY1 *= -1;
		}
		
		if(nextA <= radius2 || nextA >= areaWidth - radius2) {
			velocityX2 *= -1;
		}
		if(nextB <= radius2 || nextB >= areaHeight - radius2) {
			velocityY2 *= -1;
		}
		
		if(nextY >= radius1){
			velocityY1 += gravity*deltaT;
		}
		if(nextB >= radius2){
			velocityY2 += gravity*deltaT;
		}
		
		if ((hypotenuse-radius1-radius2) <= 0){
			
			collisionY = y1-y2;
			collisionX = x1-x2;
			
			alpha = Math.atan2(collisionY, collisionX);
			double Velocity1R = rectToPolarR(velocityX1, velocityY1);//velocity before (u1)
			double Velocity2R = rectToPolarR(velocityX2, velocityY2);//u2
			
			double Velocity1Angle = rectToPolarAngle(velocityX1, velocityY1);
			double Velocity2Angle = rectToPolarAngle(velocityX2, velocityY2);
			
			double newAngle1 = Velocity1Angle - alpha;
			double newAngle2 = Velocity2Angle - alpha;
			
			double velocity1XPolar = polarToRectX(Velocity1R,newAngle1);
			double velocity1YPolar = polarToRectY(Velocity1R,newAngle1);
			double velocity2XPolar = polarToRectX(Velocity2R,newAngle2);
			double velocity2YPolar = polarToRectY(Velocity2R,newAngle2);
		
			
			double i = mass1*velocity1XPolar+mass2*velocity2XPolar;
			double r = velocity2XPolar-velocity1XPolar;
		
			
			double velocityXTemp = -(-i+(mass2*-r))/(mass1+mass2);
			double velocityATemp = -(-i-(mass1*-r))/(mass1+mass2);
			
			double Velocity1AngleP = rectToPolarAngle(velocityXTemp, velocity1YPolar);
			double Velocity2AngleP = rectToPolarAngle(velocityATemp, velocity2YPolar);
			
			
			double newOld1Angle = Velocity1AngleP + alpha;
			double newOld2Angle = Velocity2AngleP + alpha;
			
			
			double Velocity1P = rectToPolarR(velocityXTemp, velocity1YPolar);
			double Velocity2P = rectToPolarR(velocityATemp, velocity2YPolar);
			
			
			
			
			velocityX1 = polarToRectX(Velocity1P,newOld1Angle);
			velocityY1 = polarToRectY(Velocity1P,newOld1Angle);
			velocityX2 = polarToRectX(Velocity2P,newOld2Angle);
			velocityY2 = polarToRectY(Velocity2P,newOld2Angle);
			
			/*
			System.out.println("collision!!!");
			velocityX1 = velocityXTemp;
			velocityY1 = velocity1YPolar;
			velocityX2 = velocityATemp;
			velocityY2 = velocity2YPolar;
		*/
		}
		
		x1 += velocityX1 * deltaT;
		y1 += velocityY1 * deltaT;
		x2 += velocityX2 * deltaT;
		y2 += velocityY2 * deltaT;
	}
	
	public double polarToRectX(double r, double alpha){
		return r*Math.cos(alpha);
	}
	public double polarToRectY(double r, double alpha){
		return r*Math.sin(alpha);
	}
	
	public double rectToPolarR(double x1, double y1){
		return Math.sqrt(x1*x1+y1*y1);
	}
	public double rectToPolarAngle(double x1, double y1){
		return Math.atan2(y1,x1);
	}

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x1 - radius1, y1 - radius1, 2*radius1, 2*radius1));
		myBalls.add(new Ellipse2D.Double(x2 - radius2 , y2 - radius2 , 2*radius2,2* radius2));
		return myBalls;
	}
}
