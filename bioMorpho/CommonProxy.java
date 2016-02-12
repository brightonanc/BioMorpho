package bioMorpho;

import bioMorpho.data.BlockData.EnumBlockData;
import bioMorpho.data.EntityData.EnumEntityData;
import bioMorpho.entity.EntityBlueMeteor;
import bioMorpho.entity.EntityEnergeticGlassOrb;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.tileEntity.TELunarFlower;
import bioMorpho.tileEntity.TENucleobaseCrystal;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class CommonProxy {
	
	public void registerNewPlayerModelBinding() {}
	
	public void registerItemRenderers() {}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TENucleobaseCrystal.class, EnumBlockData.NUCLEOBASE_CRYSTAL.getRegisterName());
		GameRegistry.registerTileEntity(TEGemOfGenesisHolder.class, EnumBlockData.GEM_OF_GENESIS_HOLDER.getRegisterName());
		GameRegistry.registerTileEntity(TELunarFlower.class, EnumBlockData.LUNAR_FLOWER.getRegisterName());
	}
	
	public void registerEntities() {
//		BasicEntityData curBasicEntity;
//		curBasicEntity = BasicEntityData.METEOR_BLUE;
//		int id = EntityRegistry.findGlobalUniqueEntityId();
//		EntityRegistry.registerGlobalEntityID(EntityBlueMeteor.class, curBasicEntity.getName(), id);
		loadEntity(EnumEntityData.METEOR_BLUE);
		loadEntity(EnumEntityData.ENERGETIC_GLASS_ORB);
	}
	
	public static void loadEntity(EnumEntityData data) {
		EntityRegistry.registerModEntity(data.getEntityClass(), data.getName(), data.getId(), BioMorpho.instance, 
				data.getTrackingRange(), data.getUpdateFrequency(), data.getSendsVelocityUpdates());
	}
		
}
