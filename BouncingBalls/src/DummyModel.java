import java.awt.geom.Ellipse2D;
import java.lang.management.GarbageCollectorMXBean;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	
	//first ball
	private double x, y, velocityX, velocityY, radiusXY, massXY;
	//second ball
	private double a, b, velocityA, velocityB, radiusAB, massAB;
	
	private double gravity, deltaX, deltaY, hypotenuse, alpha,
		tempX, tempY, tempA, tempB, collisionX, collisionY, r, r2;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x = 1;
		y = 2;
		velocityX = 5;
		velocityY = 6;
		velocityA = 4.3;
		velocityB = 6;
		radiusXY = 1;
		radiusAB = 1; 
		a = 3;
		b = 4;
		gravity = -9.82;
		hypotenuse = 0;
		deltaX = 0;
		deltaY = 0;
		alpha = 0;
		massXY = 2;
		massAB = 4;
		
	}

	public void tick(double deltaT) {
		double nextX = (x + velocityX * deltaT);
		double nextY = (y + velocityY * deltaT);
		double nextA = (a + velocityA * deltaT);
		double nextB = (b + velocityB * deltaT);
		
		deltaX = (x-a);
		deltaY = (y-b);
		hypotenuse = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		
		if(nextX < radiusXY || nextX > areaWidth - radiusXY) {
			velocityX *= -1;
		}
		if(nextY < radiusXY || nextY > areaHeight - radiusXY) {
			
			velocityY *= -1;
		}
		
		if(nextA < radiusAB || nextA > areaWidth - radiusAB) {
			velocityA *= -1;
		}
		if(nextB < radiusAB || nextB > areaHeight - radiusAB) {
			velocityB *= -1;
		}
		
		if(nextY > radiusXY){
			velocityY += gravity*deltaT;
		}
		if(nextB > radiusAB){
			velocityB += gravity*deltaT;
		}
		//double h1 = Math.sqrt(velocityX*velocityX+velocityY*velocityY);
		//double h2 = Math.sqrt(velocityA*velocityA+velocityB*velocityB);
		if ((hypotenuse-radiusXY-radiusAB) <= 0){
			collisionY = y-b;
			collisionX = x-a;
			alpha = Math.atan2(collisionY, collisionX);
			double Velocity1R = rectToPolarR(velocityX, velocityY);
			double Velocity2R = rectToPolarR(velocityA, velocityB);
			double Velocity1Angle = rectToPolarAngle(velocityX, velocityY);
			double Velocity2Angle = rectToPolarAngle(velocityA, velocityB);
			double newAngle1 = Velocity1Angle - alpha;
			double newAngle2 = Velocity2Angle - alpha;
			velocityX = polarToRectX(Velocity1R,newAngle1);
			velocityY = polarToRectY(Velocity1R,newAngle1);
			velocityA = polarToRectX(Velocity2R,newAngle2);
			velocityB = polarToRectY(Velocity2R,newAngle2);
			
			double velocityXTemp = -(-(massXY*velocityX+massAB*velocityA)+(massAB*-(velocityA-velocityX)))/(massXY+massAB);
			double velocityATemp = -(-(massXY*velocityX+massAB*velocityA)-(massXY*-(velocityA-velocityX)))/(massXY+massAB);
			
			
			System.out.println("collision!!!");
			velocityX = ;
			velocityY = ;
			velocityA = ;
			velocityB = ;
		}
		
		x += velocityX * deltaT;
		y += velocityY * deltaT;
		a += velocityA * deltaT;
		b += velocityB * deltaT;
	}
	
	public double polarToRectX(double r, double alpha){
		return r*Math.cos(alpha);
	}
	public double polarToRectY(double r, double alpha){
		return r*Math.sin(alpha);
	}
	
	public double rectToPolarR(double x, double y){
		return Math.sqrt(x*x+y*y);
	}
	public double rectToPolarAngle(double x, double y){
		return Math.atan2(y,x);
	}

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - radiusXY, y - radiusXY, 2*radiusXY, 2*radiusXY));
		myBalls.add(new Ellipse2D.Double(a - radiusAB , b - radiusAB , 2*radiusAB,2* radiusAB));
		return myBalls;
	}
}
