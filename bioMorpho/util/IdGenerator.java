package bioMorpho.util;

/**
 * @author Brighton Ancelin
 *
 */
public class IdGenerator {
	
	private int curId = -1;
	
	public int getNextId() {
		return ++curId;
	}
	
	public void shiftIdsBy(int amount) {
		this.curId += amount;
	}
}
