package bioMorpho.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import bioMorpho.BioMorpho;
import bioMorpho.data.GuiData;
import bioMorpho.data.KeyBindingData;
import bioMorpho.inventory.ContainerPlayerBioMorpho;
import bioMorpho.network.PacketTypeHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Brighton Ancelin
 *
 */
public class PacketKeyBinding extends PacketBioMorpho {
	
	protected int keyBindingId;
	
	public PacketKeyBinding() {
		super(PacketTypeHandler.KEY_BINDING);
	}
	
	public PacketKeyBinding(int keyBindingId) {
		this();
		this.keyBindingId = keyBindingId;
	}

	@Override
	public void writeData(DataOutputStream dataStream) throws IOException {
		dataStream.writeInt(this.keyBindingId);
	}

	@Override
	public void readData(DataInputStream dataStream) throws IOException {
		this.keyBindingId = dataStream.readInt();
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		if(FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) return;
		EntityPlayer entityPlayer = (EntityPlayer)player;
		switch(KeyBindingData.getEnumValueFromId(this.keyBindingId)) {
		case BIO_MORPHO_PLAYER:
			if(entityPlayer.openContainer instanceof ContainerPlayerBioMorpho) return;
			else entityPlayer.openGui(BioMorpho.instance, GuiData.BIO_MORPHO_PLAYER_ID, entityPlayer.worldObj, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
			break;
		default: break;
		}
	}

}
