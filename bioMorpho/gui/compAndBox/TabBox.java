package bioMorpho.gui.compAndBox;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.GuiMyTab;
import bioMorpho.lifeform.model.SegmentBox;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class TabBox extends GuiMyTab {
	
	private GuiMyButton[] btnsBoxes;
	
	public TabBox(int unscaledLeft, int unscaledTop, int membersPerPage, ArrayList<SegmentBox> members) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.members = members;
	}
	
	@Override
	protected void initButtons(int unscaledLeft, int unscaledTop) {
		this.btnPageUp = new GuiMyButton(unscaledLeft+51, unscaledTop+194, 12, 12, this.ids.getNextId());
		this.btnPageDown = new GuiMyButton(unscaledLeft+51+14, unscaledTop+194, 12, 12, this.ids.getNextId());
		
		this.btnsBoxes =  new GuiMyButton[this.membersPerPage];
		int btnsBoxesHeight = 12;
		for(int i = 0; i < this.btnsBoxes.length; i++) {
			this.btnsBoxes[i] = new GuiMyButton(unscaledLeft+4, unscaledTop+206+(i*btnsBoxesHeight), 120, btnsBoxesHeight, this.ids.getNextId());
		}
	}
	
	public boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.btnsBoxes.length; i++) {
			if(this.btnsBoxes[i].mousePressed(x, y, button)) {
				this.setCurrentObjIndexByPageIndex(i);
				return true;
			}
		}
		
		if(this.btnPageUp.mousePressed(x, y, button)) {
			this.pageUpOne();
			return true;
		}
		if(this.btnPageDown.mousePressed(x, y, button)) {
			this.pageDownOne();
			return true;
		}
		return false;
	}
	
	public SegmentBox getMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException {
		try {
			return (SegmentBox)this.members.get(index);
		} catch(IndexOutOfBoundsException e) {
			throw new BioMorphoNoCurrentObjectException();
		}
	}
	
	public SegmentBox getCurrent() throws BioMorphoNoCurrentObjectException {
		if(this.currentObjIndex != -1) {
			return (SegmentBox)this.members.get(this.currentObjIndex);
		}
		throw new BioMorphoNoCurrentObjectException();
	}

	@Override
	public void draw(GuiBasic gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		
		int curIndex;
		try {
			curIndex = this.getIndexOfCurrentOnPage();
		} catch(BioMorphoNoCurrentObjectException e) {
			curIndex = -1;
		}
		
		// Render the boxes' buttons
		for(int i = 0; i < this.getElementsOnCurrentPage(); i++) {
			int x = this.btnsBoxes[i].getX();
			int y = this.btnsBoxes[i].getY();
			int width = this.btnsBoxes[i].getWidth();
			int height = this.btnsBoxes[i].getHeight();
			SegmentBox curBox;
			try {
				curBox = this.getMemberAtIndex(this.pageIndexToAbsIndex(i));
			} catch(BioMorphoNoCurrentObjectException e) {
				// This should NEVER occur, because the loop count for this loop is the number of elements on the page,
				// therefore we should never enter a situation where there is no member at the specified index!
				curBox = null;
			}
			// Texture needs to be reloaded because drawing Strings loads a separate texture
			RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
			if(curIndex != i) {
				gui.drawTexturedModalRect(x, y, 0, 0, width, height);
				fontRenderer.drawString(curBox.name, x+1+2, y+1+1, -1, true);
			}
			else {
				gui.drawTexturedModalRect(x, y, 0, 12, width, height);
				fontRenderer.drawString(curBox.name, x+1+2, y+1+1, -1, true);
			}
		}
		
		RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
		gui.drawTexturedModalRect(this.btnPageUp.getX(), this.btnPageUp.getY(), 172, 0, this.btnPageUp.getWidth(), this.btnPageUp.getHeight());
		gui.drawTexturedModalRect(this.btnPageDown.getX(), this.btnPageDown.getY(), 186, 0, this.btnPageDown.getWidth(), this.btnPageDown.getHeight());
		
		fontRenderer.drawString(this.pageMax()+"", this.unscaledLeft+5+2, this.unscaledTop+195+1, -1, true);
		
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
