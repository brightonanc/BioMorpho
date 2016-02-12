package bioMorpho.util;

import net.minecraft.util.MathHelper;

/**
 * @author Brighton Ancelin
 *
 */
public class MathUtils {
	
	public static double square(double input) { return input * input; }
	
	public static double cube(double input) { return input * input * input; }
	
	/** Uses MathHelper to lookup in a table for fast computation */
	public static double sin(double input) {return MathHelper.sin((float)(input*((2*Math.PI)/360F)));}
	/** Uses MathHelper to lookup in a table for fast computation */
	public static double cos(double input) {return MathHelper.cos((float)(input*((2*Math.PI)/360F)));}
	
	public static double pow(double base, int power) {
		double ret = 1D;
		for(int i = 0; i < power; i++) ret *= base;
		return ret;
	}
	
	public static int pow(int base, int power) {
		int ret = 1;
		for(int i = 0; i < power; i++) ret *= base;
		return ret;
	}
	
	public static double max(double a, double b) {return a > b ? a : b;}
	public static float max(float a, float b) {return a > b ? a : b;}
	public static int max(int a, int b) {return a > b ? a : b;}
	
	public static double min(double a, double b) {return a < b ? a : b;}
	public static float min(float a, float b) {return a < b ? a : b;}
	public static int min(int a, int b) {return a < b ? a : b;}
	
}
