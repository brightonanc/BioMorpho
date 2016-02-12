package bioMorpho.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import bioMorpho.data.GuiData;
import bioMorpho.gui.etc.GuiBioMorphoPlayer;
import bioMorpho.gui.lifeformSelection.GuiLifeformSelection;
import bioMorpho.inventory.ContainerEmpty;
import bioMorpho.inventory.ContainerPlayerBioMorpho;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id) {
		case GuiData.BIO_MORPHO_PLAYER_ID :
			return new ContainerPlayerBioMorpho(player);
		case GuiData.GEM_OF_GENESIS_HOLDER_ID:
			return new ContainerEmpty();
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id) {
		case GuiData.BIO_MORPHO_PLAYER_ID :
			return new GuiBioMorphoPlayer(new ContainerPlayerBioMorpho(player));
		case GuiData.GEM_OF_GENESIS_HOLDER_ID:
			return new GuiLifeformSelection(new ContainerEmpty(), (TEGemOfGenesisHolder)world.getBlockTileEntity(x, y, z));
		default:
			return null;
		}
	}

}
