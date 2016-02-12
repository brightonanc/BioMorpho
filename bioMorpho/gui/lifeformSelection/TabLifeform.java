package bioMorpho.gui.lifeformSelection;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.GuiMyTab;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class TabLifeform extends GuiMyTab {
	
	private GuiMyButton[] btnsLifeforms;
	
	public TabLifeform(int unscaledLeft, int unscaledTop, int membersPerPage, ArrayList<Lifeform> members) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.members = members;
		this.currentObjIndex = 0;
	}
	
	@Override
	protected void initButtons(int unscaledLeft, int unscaledTop) {
		this.btnPageUp = new GuiMyButton(unscaledLeft+10, unscaledTop+251, 23, 65, this.ids.getNextId());
		this.btnPageDown = new GuiMyButton(unscaledLeft+479, unscaledTop+251, 23, 65, this.ids.getNextId());
		
		this.btnsLifeforms =  new GuiMyButton[this.membersPerPage];
		int btnsLifeformsWidth = 86;
		for(int i = 0; i < this.btnsLifeforms.length; i++) {
			this.btnsLifeforms[i] = new GuiMyButton(unscaledLeft+41+(i*btnsLifeformsWidth), unscaledTop+251, btnsLifeformsWidth, 65, this.ids.getNextId());
		}
	}
	
	@Override
	public void setCurrentObjIndexByPageIndex(int pageIndex) {
		int prevIndex = this.currentObjIndex;
		super.setCurrentObjIndexByPageIndex(pageIndex);
		if(this.currentObjIndex == -1) this.currentObjIndex = prevIndex;
	}
	
	public void setPageToCurrentObjPage() {
		int pageIndex = (int)(this.currentObjIndex / this.membersPerPage) + 1;
		this.setPageNum(pageIndex);
	}
	
	@Override
	protected boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.btnsLifeforms.length; i++) {
			if(this.btnsLifeforms[i].mousePressed(x, y, button)) {
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
	
	@Override
	public Lifeform getMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException {
		try {return (Lifeform)this.members.get(index);}
		catch(IndexOutOfBoundsException e) {throw new BioMorphoNoCurrentObjectException();}
	}
	
	@Override
	public Lifeform getCurrent() throws BioMorphoNoCurrentObjectException {
		if(this.currentObjIndex != -1) return (Lifeform)this.members.get(this.currentObjIndex);
		throw new BioMorphoNoCurrentObjectException();
	}
	
	@Override
	public void draw(GuiBasic gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		
		int curIndex;
		try {curIndex = this.getIndexOfCurrentOnPage();}
		catch(BioMorphoNoCurrentObjectException e) {curIndex = -1;}
		
		for(int i = 0; i < this.getElementsOnCurrentPage(); i++) {
			int x = this.btnsLifeforms[i].getX();
			int y = this.btnsLifeforms[i].getY();
			int width = this.btnsLifeforms[i].getWidth();
			int height = this.btnsLifeforms[i].getHeight();
			Lifeform curLifeform;
			try {
				curLifeform = this.getMemberAtIndex(this.pageIndexToAbsIndex(i));
			} catch(BioMorphoNoCurrentObjectException e) {
				// This should NEVER occur, because the loop count for this loop is the number of elements on the page,
				// therefore we should never enter a situation where there is no member at the specified index!
				curLifeform = null;
			}
			// Texture needs to be reloaded because RENDERING MODELS loads a separate texture
			RenderUtils.bindTexture(renderEngine, GuiTextures.LIFEFORM_SELECTION_MISC);
			if(curIndex != i) {
				gui.drawTexturedModalRect(x, y, 0, 0, width, height);
				CustomModelRenderingPort renderPort = new CustomModelRenderingPort(0, 0, 
						curLifeform.getModel(), renderEngine).initCustomAttributes(x+1, y+1, width-2, height-2, 3D);
				renderPort.setRotation(-(-30F), gui.getMinecraft().theWorld.getTotalWorldTime() % 360F);
				renderPort.drawWithoutRotPoints(gui, gui.getMinecraft());
			} else {
				gui.drawTexturedModalRect(x, y, 86, 0, width, height);
				CustomModelRenderingPort renderPort = new CustomModelRenderingPort(0, 0, 
						curLifeform.getModel(), renderEngine).initCustomAttributes(x+1, y+1, width-2, height-2, 3D);
				renderPort.setRotation(-(-30F), gui.getMinecraft().theWorld.getTotalWorldTime() % 360F);
				renderPort.drawWithoutRotPoints(gui, gui.getMinecraft());
			}
		}
		
		RenderUtils.bindTexture(renderEngine, GuiTextures.LIFEFORM_SELECTION_MISC);
		gui.drawTexturedModalRect(this.btnPageUp.getX(), this.btnPageUp.getY(), 0, 67, this.btnPageUp.getWidth(), this.btnPageUp.getHeight());
		gui.drawTexturedModalRect(this.btnPageDown.getX(), this.btnPageDown.getY(), 23, 67, this.btnPageDown.getWidth(), this.btnPageDown.getHeight());
				
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
