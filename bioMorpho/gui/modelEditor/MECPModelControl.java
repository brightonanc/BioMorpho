package bioMorpho.gui.modelEditor;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RandomUtils;
import bioMorpho.util.RenderUtils;


/**
 * @author Brighton Ancelin
 *
 */
public class MECPModelControl {
	
	private IdGenerator ids;
	private CompAndBoxSetup cAndB;
	private int unscaledLeft;
	private int unscaledTop;
	
	private GuiMyButton newComp;
	private GuiMyButton remComp;
	private GuiMyButton newBox;
	private GuiMyButton remBox;
	private GuiMyButton newSubComp;
	private GuiMyButton copyComp;
	private GuiMyButton reflectComp;
	
	public MECPModelControl(CompAndBoxSetup cAndB, int unscaledLeft, int unscaledTop) {
		this.ids = new IdGenerator();
		this.cAndB = cAndB;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		
		this.newComp = new GuiMyButton(unscaledLeft+128, unscaledTop+192, 18, 18, ids.getNextId());
		this.remComp = new GuiMyButton(unscaledLeft+128, unscaledTop+210, 18, 18, ids.getNextId());
		this.newBox = new GuiMyButton(unscaledLeft+128, unscaledTop+228, 18, 18, ids.getNextId());
		this.remBox = new GuiMyButton(unscaledLeft+128, unscaledTop+246, 18, 18, ids.getNextId());
		this.newSubComp = new GuiMyButton(unscaledLeft+128, unscaledTop+264, 18, 18, ids.getNextId());
		this.copyComp = new GuiMyButton(unscaledLeft+128, unscaledTop+282, 18, 18, ids.getNextId());
		this.reflectComp = new GuiMyButton(unscaledLeft+128, unscaledTop+300, 18, 18, ids.getNextId());
	}
	
	public boolean mousePressed(int x, int y, int button) {
		if(this.newComp.mousePressed(x, y, button)) {
			((ArrayList<EntityComponent>)this.cAndB.getCurrentCompTab().members).add(new EntityComponent(this.cAndB.getModel()));
			this.cAndB.getCurrentCompTab().currentObjIndex = this.cAndB.getCurrentCompTab().members.size() - 1;
			this.cAndB.getCurrentCompTab().setPageNum(this.cAndB.getCurrentCompTab().pageMax());
			return true;
		} else if(this.remComp.mousePressed(x, y, button)) {
			try {
				((ArrayList<EntityComponent>)this.cAndB.getCurrentCompTab().members).remove(this.cAndB.getCurrentCompTab().currentObjIndex);
				if(this.cAndB.getCurrentCompTab().members.size() == 0 && !this.cAndB.isCurrentCompTabAtZero()) {
					this.cAndB.compTabLeft();
					this.cAndB.getCurrentCompTab().getCurrent().segment.setSubCompsToNull();
					this.cAndB.getCurrentCompTab().currentObjIndex = -1;
				}
				else {
					this.cAndB.getCurrentCompTab().currentObjIndex = -1;
				}
			} catch(IndexOutOfBoundsException e) {
				// No selected comp to remove, so nothing can be done
			} catch(BioMorphoNoCurrentObjectException e) {
				// This should NEVER OCCUR because if we tab left the new CompTab should have a currentObjIndex 
				// for the current Comp that contained the previously shown subComps
			}
			return true;
		} else if(this.newBox.mousePressed(x, y, button)) {
			try {
				this.cAndB.getCurrentCompTab().getCurrent().segment.addNewDefaultBox();
				this.cAndB.getBoxTab().currentObjIndex = this.cAndB.getBoxTab().members.size() - 1;
				this.cAndB.getBoxTab().setPageNum(this.cAndB.getBoxTab().pageMax());
			} catch(BioMorphoNoCurrentObjectException e) {
				// No comp to put boxes into so there's no reason to do anything
			}
			// NOTE: Possibly change this in the future because this method relies on the CompTab instead of the BoxTab
			// However, the segment methods need to be run due to their RECOMPILE method invocation
			return true;
		} else if(this.remBox.mousePressed(x, y, button)) {
			try {
				this.cAndB.getCurrentCompTab().getCurrent().segment.removeBoxAtIndex(this.cAndB.getBoxTab().currentObjIndex);
				this.cAndB.getBoxTab().currentObjIndex = -1;
			} catch(IndexOutOfBoundsException e) {
				// No selected box to remove, so nothing can be done
			} catch(BioMorphoNoCurrentObjectException e) {
				// No comp to remove boxes into so there's no reason to do anything
			}
			// NOTE: Possibly change this in the future because this method relies on the CompTab instead of the BoxTab
			// However, the segment methods need to be run due to their RECOMPILE method invocation
			return true;
		} else if(this.newSubComp.mousePressed(x, y, button)) {
			try {
				if(!this.cAndB.getCurrentCompTab().getCurrent().segment.getHasSubComps()) {
					this.cAndB.getCurrentCompTab().getCurrent().segment.addNewDefaultSubComp();
				}
			} catch(BioMorphoNoCurrentObjectException e) {
				// No selected component so there's no reason to do anything!
			}
			return true;
		} else if(this.copyComp.mousePressed(x, y, button)) {
			try {
				EntityComponent copy = this.cAndB.getCurrentCompTab().getCurrent().copy();
				((ArrayList<EntityComponent>)this.cAndB.getCurrentCompTab().members).add(copy);
				this.cAndB.getCurrentCompTab().currentObjIndex = this.cAndB.getCurrentCompTab().members.size() - 1;
				this.cAndB.getCurrentCompTab().setPageNum(this.cAndB.getCurrentCompTab().pageMax());
			} catch(BioMorphoNoCurrentObjectException e) {}
			return true;
		} else if(this.reflectComp.mousePressed(x, y, button)) {
			try {RandomUtils.reflectComp(this.cAndB.getCurrentCompTab().getCurrent());}
			catch(BioMorphoNoCurrentObjectException e) {}
			return true;
		}
		return false;
	}
	
	public void draw(GuiBasic gui, TextureManager renderEngine) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.bindTexture(renderEngine, GuiTextures.MODEL_EDITOR_MISC);
		gui.drawTexturedRect(this.newComp.getX(), this.newComp.getY(), this.newComp.getWidth(), this.newComp.getHeight(), 0, 0, 256, 256);
		gui.drawTexturedRect(this.remComp.getX(), this.remComp.getY(), this.remComp.getWidth(), this.remComp.getHeight(), 0, 20, 256, 256);
		gui.drawTexturedRect(this.newBox.getX(), this.newBox.getY(), this.newBox.getWidth(), this.newBox.getHeight(), 0, 40, 256, 256);
		gui.drawTexturedRect(this.remBox.getX(), this.remBox.getY(), this.remBox.getWidth(), this.remBox.getHeight(), 0, 60, 256, 256);
		gui.drawTexturedRect(this.newSubComp.getX(), this.newSubComp.getY(), this.newSubComp.getWidth(), this.newSubComp.getHeight(), 0, 80, 256, 256);
		gui.drawTexturedRect(this.copyComp.getX(), this.copyComp.getY(), this.copyComp.getWidth(), this.copyComp.getHeight(), 0, 100, 256, 256);
		gui.drawTexturedRect(this.reflectComp.getX(), this.reflectComp.getY(), this.reflectComp.getWidth(), this.reflectComp.getHeight(), 0, 120, 256, 256);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
