package bioMorpho.data;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockData {
	
	public static interface IBlockData {
		public void setId(int id);
		public String getCfgKey();
		public int getDefaultId();
		public String getCfgComment();
		
		public int getId();
		public String getUnlocalizedName();
		public String getRegisterName();
	}
	
	public static enum EnumBlockData implements IBlockData {
		
		NUCLEOBASE_ORE(512, "nucleobaseOre"),
		NUCLEOBASE_LOG(513, "nucleobaseLog"),
		NUCLEOBASE_CRYSTAL(514, "nucleobaseCrystal"),
		NUCLEOBASE_BLOCK(515, "nucleobaseBlock"),
		NUCLEOBASE_SAPLING(516, "nucleobaseSapling"),
		NUCLEOBASE_LEAVES(517, "nucleobaseLeaves"),
		ENERGETIC_SAND(518, "energeticSand"),
		GEM_OF_GENESIS_HOLDER(519, "Lapis_Lectern", "gemOfGenesisHolder"),
		LUNAR_FLOWER(520, "lunarFlower"),
		;
		
		private int id;
		private String cfgKey;
		private final int defaultId;
		private String cfgComment;
		private String unlocalizedName;
		private String registerName;
		
		private EnumBlockData(int defaultId, String allNames) {
			this(defaultId, allNames, null, allNames, allNames);
		}
		private EnumBlockData(int defaultId, String cfgKey, String codeNames) {
			this(defaultId, cfgKey, null, codeNames, codeNames);
		}
		private EnumBlockData(int defaultId, String cfgKey, String cfgComment, String codeNames) {
			this(defaultId, cfgKey, cfgComment, codeNames, codeNames);
		}
		private EnumBlockData(int defaultId, String cfgKey, String cfgComment, String unlocalizedName, String registerName) {
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
/*	public static enum BasicBlockData implements IBlockData {
		
		NUCLEOBASE_ORE(512, "nucleobaseOre"),
		NUCLEOBASE_LOG(513, "nucleobaseLog"),
		NUCLEOBASE_BLOCK(515, "nucleobaseBlock"),
		NUCLEOBASE_SAPLING(516, "nucleobaseSapling"),
		NUCLEOBASE_LEAVES(517, "nucleobaseLeaves"),
		ENERGETIC_SAND(518, "energeticSand"),
		;
		
		private int id;
		private String unlocalizedName;
		private String registerName;
		
		private BasicBlockData(int id, String allNames) {
			this(id, allNames, allNames);
		}
		
		private BasicBlockData(int id, String unlocalizedName, String registerName) {
			this.id = id;
			this.unlocalizedName = unlocalizedName;
			this.registerName = registerName;
		}
		
		public void setId(int id) {this.id = id;}
		
		public int getId() {return this.id;}
		public String getUnlocalizedName() {return this.unlocalizedName;}
		public String getRegisterName() {return this.registerName;}
		
	}
	public static enum TEBlockData implements IBasicBlock {
		
		NUCLEOBASE_CRYSTAL(514, "nucleobaseCrystal"),
		GEM_OF_GENESIS_HOLDER(519, "gemOfGenesisHolder"),
		LUNAR_FLOWER(520, "lunarFlower"),
		;
		
		private int id;
		private String unlocalizedName;
		private String registerName;
		private String teRegisterName;
		
		private TEBlockData(int id, String allNames) {
			this(id, allNames, allNames, allNames);
		}
		
		private TEBlockData(int id, String unlocalizedName, String registerName, String teRegisterName) {
			this.id = id;
			this.unlocalizedName = unlocalizedName;
			this.registerName = registerName;
			this.teRegisterName = teRegisterName;
		}
		
		public void setId(int id) {this.id = id;}
		
		public int getId() {return this.id;}
		public String getUnlocalizedName() {return this.unlocalizedName;}
		public String getRegisterName() {return this.registerName;}
		public String getTERegisterName() {return this.teRegisterName;}
		
	}*/
	
}
