package bioMorpho;

import net.minecraftforge.common.MinecraftForge;

import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.data.ModData;
import bioMorpho.data.NetworkData;
import bioMorpho.etc.PlayerTracker;
import bioMorpho.handler.BioMorphoRecipes;
import bioMorpho.handler.ConfigHandler;
import bioMorpho.handler.EventContainer;
import bioMorpho.handler.IngameOverlayHandler;
import bioMorpho.handler.IngameSelectionHandler;
import bioMorpho.handler.KeyBindingHandler;
import bioMorpho.handler.LanguageHandler;
import bioMorpho.handler.LifeformAttributeHandler;
import bioMorpho.handler.MeteorSpawningHandler;
import bioMorpho.handler.ModelUpdateHandler;
import bioMorpho.handler.PlayerRenderingHandler;
import bioMorpho.handler.RenderInfoGatherer;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.network.GuiHandler;
import bioMorpho.network.PacketHandler;
import bioMorpho.world.gen.BioMorphoWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Main mod file. From here, everything is integrated into the game.
 * 
 * @author Brighton Ancelin
 * 
 */
@Mod(modid = ModData.MOD_ID, name = ModData.MOD_NAME, version = ModData.MOD_VERSION)
@NetworkMod(clientSideRequired = ModData.CLIENT_REQUIRED, serverSideRequired = ModData.SERVER_REQUIRED, channels = {NetworkData.CHANNEL}, packetHandler = PacketHandler.class)
public class BioMorpho {
	
	@Instance(ModData.MOD_ID)
	public static BioMorpho instance;
	
	@SidedProxy(clientSide = ModData.CLIENT_PROXY, serverSide = ModData.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
		BioMorphoBlocks.init();
		BioMorphoItems.init();
		
		LanguageHandler.setNames();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		BioMorphoRecipes.init();
		
		proxy.registerTileEntities();
		proxy.registerItemRenderers();
		proxy.registerEntities();
		
		GameRegistry.registerWorldGenerator(new BioMorphoWorldGenerator());
		TickRegistry.registerTickHandler(new MeteorSpawningHandler(), Side.SERVER);
		TickRegistry.registerTickHandler(new KeyBindingHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new ModelUpdateHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new LifeformAttributeHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new RenderInfoGatherer(), Side.CLIENT);
		TickRegistry.registerTickHandler(new IngameSelectionHandler(), Side.CLIENT);
		
		// Class registry stuff
		MinecraftForge.EVENT_BUS.register(new EventContainer());
		MinecraftForge.EVENT_BUS.register(new PlayerRenderingHandler());
		MinecraftForge.EVENT_BUS.register(new IngameOverlayHandler());
		GameRegistry.registerPlayerTracker(new PlayerTracker());
		
		// Proxy Stuff
//		proxy.registerNewPlayerModelBinding();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
