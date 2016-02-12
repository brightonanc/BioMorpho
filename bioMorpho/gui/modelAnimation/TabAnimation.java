package bioMorpho.gui.modelAnimation;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.GuiMyTabSearchable;
import bioMorpho.lifeform.model.animation.ModelAnimations;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class TabAnimation extends GuiMyTabSearchable {
	
	public ModelAnimations defaultAnimation = ModelAnimations.NONE;
	
	private GuiMyButton[] btnsAnimations;
	
	public TabAnimation(int unscaledLeft, int unscaledTop, int membersPerPage) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.currentObj = this.defaultAnimation;
		
		ArrayList<ModelAnimations> list = new ArrayList<ModelAnimations>();
		for(ModelAnimations curAnimation : ModelAnimations.values()) {
			if(curAnimation != this.defaultAnimation) list.add(curAnimation);
		}
		Collections.sort(list, ModelAnimations.getGuiComparator());
		this.baseMembers.add(this.defaultAnimation);
		this.baseMembers.addAll(list);
	}
	
	@Override
	public void setCurrentObjByPageIndex(int pageIndex) {
		int index = this.minIndexForPage() + pageIndex;
		if(index < this.members.size()) {
			this.currentObj = this.members.get(index);
		}
	}
	
	@Override
	protected void initButtons(int unscaledLeft, int unscaledTop) {
		this.btnPageUp = new GuiMyButton(unscaledLeft+384+51, unscaledTop+146, 12, 12, this.ids.getNextId());
		this.btnPageDown = new GuiMyButton(unscaledLeft+384+51+14, unscaledTop+146, 12, 12, this.ids.getNextId());
		
		this.btnsAnimations =  new GuiMyButton[this.membersPerPage];
		int btnsBoxesHeight = 12;
		for(int i = 0; i < this.btnsAnimations.length; i++) {
			this.btnsAnimations[i] = new GuiMyButton(unscaledLeft+384+4, unscaledTop+160+(i*btnsBoxesHeight), 120, btnsBoxesHeight, this.ids.getNextId());
		}
	}

	@Override
	protected boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.btnsAnimations.length; i++) {
			if(this.btnsAnimations[i].mousePressed(x, y, button)) {
				this.setCurrentObjByPageIndex(i);
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
	
	@Override
	public ModelAnimations getBaseMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException {
		try {
			return (ModelAnimations)this.baseMembers.get(index);
		} catch(IndexOutOfBoundsException e) {
			throw new BioMorphoNoCurrentObjectException();
		}
	}

	@Override
	public ModelAnimations getMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException {
		try {
			return (ModelAnimations)this.members.get(index);
		} catch(IndexOutOfBoundsException e) {
			throw new BioMorphoNoCurrentObjectException();
		}
	}

	@Override
	public ModelAnimations getCurrent() throws BioMorphoNoCurrentObjectException {
		if(this.currentObj != null) {
			return (ModelAnimations)this.currentObj;
		}
		throw new BioMorphoNoCurrentObjectException();
	}

	@Override
	public void draw(Gui gui, TextureManager renderEngine, FontRenderer fontRenderer) {
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
			int x = this.btnsAnimations[i].getX();
			int y = this.btnsAnimations[i].getY();
			int width = this.btnsAnimations[i].getWidth();
			int height = this.btnsAnimations[i].getHeight();
			ModelAnimations curAnimation;
			try {
				curAnimation = this.getMemberAtIndex(this.pageIndexToAbsIndex(i));
			} catch(BioMorphoNoCurrentObjectException e) {
				// This should NEVER occur, because the loop count for this loop is the number of elements on the page,
				// therefore we should never enter a situation where there is no member at the specified index!
				curAnimation = null;
			}
			// Texture needs to be reloaded because drawing Strings loads a separate texture
			RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
			if(curIndex != i) {
				gui.drawTexturedModalRect(x, y, 0, 0, width, height);
				fontRenderer.drawString(curAnimation.getGuiName(), x+1+2, y+1+1, -1, true);
			}
			else {
				gui.drawTexturedModalRect(x, y, 0, 12, width, height);
				fontRenderer.drawString(curAnimation.getGuiName(), x+1+2, y+1+1, -1, true);
			}
		}
		
		RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
		gui.drawTexturedModalRect(this.btnPageUp.getX(), this.btnPageUp.getY(), 172, 0, this.btnPageUp.getWidth(), this.btnPageUp.getHeight());
		gui.drawTexturedModalRect(this.btnPageDown.getX(), this.btnPageDown.getY(), 186, 0, this.btnPageDown.getWidth(), this.btnPageDown.getHeight());
		
		fontRenderer.drawString(this.pageMax()+"", this.unscaledLeft+384+5+2, this.unscaledTop+147+1, -1, true);
		
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
