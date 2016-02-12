package bioMorpho.etc;


import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import bioMorpho.manager.InventoryManager;
import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketInventoryManData;
import bioMorpho.network.packet.PacketLFSMAll;
import bioMorpho.network.packet.PacketLifeformIndexToClient;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * @author Brighton Ancelin
 *
 */
public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		// This occurs on server side
//		ModelManager.getServerInstance().initNewPlayerToMap(player.username);
//		PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData(player.username)));
		List playersList = player.worldObj.playerEntities;
		String[] playerNames = new String[playersList.size()];
		for(int i = 0; i < playersList.size(); i++) {
			playerNames[i] = ((EntityPlayer)playersList.get(i)).username;
		}
//		PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketModelManData(playerNames)), (Player)player);
		
		InventoryManager.getServerInstance().initNewPlayer(player.username);
		PacketDispatcher.sendPacketToAllPlayers(PacketTypeHandler.convertToMinecraftPacketForm(new PacketInventoryManData(player.username)));
		PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketInventoryManData(playerNames)), (Player)player);
		
		LifeformIndexManager.getServerInstance().initPlayerIfNecessary(player.username);
		int index = LifeformIndexManager.getServerInstance().getLifeformIndexOfPlayer(player.username);
		PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketLifeformIndexToClient(index)), (Player)player);
		
		PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketLFSMAll()), (Player)player);
	}
	
	@Override
	public void onPlayerLogout(EntityPlayer player) {
		
	}
	
	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		
	}
	
	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		
	}
	
}
