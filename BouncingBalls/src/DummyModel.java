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
		alpha2, alpha, tempX, tempY, tempA, tempB;

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
		alpha1 = 0;
		alpha2 = 0;
		alpha = 0;
		massXY = 2;
		massAB = 4;
		
	}

	public void tick(double deltaT) {
		double nextX = (x + velocityX * deltaT);
		double nextY = (y + velocityY * deltaT);
		double nextA = (a + velocityA * deltaT);
		double nextB = (b + velocityB * deltaT);
		
		deltaX = Math.abs(x-a);
		deltaY = Math.abs(y-b);
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
		
		double h1 = Math.sqrt(velocityX*velocityX+velocityY*velocityY);
		alpha1 = Math.asin(velocityY/h1);
		double h2 = Math.sqrt(velocityA*velocityA+velocityB*velocityB);
		alpha2 = Math.asin(velocityB/h2);
		double vector1 = -(-(massXY*h1+massAB*h2)+(massAB*-(h2-h1)))/(massXY+massAB);
		double vector2 = -(-(massXY*h1+massAB*h2)-(massXY*-(h2-h1)))/(massXY+massAB);
		if ((hypotenuse-radiusXY-radiusAB) <= 0){
			System.out.println("collision!!!");
			x = polarToRectX(vector1,alpha1);
			y = polarToRectY(vector1,alpha1);
			a = polarToRectX(vector2,alpha2);
			b = polarToRectY(vector2,alpha2);
			
			/*alpha1 = Math.atan2(y,x);
			alpha2 = Math.atan2(b,a);
			alpha = alpha1 - alpha2;
			tempX = (Math.sqrt(x*x+y*y))*Math.cos(alpha);
			tempY = (Math.sqrt(x*x+y*y))*Math.sin(alpha);
			tempA = (Math.sqrt(a*a+b*b))*Math.cos(alpha);
			tempB = (Math.sqrt(a*a+b*b))*Math.sin(alpha);*/
			
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

	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		myBalls.add(new Ellipse2D.Double(x - radiusXY, y - radiusXY, 2*radiusXY, 2*radiusXY));
		myBalls.add(new Ellipse2D.Double(a - radiusAB , b - radiusAB , 2*radiusAB,2* radiusAB));
		return myBalls;
	}
}