package bioMorpho.etc;


/**
 * @author Brighton Ancelin
 *
 */
public enum Nucleobases {
	
	ADENINE(),
	GUANINE(),
	THYMINE(),
	CYTOSINE(),
	;
	
	public int[] getDefaultRGBIntArray() {
		switch(this) {
		case ADENINE: return new int[] {0xe4, 0x02, 0x0d};
		case GUANINE: return new int[] {0x5c, 0x02, 0xe4};
		case THYMINE: return new int[] {0xe4, 0xe2, 0x02};
		case CYTOSINE: return new int[] {0x02, 0xe4, 0x5c};
		default: return null;
		}
	}
	
	public float[] getDefaultRGBFloatArray() {
		switch(this) {
		case ADENINE: return new float[] {0xe4/255F, 0x02/255F, 0x0d/255F};
		case GUANINE: return new float[] {0x5c/255F, 0x02/255F, 0xe4/255F};
		case THYMINE: return new float[] {0xe4/255F, 0xe2/255F, 0x02/255F};
		case CYTOSINE: return new float[] {0x02/255F, 0xe4/255F, 0x5c/255F};
		default: return null;
		}
	}
	
}
