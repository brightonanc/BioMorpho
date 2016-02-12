package bioMorpho.helper;

import static bioMorpho.etc.Nucleobases.ADENINE;
import static bioMorpho.etc.Nucleobases.CYTOSINE;
import static bioMorpho.etc.Nucleobases.GUANINE;
import static bioMorpho.etc.Nucleobases.THYMINE;

import java.util.ArrayList;

import bioMorpho.lifeform.Lifeform;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.lifeform.model.SegmentBox;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformExpenseHandler {
	
	public static int[] getCostForChanges(ArrayList<Lifeform> initLifeforms, ArrayList<Lifeform> newLifeforms) {
		int[] initLifeformsCost = new int[] {0, 0, 0, 0};
		for(Lifeform curLifeform : initLifeforms) {
			initLifeformsCost = addIntArrays(initLifeformsCost, getCostForLifeform(curLifeform));
		}
		int[] newLifeformsCost = new int[] {0, 0, 0, 0};
		for(Lifeform curLifeform : newLifeforms) {
			newLifeformsCost = addIntArrays(newLifeformsCost, getCostForLifeform(curLifeform));
		}
		return subtractIntArrays(newLifeformsCost, initLifeformsCost);
	}
	
	protected static int[] getCostForLifeform(Lifeform lifeform) {
		int[] lifeformCost = new int[] {0, 0, 0, 0};
		lifeformCost = addIntArrays(lifeformCost, getCostForModel(lifeform.getModel()));
		return lifeformCost;
	}
	
	protected static int[] getCostForModel(ModelCustom model) {
		int[] modelCost = new int[] {0, 0, 0, 0};
		for(EntityComponent curComp : model.components) {
			modelCost = addIntArrays(modelCost, getCostForComp(curComp));
		}
		return modelCost;
	}
	
	protected static int[] getCostForComp(EntityComponent comp) {
		int[] compCost = new int[] {0, 0, 0, 0};
		for(int i = 0; i < comp.segment.cubeList.size(); i++) {
			compCost = addIntArrays(compCost, getCostForBox(comp.segment.getBoxAtIndex(i)));
		}
		return compCost;
	}
	
	protected static int[] getCostForBox(SegmentBox box) {
		float volume = Math.abs(box.getWidth() * box.getHeight() * box.getDepth());
		return createCost((int)(volume/8));
	}
	
	protected static int[] createCost(int all) {return createCost(all, all, all, all);}
	protected static int[] createCost(int a_t, int g_c) {return createCost(a_t, g_c, a_t, g_c);}
	protected static int[] createCost(int adenine, int guanine, int thymine, int cytosine) {
		int[] cost = new int[4];
		cost[ADENINE.ordinal()] = adenine;
		cost[GUANINE.ordinal()] = guanine;
		cost[THYMINE.ordinal()] = thymine;
		cost[CYTOSINE.ordinal()] = cytosine;
		return cost;
	}
	
	protected static int[] addIntArrays(int[] a, int[] b) {
		if(a.length != b.length) return null;
		int[] sum = new int[a.length];
		for(int i = 0; i < sum.length; i++) {
			sum[i] = a[i] + b[i];
		}
		return sum;
	}
	
	protected static int[] subtractIntArrays(int[] a, int[] b) {
		if(a.length != b.length) return null;
		int[] sum = new int[a.length];
		for(int i = 0; i < sum.length; i++) {
			sum[i] = a[i] - b[i];
		}
		return sum;
	}
	
}
