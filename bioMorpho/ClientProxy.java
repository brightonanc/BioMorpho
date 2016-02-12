package bioMorpho;

import net.minecraftforge.client.MinecraftForgeClient;
import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.entity.EntityBlueMeteor;
import bioMorpho.entity.EntityEnergeticGlassOrb;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.render.entity.EntityBlueMeteorRenderer;
import bioMorpho.render.entity.EntityEnergeticGlassOrbRenderer;
import bioMorpho.render.item.ItemEnergeticGlassOrbRenderer;
import bioMorpho.render.item.ItemGemOfGenesisHolderRenderer;
import bioMorpho.render.item.ItemGemOfGenesisRenderer;
import bioMorpho.render.item.ItemNucleobaseCrystalRenderer;
import bioMorpho.render.tileEntity.TEGemOfGenesisHolderRenderer;
import bioMorpho.render.tileEntity.TELunarFlowerRenderer;
import bioMorpho.render.tileEntity.TENucleobaseCrystalRenderer;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.tileEntity.TELunarFlower;
import bioMorpho.tileEntity.TENucleobaseCrystal;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerNewPlayerModelBinding() {
		//RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderCustom());
	}
	
	@Override
	public void registerItemRenderers() {
		MinecraftForgeClient.registerItemRenderer(BioMorphoBlocks.nucleobaseCrystal.blockID, new ItemNucleobaseCrystalRenderer());
		MinecraftForgeClient.registerItemRenderer(BioMorphoItems.gemOfGenesis.itemID, new ItemGemOfGenesisRenderer());
		MinecraftForgeClient.registerItemRenderer(BioMorphoBlocks.gemOfGenesisHolder.blockID, new ItemGemOfGenesisHolderRenderer());
		MinecraftForgeClient.registerItemRenderer(BioMorphoItems.energeticGlassOrb.itemID, new ItemEnergeticGlassOrbRenderer());
	}
	
	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
		ClientRegistry.bindTileEntitySpecialRenderer(TENucleobaseCrystal.class, new TENucleobaseCrystalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TEGemOfGenesisHolder.class, new TEGemOfGenesisHolderRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TELunarFlower.class, new TELunarFlowerRenderer());
	}
	
	@Override
	public void registerEntities() {
		super.registerEntities();
		RenderingRegistry.registerEntityRenderingHandler(EntityBlueMeteor.class, new EntityBlueMeteorRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnergeticGlassOrb.class, new EntityEnergeticGlassOrbRenderer());
	}
	
}
