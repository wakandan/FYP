package generatorbase;

public class NormalDistribution extends Distribution{
	
	/*Return probablity density value at the point of x*/
	public float f(float x) {
		return (float) (Math.exp(-Math.pow(x-mean, 2)/
				(float)(2*variance))/
				Math.sqrt(2*Math.PI*variance));
	}
}
