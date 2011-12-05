package generatorbase;

public abstract class Distribution {
	float mean;
	float variance;
	
	public abstract float f(float x);
}
