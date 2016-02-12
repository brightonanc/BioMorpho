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
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.util.RenderUtils;


/**
 * @author Brighton Ancelin
 *
 */
public class TabComp extends GuiMyTab {
	
	protected CompAndBoxSetup compAndBox;
	protected CompOnlySetup compOnly;
	
	private GuiMyButton[] btnsComps;
	private GuiMyButton[] btnsCompsArrows;
	private GuiMyButton btnTabLeft;
	
	public TabComp(CompAndBoxSetup compAndBox, int unscaledLeft, int unscaledTop, int membersPerPage, ArrayList<EntityComponent> members) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.members = members;
		this.compAndBox = compAndBox;
		this.compOnly = null;
	}
	
	public TabComp(CompOnlySetup compOnly, int unscaledLeft, int unscaledTop, int membersPerPage, ArrayList<EntityComponent> members) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.members = members;
		this.compAndBox = null;
		this.compOnly = compOnly;
	}
	
	@Override
	protected void initButtons(int unscaledLeft, int unscaledTop) {
		this.btnPageUp = new GuiMyButton(unscaledLeft+51, unscaledTop+22, 12, 12, this.ids.getNextId());
		this.btnPageDown = new GuiMyButton(unscaledLeft+51+14, unscaledTop+22, 12, 12, this.ids.getNextId());
		
		this.btnsComps =  new GuiMyButton[this.membersPerPage];
		int btnsCompsHeight = 12;
		for(int i = 0; i < this.btnsComps.length; i++) {
			this.btnsComps[i] = new GuiMyButton(unscaledLeft+4, unscaledTop+34+(i*btnsCompsHeight), 114, btnsCompsHeight, this.ids.getNextId());
		}
		this.btnsCompsArrows =  new GuiMyButton[this.membersPerPage];
		int btnsCompsArrowsHeight = 12;
		for(int i = 0; i < this.btnsCompsArrows.length; i++) {
			this.btnsCompsArrows[i] = new GuiMyButton(unscaledLeft+4+114, unscaledTop+34+(i*btnsCompsArrowsHeight), 6, btnsCompsArrowsHeight, this.ids.getNextId());
		}
		this.btnTabLeft = new GuiMyButton(unscaledLeft+24, unscaledTop+22, 12, 12, this.ids.getNextId());
	}
	
	@Override
	protected boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.btnsComps.length; i++) {
			if(this.btnsComps[i].mousePressed(x, y, button)) {
				this.setCurrentObjIndexByPageIndex(i);
				return true;
			}
		}
		for(int i = 0; i < this.btnsCompsArrows.length; i++) {
			if(this.btnsCompsArrows[i].mousePressed(x, y, button)) {
				try {
					if(this.getMemberAtIndex(this.pageIndexToAbsIndex(i)).segment.getHasSubComps()) {
						this.setCurrentObjIndexByPageIndex(i);
						
						if(this.compAndBox != null) this.compAndBox.compTabRight();
						else if(this.compOnly != null) this.compOnly.compTabRight();
					}
				} catch(BioMorphoNoCurrentObjectException e) {
					this.setCurrentObjIndexByPageIndex(i); //This will set the current objIndex to -1 since we know i is out of bounds due to the caught BioMorphoNoCurrentObjectException
				}
				// Even though the mouse click may have not worked due to the above if conditional, we know that the mouse clicked
				// on this button's zone, so we return true to end the search for the pressed button
				return true;
			}
		}
		if(this.btnTabLeft.mousePressed(x, y, button)) {
			if(this.compAndBox != null) this.compAndBox.compTabLeft();
			else if(this.compOnly != null) this.compOnly.compTabLeft();
			return true;
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
	
	public EntityComponent getMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException {
		try {
			return (EntityComponent)this.members.get(index);
		} catch(IndexOutOfBoundsException e) {
			throw new BioMorphoNoCurrentObjectException();
		}
	}
	
	public EntityComponent getCurrent() throws BioMorphoNoCurrentObjectException {
		if(this.currentObjIndex != -1) {
			return (EntityComponent)this.members.get(this.currentObjIndex);
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
		
		// Render the components' buttons
		for(int i = 0; i < this.getElementsOnCurrentPage(); i++) {
			int x = this.btnsComps[i].getX();
			int y = this.btnsComps[i].getY();
			int width = this.btnsComps[i].getWidth();
			int height = this.btnsComps[i].getHeight();
			int arrowWidth = this.btnsCompsArrows[i].getWidth();
			EntityComponent curComp;
			try {
				curComp = this.getMemberAtIndex(this.pageIndexToAbsIndex(i));
			} catch(BioMorphoNoCurrentObjectException e) {
				// This should NEVER occur, because the loop count for this loop is the number of elements on the page,
				// therefore we should never enter a situation where there is no member at the specified index!
				curComp = null;
			}
			// Texture needs to be reloaded because drawing Strings loads a separate texture
			RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
			if(curIndex != i) {
				if(!curComp.segment.getHasSubComps()) {
					gui.drawTexturedModalRect(x, y, 0, 26, width, height);
					fontRenderer.drawString(curComp.name, x+1+2, y+1+1, -1, true);
				}
				else {
					gui.drawTexturedModalRect(x, y, 0, 26, width+arrowWidth, height);
					fontRenderer.drawString(curComp.name, x+1+2, y+1+1, -1, true);
				}
			}
			else {
				if(!curComp.segment.getHasSubComps()) {
					gui.drawTexturedModalRect(x, y, 0, 38, width, height);
					fontRenderer.drawString(curComp.name, x+1+2, y+1+1, -1, true);
				}
				else {
					gui.drawTexturedModalRect(x, y, 0, 38, width+arrowWidth, height);
					fontRenderer.drawString(curComp.name, x+1+2, y+1+1, -1, true);
				}
			}
		}
		
		RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_MISC);
		gui.drawTexturedModalRect(this.btnTabLeft.getX(), this.btnTabLeft.getY(), 200, 0, this.btnTabLeft.getWidth(), this.btnTabLeft.getHeight());
		gui.drawTexturedModalRect(this.btnPageUp.getX(), this.btnPageUp.getY(), 172, 0, this.btnPageUp.getWidth(), this.btnPageUp.getHeight());
		gui.drawTexturedModalRect(this.btnPageDown.getX(), this.btnPageDown.getY(), 186, 0, this.btnPageDown.getWidth(), this.btnPageDown.getHeight());
		
		fontRenderer.drawString(this.pageMax()+"", this.unscaledLeft+5+2, this.unscaledTop+23+1, -1, true);
		
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
