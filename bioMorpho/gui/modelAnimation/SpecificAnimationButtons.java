package bioMorpho.gui.modelAnimation;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class SpecificAnimationButtons {
	
	protected IdGenerator ids = new IdGenerator();
	
	public AnimationSelectionSetup animSel;
	public int unscaledLeft;
	public int unscaledTop;
	
	public int currentButtonIndex;
	protected GuiMyButton[] btns;
	
	public SpecificAnimationButtons(AnimationSelectionSetup animSel, int unscaledLeft, int unscaledTop) {
		this.animSel = animSel;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.currentButtonIndex = -1;
		
		this.btns = new GuiMyButton[10];
		int btnWidth = 12;
		for(int i = 0; i < this.btns.length; i++) this.btns[i] = new GuiMyButton(unscaledLeft+384+4+(i*btnWidth), unscaledTop+4, btnWidth, 12, this.ids.getNextId());
	}
	
	public boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.btns.length; i++) {
			if(this.btns[i].mousePressed(x, y, button)) {
				this.setCurrentButtonIndex(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * DO NOT USE FOR SETTING TO -1. Manually do that as .currentButtonIndex is public
	 */
	protected void setCurrentButtonIndex(int index) {
		this.currentButtonIndex = index;
		this.animSel.specAnimButtonChanged(index);
	}
	
	public void draw(Gui gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		for(int i = 0; i < this.btns.length; i++) {
			GuiMyButton btn = this.btns[i];
			RenderUtils.bindTexture(renderEngine, GuiTextures.ANIMATION_MISC);
			if(i != this.currentButtonIndex) {
				gui.drawTexturedModalRect(btn.getX(), btn.getY(), 0, 0, btn.getWidth(), btn.getHeight());
			} else {
				gui.drawTexturedModalRect(btn.getX(), btn.getY(), 0, 12, btn.getWidth(), btn.getHeight());
			}
		}
		
		fontRenderer.drawString("A", this.btns[0].getX()+1+2, this.btns[0].getY()+1+1, -1, true);
		fontRenderer.drawString("B", this.btns[1].getX()+1+2, this.btns[1].getY()+1+1, -1, true);
		fontRenderer.drawString("C", this.btns[2].getX()+1+2, this.btns[2].getY()+1+1, -1, true);
		fontRenderer.drawString("D", this.btns[3].getX()+1+2, this.btns[3].getY()+1+1, -1, true);
		fontRenderer.drawString("E", this.btns[4].getX()+1+2, this.btns[4].getY()+1+1, -1, true);
		fontRenderer.drawString("F", this.btns[5].getX()+1+2, this.btns[5].getY()+1+1, -1, true);
		fontRenderer.drawString("G", this.btns[6].getX()+1+2, this.btns[6].getY()+1+1, -1, true);
		fontRenderer.drawString("H", this.btns[7].getX()+1+2, this.btns[7].getY()+1+1, -1, true);
		fontRenderer.drawString("I", this.btns[8].getX()+1+2, this.btns[8].getY()+1+1, -1, true);
		fontRenderer.drawString("J", this.btns[9].getX()+1+2, this.btns[9].getY()+1+1, -1, true);
	}
}
