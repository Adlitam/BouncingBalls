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
	
	private double gravity, deltaX, deltaY, hypotenuse;

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
		
	}

	public void tick(double deltaT) {
		velocityY += gravity * deltaT;
		velocityB += gravity * deltaT;
		deltaX = Math.abs(x-a);
		deltaY = Math.abs(y-b);
		hypotenuse = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		if (x < radiusXY || x > areaWidth - radiusXY) {
			velocityX *= -1;
		}
		if (y < radiusXY || y > areaHeight - radiusXY) {
			
			velocityY *= -1;
		}
		if (a < radiusAB || a > areaWidth - radiusAB) {
			velocityA *= -1;
		}
		if (b < radiusAB || b > areaHeight - radiusAB ) {
			velocityB *= -1;
		}
		if ((hypotenuse-radiusXY-radiusAB) <= 0){
			
		}

		
		x += velocityX * deltaT;
		y += velocityY * deltaT;
		a += velocityA * deltaT;
		b += velocityB * deltaT;
	}

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - radiusXY, y - radiusXY, 2 * radiusXY, 2 * radiusXY));
		myBalls.add(new Ellipse2D.Double(a - radiusAB , b - radiusAB , 2*radiusAB,2* radiusAB));
		return myBalls;
	}
}