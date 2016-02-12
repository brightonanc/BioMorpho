package bioMorpho.lifeform.model.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brighton Ancelin
 *
 */
public class InheritedAnimData {
	
	private Map<String, Integer> ints = new HashMap<String, Integer>();
	private Map<String, Float> floats = new HashMap<String, Float>();
	private Map<String, Double> doubles = new HashMap<String, Double>();
	
	public void setInt(String key, int value) { this.ints.put(key, value); }
	public void setFloat(String key, float value) { this.floats.put(key, value); }
	public void setDouble(String key, double value) { this.doubles.put(key, value); }
	
	public Integer getInt(String key) { return this.ints.get(key); }
	public Float getFloat(String key) { return this.floats.get(key); }
	public Double getDouble(String key) { return this.doubles.get(key); }
	
}
