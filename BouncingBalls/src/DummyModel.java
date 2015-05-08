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
	
	private double gravity, deltaX, deltaY, hypotenuse, alpha1,
		alpha2, tempAlpha, tempX, tempY, tempA, tempB;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x = 1;
		y = 1;
		velocityX = 5;
		velocityY = 6;
		velocityA = 4.3;
		velocityB = 6;
		radiusXY = 1;
		radiusAB = 1; 
		a = 3;
		b = 4;
		gravity = -15;
		hypotenuse = 0;
		deltaX = 0;
		deltaY = 0;
		alpha1 = 0;
		alpha2 = 0;
		tempAlpha = 0;
		
	}

	public void tick(double deltaT) {
		double nextX = (x + velocityX * deltaT);
		double nextY = (y + velocityY * deltaT);
		double nextA = (a + velocityA * deltaT);
		double nextB = (b + velocityB * deltaT);
		//deltaX = Math.abs(x-a);
		//deltaY = Math.abs(y-b);
		//hypotenuse = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
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
		/*if ((hypotenuse-radiusXY-radiusAB) <= 0){
			System.out.println("colission!!!");
			alpha1 = Math.atan2(y, x);
			alpha2 = Math.atan2(b, a);
			tempAlpha = alpha1 - alpha2;
			tempX = (Math.sqrt(x*x+y*y))*Math.cos(tempAlpha);
			tempY = (Math.sqrt(x*x+y*y))*Math.sin(tempAlpha);
			tempA = (Math.sqrt(a*a+b*b))*Math.cos(tempAlpha);
			tempB = (Math.sqrt(a*a+b*b))*Math.sin(tempAlpha);
		}*/

		addGravity(nextY, velocityY, radiusXY, deltaT);
		addGravity(nextB, velocityB, radiusAB, deltaT);
		
		x += velocityX * deltaT;
		y += velocityY * deltaT;
		a += velocityA * deltaT;
		b += velocityB * deltaT;
	}
	
	
	public void addGravity(double next, double velocity, double radius, double d){
		if((next + velocity*d) > radius){
			velocity += gravity*d;
		}
	}
	

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - radiusXY, y - radiusXY, 2 * radiusXY, 2 * radiusXY));
		myBalls.add(new Ellipse2D.Double(a - radiusAB , b - radiusAB , 2*radiusAB,2* radiusAB));
		return myBalls;
	}
}