package bioMorpho.gui.lifeformSelection;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformSelectionExtra implements IModelElement {
	
	private IdGenerator ids = new IdGenerator();
	
	private GuiLifeformSelection gui;
	
	private TabLifeform tabLifeform;
	
	private GuiMyButton newLifeform;
	private GuiMyButton remLifeform;
	
	private IndexSelectionPanel indexSel;
	
	private long remLifeformInitTime;
	private int remLifeformState = 0;
	
	public LifeformSelectionExtra(GuiLifeformSelection gui, int unscaledLeft, int unscaledTop, TabLifeform tabLifeform) {
		this.gui = gui;
		this.tabLifeform = tabLifeform;
		this.newLifeform = new GuiMyButton(unscaledLeft+481, unscaledTop+8, 22, 22, this.ids.getNextId());
		this.remLifeform = new GuiMyButton(unscaledLeft+481, unscaledTop+30, 22, 22, this.ids.getNextId());
		this.indexSel = new IndexSelectionPanel(gui, unscaledLeft, unscaledTop);
	}
	
	public void mousePressedTextFields(int x, int y, int button) {}
	public boolean keyPressed(char keyChar, int keyCode) {return false;}
	
	@Override
	public void onGuiUpdate() {
		if(this.remLifeformState != 0) {
			if(System.currentTimeMillis() - this.remLifeformInitTime > 3000) {
				this.remLifeformState = 0;
			}
		}
	}
	
	@Override
	public boolean mousePressed(int x, int y, int button) {
		if(this.newLifeform.mousePressed(x, y, button)) {
			((ArrayList<Lifeform>)this.tabLifeform.members).add(new Lifeform());
			this.tabLifeform.currentObjIndex = this.tabLifeform.members.size() - 1;
			this.tabLifeform.setPageNum(this.tabLifeform.pageMax());
			return true;
		}
		if(this.remLifeform.mousePressed(x, y, button)) {
			if(this.remLifeformState == 0) this.remLifeformInitTime = System.currentTimeMillis();
			this.remLifeformState++;
			if(this.remLifeformState == 4) {
				try {
					((ArrayList<Lifeform>)this.tabLifeform.members).remove(this.tabLifeform.currentObjIndex);
					if(this.indexSel.currentLifeformIsPointedTo()) {
						for(int i = 0; i < this.gui.getIndexPointers().length; i++) {
							if(this.gui.getIndexPointers()[i] == this.tabLifeform.currentObjIndex) {
								this.gui.getIndexPointers()[i] = -1;
								break;
							}
						}
					}
					for(int i = 0; i < this.gui.getIndexPointers().length; i++) {
						if(this.gui.getIndexPointers()[i] > this.tabLifeform.currentObjIndex) {
							this.gui.getIndexPointers()[i]--;
						}
					}
					this.tabLifeform.currentObjIndex = -1;
				} catch(IndexOutOfBoundsException e) {
					// Nothing selected, so nothing is deleted
				}
				
				this.remLifeformState = 0;
			}
			return true;
		}
		if(this.indexSel.mousePressed(x, y, button)) return true;
		return false;
	}
	
	public void draw(GuiBasic gui) {
		RenderUtils.bindTexture(gui.getMinecraft().renderEngine, GuiTextures.LIFEFORM_SELECTION_MISC);
		GuiMyButton btn;
		btn = this.newLifeform;
		gui.drawTexturedRect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight(), 0, 134, 256, 256);
		btn = this.remLifeform;
		gui.drawTexturedRect(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight(), this.remLifeformState*22, 158, 256, 256);
		this.indexSel.draw(gui);
	}
	
}
