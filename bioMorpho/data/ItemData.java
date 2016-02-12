package bioMorpho.data;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemData {
	
	public static interface IItemData {
		public void setId(int id);
		public String getCfgKey();
		public int getDefaultId();
		public String getCfgComment();
		
		public int getId();
		public String getUnlocalizedName();
		public String getRegisterName();
	}
	
	public static enum EnumItemData implements IItemData {
		
		//PROTOTYPE_NUCLEUS(1024, "prototypeNucleus"),
		NUCLEOBASE_GOO(3841, "nucleobaseGoo"),
		GEM_OF_GENESIS(3842, "gemOfGenesis"),
		BLUE_METEOR_SHARD(3843, "blueMeteorShard"),
		LUNAR_ORB(3844, "lunarOrb"),
		ENERGETIC_GLASS_ORB(3845, "energeticGlassOrb"),
		;
		
		private int id;
		private String cfgKey;
		private final int defaultId;
		private String cfgComment;
		private String unlocalizedName;
		private String registerName;
		
		private EnumItemData(int defaultId, String allNames) {
			this(defaultId, allNames, null, allNames, allNames);
		}
		private EnumItemData(int defaultId, String cfgKey, String codeNames) {
			this(defaultId, cfgKey, null, codeNames, codeNames);
		}
		private EnumItemData(int defaultId, String cfgKey, String cfgComment, String codeNames) {
			this(defaultId, cfgKey, cfgComment, codeNames, codeNames);
		}
		private EnumItemData(int defaultId, String cfgKey, String cfgComment, String unlocalizedName, String registerName) {
			this.id = defaultId;
			this.cfgKey = cfgKey;
			this.defaultId = defaultId;
			this.cfgComment = cfgComment;
			this.unlocalizedName = unlocalizedName;
			this.registerName = registerName;
		}
		
		public void setId(int id) {this.id = id;}
		public String getCfgKey() {return this.cfgKey;}
		public int getDefaultId() {return this.defaultId;}
		public String getCfgComment() {return this.cfgComment;}
		
		public int getId() {return this.id;}
		public String getUnlocalizedName() {return this.unlocalizedName;}
		public String getRegisterName() {return this.registerName;}
		
	}
	
}
