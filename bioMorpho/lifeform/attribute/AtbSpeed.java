package bioMorpho.lifeform.attribute;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public class AtbSpeed extends Attribute {
	
	AttributeModifier mod = new AttributeModifier("bioMorpho_speed", 7, 1);
	
	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public void executeStart(EntityPlayer player, World world) {
//		((ModifiableAttributeInstance)player.func_110148_a(SharedMonsterAttributes.field_111263_d)).func_111130_a(1).add(this.mod);
		player.jumpMovementFactor *= 7;
	}
	
	@Override
	public void executeEnd(EntityPlayer player, World world) {
//		((ModifiableAttributeInstance)player.func_110148_a(SharedMonsterAttributes.field_111263_d)).func_111130_a(1).remove(this.mod);
	}
	
}
