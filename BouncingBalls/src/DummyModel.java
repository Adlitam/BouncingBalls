import java.awt.geom.Ellipse2D;
import java.lang.management.GarbageCollectorMXBean;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	
	//Variabler för första bollen
	private double x, y, velocityBeforeX, velocityBeforeY, velocityAfterX, velocityAfterY, radiusXY,massXY,
	//Variabler för andra bollen
	a,b,velocityBeforeA,velocityBeforeB,velocityAfterA,velocityAfterB,radiusAB,massAB,
	//Gravity
	gravity;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		x = 1;
		y = 1;
		velocityBeforeX = 5;
		velocityBeforeY = 6;
		velocityBeforeA = 4.3;
		velocityBeforeB = 6;
		radiusXY = 1;
		radiusAB = 1; 
		a = 3;
		b = 4;
		gravity = -15;
		
		
	}

	public void tick(double deltaT) {
		velocityBeforeY += gravity * deltaT;
		velocityBeforeB += gravity * deltaT;
		if (x < radiusXY || x > areaWidth - radiusXY) {
			velocityBeforeX *= -1;
		}
		if (y < radiusXY || y > areaHeight - radiusXY) {
			
			velocityBeforeY *= -1;
		}
		if (a < radiusAB || a > areaWidth - radiusAB) {
			velocityBeforeA *= -1;
		}
		if (b < radiusAB || b > areaHeight - radiusAB ) {
			velocityBeforeB *= -1;
		}
		/*
		if(x == areaHeight - radiusXY){
			velocityBeforeY *= -1 * 0.9; 
			
		}
		*/
		
		
		x += velocityBeforeX * deltaT;
		y += velocityBeforeY * deltaT;
		a += velocityBeforeA * deltaT;
		b += velocityBeforeB * deltaT;
	}

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - radiusXY, y - radiusXY, 2 * radiusXY, 2 * radiusXY));
		myBalls.add(new Ellipse2D.Double(a - radiusAB , b - radiusAB , 2*radiusAB,2* radiusAB));
		return myBalls;
	}
}