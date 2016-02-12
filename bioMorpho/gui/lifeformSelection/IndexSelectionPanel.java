package bioMorpho.gui.lifeformSelection;

import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class IndexSelectionPanel {
	
	private IdGenerator ids = new IdGenerator();
	
	private GuiLifeformSelection gui;
	private GuiMyButton[] indexButtons = new GuiMyButton[7];
	
	public IndexSelectionPanel(GuiLifeformSelection gui, int unscaledLeft, int unscaledTop) {
		this.gui = gui;
		int buttonHeight = 22;
		for(int i = 0; i < this.indexButtons.length; i++) {
			this.indexButtons[i] = new GuiMyButton(unscaledLeft+480, unscaledTop+59+(buttonHeight*i), 22, buttonHeight, this.ids.getNextId());
		}
	}
	
	public boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.indexButtons.length; i++) {
			if(this.indexButtons[i].mousePressed(x, y, button)) {
				if(button == 0) {
					if(this.isIndexAvailable(i) && !this.currentLifeformIsPointedTo()) this.setIndexPointerToCurrentLifeform(i);
				} else if(button == 1) {
					if(this.isIndexPointerSetToCurrentLifeform(i)) this.flagIndexPointerAsEmpty(i);
				}
				return true;
			}
		}
		return false;
	}
	
	protected boolean isIndexAvailable(int index) {return this.gui.getIndexPointers()[index] == -1;}
	public void setIndexPointerToCurrentLifeform(int index) {if(this.gui.getCurrentLifeformIndex() != -1) this.gui.getIndexPointers()[index] = this.gui.getCurrentLifeformIndex();}
	public void flagIndexPointerAsEmpty(int index) {this.gui.getIndexPointers()[index] = -1;}
	public boolean isIndexPointerSetToCurrentLifeform(int index) {return this.gui.getIndexPointers()[index] == this.gui.getCurrentLifeformIndex();}
	public boolean currentLifeformIsPointedTo() {
		for(int curPointer : this.gui.getIndexPointers()) {
			if(curPointer == this.gui.getCurrentLifeformIndex()) return true;
		}
		return false;
	}
	
	public void draw(GuiBasic gui) {
		RenderUtils.bindTexture(gui.getMinecraft().renderEngine, GuiTextures.LIFEFORM_SELECTION_MISC);
		for(int i = 0; i < this.indexButtons.length; i++) {
			GuiMyButton btn = this.indexButtons[i];
			if(this.isIndexAvailable(i)) gui.drawTexturedRect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight(), 0, 182, 256, 256);
			else if(this.isIndexPointerSetToCurrentLifeform(i)) gui.drawTexturedRect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight(), 22, 182, 256, 256);
			else gui.drawTexturedRect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight(), 44, 182, 256, 256);
		}
	}
	
}
