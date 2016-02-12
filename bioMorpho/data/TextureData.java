package bioMorpho.data;

import net.minecraft.util.ResourceLocation;

/**
 * @author Brighton Ancelin
 *
 */
public class TextureData {
	
	private static final String MOD_LOC = "bio_morpho";
	
	// Texture File Locations
	private static final String GUI_LOC = "textures/gui/";
	private static final String PATTERN_LOC = "textures/patterns/";
	private static final String ITEM_LOC = "textures/items/";
	private static final String BLOCK_LOC = "textures/blocks/";
	private static final String MODEL_LOC = "textures/models/";
	
	public static class GuiTextures {
		public static final ResourceLocation COMP_AND_BOX_SETUP_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "Misc2A.png");
		public static final ResourceLocation COMP_AND_BOX_SETUP_BKGRND = new ResourceLocation(MOD_LOC, GUI_LOC + "CompAndBox_Design1A.png");
		public static final ResourceLocation COMP_ONLY_SETUP_BKGRND = new ResourceLocation(MOD_LOC, GUI_LOC + "CompAndBoxDesign2A_CompOnly.png");
		public static final ResourceLocation MODEL_EDITOR_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "ModelEditor_Misc2A.png");
		public static final ResourceLocation MODEL_NUMBER_INPUT_SETUP_BKGRND = new ResourceLocation(MOD_LOC, GUI_LOC + "ModelNumberInput_Design2A.png");
		public static final ResourceLocation MODEL_RENDERING_PORT_BKGRND = new ResourceLocation(MOD_LOC, GUI_LOC + "ModelRenderingPort_Design1A.png");
		public static final ResourceLocation MODEL_RENDERING_PORT_ROT_POINT = new ResourceLocation(MOD_LOC, GUI_LOC + "WhiteCircle.png");
		public static final ResourceLocation ANIMATION_SELECTION_BKGRND = new ResourceLocation(MOD_LOC, GUI_LOC + "AnimationSelection_Design3A.png");
		public static final ResourceLocation ANIMATION_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "Animation_Misc1A.png");
		public static final ResourceLocation ANIM_FIELD_INPUT_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "AnimFieldInput_Misc1A.png");
		public static final ResourceLocation INVENTORY_BIO_MORPHO_PLAYER_BASE = new ResourceLocation(MOD_LOC, GUI_LOC + "BioMorphoPlayerInventoryWithoutDNADesign_1A.png");
		public static final ResourceLocation INVENTORY_BIO_MORPHO_PLAYER_DESIGNED = new ResourceLocation(MOD_LOC, GUI_LOC + "BioMorphoPlayerInventory_1A.png");
		public static final ResourceLocation LIFEFORM_SELECTION_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "LifeformSelection_Misc4A.png");
		public static final ResourceLocation LIFEFORM_SELECTION_MAIN_PORT_FRAME = new ResourceLocation(MOD_LOC, GUI_LOC + "LifeformSelection_MainViewportFrame2A.png");
		public static final ResourceLocation LIFEFORM_DATA_PANEL = new ResourceLocation(MOD_LOC, GUI_LOC + "LifeformSelectionDataPanel_1A.png");
		public static final ResourceLocation INGAME_SELECTION_MISC = new ResourceLocation(MOD_LOC, GUI_LOC + "IngameSelectionMisc_2A.png");
		public static final ResourceLocation GUI_LIFEFORM_SWAP_BUTTONS = new ResourceLocation(MOD_LOC, GUI_LOC+"GuiSelectionIcons_1A.png");
	}
	public static class BlockTextures {
		public static final String NUCLEOBASE_ORE_A = MOD_LOC + ":" + "Ore_Adenine";
		public static final String NUCLEOBASE_ORE_G = MOD_LOC + ":" + "Ore_Guanine";
		public static final String NUCLEOBASE_ORE_T = MOD_LOC + ":" + "Ore_Thymine";
		public static final String NUCLEOBASE_ORE_C = MOD_LOC + ":" + "Ore_Cytosine";
		public static final String NUCLEOBASE_LOG_A_OUT = MOD_LOC + ":" + "Log_Nucleobase_Adenine_Base";
		public static final String NUCLEOBASE_LOG_A_IN = MOD_LOC + ":" + "Log_Nucleobase_Adenine_Inner";
		public static final String NUCLEOBASE_LOG_G_OUT = MOD_LOC + ":" + "Log_Nucleobase_Guanine_Base";
		public static final String NUCLEOBASE_LOG_G_IN = MOD_LOC + ":" + "Log_Nucleobase_Guanine_Inner";
		public static final String NUCLEOBASE_LOG_T_OUT = MOD_LOC + ":" + "Log_Nucleobase_Thymine_Base";
		public static final String NUCLEOBASE_LOG_T_IN = MOD_LOC + ":" + "Log_Nucleobase_Thymine_Inner";
		public static final String NUCLEOBASE_LOG_C_OUT = MOD_LOC + ":" + "Log_Nucleobase_Cytosine_Base";
		public static final String NUCLEOBASE_LOG_C_IN = MOD_LOC + ":" + "Log_Nucleobase_Cytosine_Inner";
		public static final String NUCLEOBASE_BLOCK_A = MOD_LOC + ":" + "AdenineBlock";
		public static final String NUCLEOBASE_BLOCK_G = MOD_LOC + ":" + "GuanineBlock";
		public static final String NUCLEOBASE_BLOCK_T = MOD_LOC + ":" + "ThymineBlock";
		public static final String NUCLEOBASE_BLOCK_C = MOD_LOC + ":" + "CytosineBlock";
		public static final String NUCLEOBASE_SAPLING_A = MOD_LOC + ":" + "Sapling_Adenine_4";
		public static final String NUCLEOBASE_SAPLING_G = MOD_LOC + ":" + "Sapling_Guanine_4";
		public static final String NUCLEOBASE_SAPLING_T = MOD_LOC + ":" + "Sapling_Thymine_4";
		public static final String NUCLEOBASE_SAPLING_C = MOD_LOC + ":" + "Sapling_Cytosine_4";
		public static final String NUCLEOBASE_LEAVES_A_FAST = MOD_LOC + ":" + "Leaves_Adenine_Fast_2";
		public static final String NUCLEOBASE_LEAVES_G_FAST = MOD_LOC + ":" + "Leaves_Guanine_Fast_2";
		public static final String NUCLEOBASE_LEAVES_T_FAST = MOD_LOC + ":" + "Leaves_Thymine_Fast_2";
		public static final String NUCLEOBASE_LEAVES_C_FAST = MOD_LOC + ":" + "Leaves_Cytosine_Fast_2";
		public static final String NUCLEOBASE_LEAVES_A_FANCY = MOD_LOC + ":" + "Leaves_Adenine_Fancy_2";
		public static final String NUCLEOBASE_LEAVES_G_FANCY = MOD_LOC + ":" + "Leaves_Guanine_Fancy_2";
		public static final String NUCLEOBASE_LEAVES_T_FANCY = MOD_LOC + ":" + "Leaves_Thymine_Fancy_2";
		public static final String NUCLEOBASE_LEAVES_C_FANCY = MOD_LOC + ":" + "Leaves_Cytosine_Fancy_2";
		public static final String ENERGETIC_SAND = MOD_LOC + ":" + "EnergeticSand2A";
		public static final String ALPHA = MOD_LOC + ":" + "Alpha_32x32"; // Used by BlockNucleobaseCrystal to avoid "missing texture" particles
		public static final String FX_GEM_OF_GENESIS_HOLDER = MOD_LOC + ":" + "GemOfGenesisHolder_Dormant_Design1A";
	}
	public static class ItemTextures {
		public static final String NUCLEUS = MOD_LOC + ":Nucleus_Design1A";
		public static final String NUCLEOBASE_GOO_A = MOD_LOC + ":" + "Adenine_Clump";
		public static final String NUCLEOBASE_GOO_G = MOD_LOC + ":" + "Guanine_Clump";
		public static final String NUCLEOBASE_GOO_T = MOD_LOC + ":" + "Thymine_Clump";
		public static final String NUCLEOBASE_GOO_C = MOD_LOC + ":" + "Cytosine_Clump";
		public static final String BLUE_METEOR_SHARD = MOD_LOC + ":" + "BlueMeteorShard_3A";
		public static final String LUNAR_FLOWER = MOD_LOC + ":" + "LunarFlowerItem_1A";
		public static final String LUNAR_ORB = MOD_LOC + ":" + "LunarOrbItem_1A";
	}
	public static class ModelTextures {
		public static final ResourceLocation[] NUCLEOBASE_CRYSTAL_A = {
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_1.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_2.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_3.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_4.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_5.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_6.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_7.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Adenine_Shifting_8.png"),
		};
		public static final ResourceLocation[] NUCLEOBASE_CRYSTAL_G = {
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_1.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_2.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_3.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_4.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_5.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_6.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_7.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Guanine_Shifting_8.png"),
		};
		public static final ResourceLocation[] NUCLEOBASE_CRYSTAL_T = {
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_1.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_2.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_3.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_4.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_5.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_6.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_7.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Thymine_Shifting_8.png"),
		};
		public static final ResourceLocation[] NUCLEOBASE_CRYSTAL_C = {
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_1.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_2.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_3.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_4.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_5.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_6.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_7.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "Cytosine_Shifting_8.png"),
		};
		public static final ResourceLocation METEOR_BLUE = new ResourceLocation(MOD_LOC, MODEL_LOC + "BlueMeteor_Design3A.png");
		public static final ResourceLocation GEM_OF_GENESIS = new ResourceLocation(MOD_LOC, MODEL_LOC + "OctagonGem_Design4A.png");
		public static final ResourceLocation GEM_OF_GENESIS_HOLDER_DORMANT = new ResourceLocation(MOD_LOC, MODEL_LOC + "GemOfGenesisHolder_Dormant_Design1A.png");
		public static final ResourceLocation[] GEM_OF_GENESIS_HOLDER_ACTIVE = {
			new ResourceLocation(MOD_LOC, MODEL_LOC + "GemOfGenesisHolder_Design1_1A.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "GemOfGenesisHolder_Design1_2A.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "GemOfGenesisHolder_Design1_3A.png"),
			new ResourceLocation(MOD_LOC, MODEL_LOC + "GemOfGenesisHolder_Design1_4A.png"),
		};
		public static final ResourceLocation LUNAR_FLOWER = new ResourceLocation(MOD_LOC, MODEL_LOC + "LunarFlower_5A.png");
		public static final ResourceLocation LUNAR_ORB = new ResourceLocation(MOD_LOC, MODEL_LOC + "LunarOrb_1A.png");
		public static final ResourceLocation ENERGETIC_GLASS_ORB = new ResourceLocation(MOD_LOC, MODEL_LOC + "SolarGlass_1A.png");
	}
	
	public static final ResourceLocation PATTERN_DEFAULT = new ResourceLocation(MOD_LOC, PATTERN_LOC + "HvnTexture.png");
	
}
