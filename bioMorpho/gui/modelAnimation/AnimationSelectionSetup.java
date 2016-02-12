package bioMorpho.gui.modelAnimation;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.compAndBox.CompOnlySetup;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.lifeform.model.animation.BioMorphoPlayerEvents;
import bioMorpho.lifeform.model.animation.ModelAnimations;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimationSelectionSetup implements IModelElement {
	
	private Gui gui;
	private CompOnlySetup compOnly;
	private FontRenderer fontRenderer;
	private ModelCustom model;
	private int unscaledLeft;
	private int unscaledTop;
	
	private AnimationSelectionTextFields textFields;
	
	private TabEvent eventTab;
	private TabAnimation animationTab;
	
	private SpecificAnimationButtons specAnim;
	private AnimSelDrawnText drawnText;
	private AnimationFieldInput animFieldInput;
	
	private EntityComponent priorComp = null;
	private BioMorphoPlayerEvents priorEvent = null;
	private int priorSpecAnimIndex = -1;
	private ModelAnimations priorAnimation = null;
	
	public AnimationSelectionSetup(Gui gui, CompOnlySetup compOnly, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, ModelCustom model, TrustMutex textFieldMutex) {
		this.gui = gui;
		this.compOnly = compOnly;
		this.fontRenderer = fontRenderer;
		this.model = model;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		
		this.textFields = new AnimationSelectionTextFields(this, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
		
		this.eventTab = new TabEvent(unscaledLeft, unscaledTop, 3);
		this.animationTab = new TabAnimation(unscaledLeft, unscaledTop, 3);
		
		this.specAnim = new SpecificAnimationButtons(this, unscaledLeft, unscaledTop);
		this.drawnText = new AnimSelDrawnText(unscaledLeft, unscaledTop);
		this.animFieldInput = new AnimationFieldInput(gui, this, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
	}
	
	@Override
	public void onGuiUpdate() {
		if(!this.textFields.getTextFieldById(this.textFields.eventPageId).isFocused()) {
			this.textFields.getTextFieldById(this.textFields.eventPageId).setText(this.getEventTab().getCurrentPageNum()+"");
		}
		if(!this.textFields.getTextFieldById(this.textFields.animationPageId).isFocused()) {
			this.textFields.getTextFieldById(this.textFields.animationPageId).setText(this.getAnimationTab().getCurrentPageNum()+"");
		}
		
		boolean isCompSelected;
		try {
			this.compOnly.getCurrentCompTab().getCurrent();
			isCompSelected = true;
		} catch(BioMorphoNoCurrentObjectException e) {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.eventNameSearchId ||
					this.textFields.getActiveTextFieldValue() == this.textFields.animationNameSearchId) this.textFields.setActiveTextField(-1);
			this.textFields.getTextFieldById(this.textFields.eventNameSearchId).setText("");
			this.textFields.getTextFieldById(this.textFields.animationNameSearchId).setText("");
			isCompSelected = false;
		}
		
		String eventSearch = this.textFields.getTextFieldById(this.textFields.eventNameSearchId).getText();
		if(!eventSearch.equals("")) {
			ArrayList<BioMorphoPlayerEvents> searchedEvents = new ArrayList<BioMorphoPlayerEvents>();
			for(int i = 0; i < this.getEventTab().baseMembers.size(); i++) {
				try {
					if(this.getEventTab().getBaseMemberAtIndex(i).getGuiName().toLowerCase().contains(eventSearch.toLowerCase())) {
						searchedEvents.add(this.getEventTab().getBaseMemberAtIndex(i));
					}
				} catch(BioMorphoNoCurrentObjectException e) {/*Will NEVER occur. for loop iterates through all values, so we won't exceed baseMember.size()*/}
			}
			this.getEventTab().members = searchedEvents;
		}
		else {
			this.getEventTab().members = this.getEventTab().baseMembers;
		}
		
		String animationSearch = this.textFields.getTextFieldById(this.textFields.animationNameSearchId).getText();
		if(!animationSearch.equals("")) {
			ArrayList<ModelAnimations> searchedAnimations = new ArrayList<ModelAnimations>();
			for(int i = 0; i < this.getAnimationTab().baseMembers.size(); i++) {
				try {
					if(this.getAnimationTab().getBaseMemberAtIndex(i).getGuiName().toLowerCase().contains(animationSearch.toLowerCase())) {
						searchedAnimations.add(this.getAnimationTab().getBaseMemberAtIndex(i));
					}
				} catch(BioMorphoNoCurrentObjectException e) {/*Will NEVER occur. for loop iterates through all values, so we won't exceed baseMember.size()*/}
			}
			this.getAnimationTab().members = searchedAnimations;
		}
		else {
			this.getAnimationTab().members = this.getAnimationTab().baseMembers;
		}
		
		try {
			EntityComponent curComp = this.compOnly.getCurrentCompTab().getCurrent();
			boolean compChanged = curComp != this.priorComp;
			if(compChanged) {
				this.specAnim.setCurrentButtonIndex(0);
				this.getEventTab().members = this.getEventTab().baseMembers;
				this.getAnimationTab().members = this.getAnimationTab().baseMembers;
				this.getEventTab().currentObj = curComp.animRenderObjs[0].getEvent();
				this.getAnimationTab().currentObj = curComp.animRenderObjs[0].getAnimation();
				this.textFields.getTextFieldById(this.textFields.eventNameSearchId).setText("");
				this.textFields.getTextFieldById(this.textFields.animationNameSearchId).setText("");
				this.getEventTab().setPageNum(1);
				this.getAnimationTab().setPageNum(1);
				this.animFieldInput.setSettingsAndFields(curComp.animRenderObjs[0].getSettings());
			}
			this.priorComp = curComp;
			
			BioMorphoPlayerEvents curEvent = this.getEventTab().getCurrent();
			if(curEvent != this.priorEvent) {
				curComp.animRenderObjs[this.specAnim.currentButtonIndex].setEvent(curEvent);
			}
			this.priorEvent = curEvent;
			int curSpecAnimIndex = this.specAnim.currentButtonIndex;
			ModelAnimations curAnimation = this.getAnimationTab().getCurrent();
			if(curAnimation != this.priorAnimation) {
				if(curSpecAnimIndex == this.priorSpecAnimIndex && !compChanged) {
					curComp.animRenderObjs[this.specAnim.currentButtonIndex].setAnimationWithNewSettings(curAnimation);
					this.animFieldInput.setSettingsAndFields(curComp.animRenderObjs[curSpecAnimIndex].getSettings());
				}
			}
			this.priorSpecAnimIndex = curSpecAnimIndex;
			this.priorAnimation = curAnimation;
		} catch(BioMorphoNoCurrentObjectException e) {
			// No selected comp
			this.specAnim.currentButtonIndex = -1;
			this.getEventTab().members = new ArrayList<BioMorphoPlayerEvents>();
			this.getAnimationTab().members = new ArrayList<ModelAnimations>();
			this.getEventTab().currentObj = null;
			this.getAnimationTab().currentObj = null;
			this.textFields.getTextFieldById(this.textFields.eventNameSearchId).setText("");
			this.textFields.getTextFieldById(this.textFields.animationNameSearchId).setText("");
			this.getEventTab().setPageNum(1);
			this.getAnimationTab().setPageNum(1);
			
			this.priorComp = null;
			this.priorEvent = null;
			this.priorAnimation = null;
		}
		this.animFieldInput.onGuiUpdate();
	}
	
	public void specAnimButtonChanged(int index) {
		try {
			EntityComponent curComp = this.compOnly.getCurrentCompTab().getCurrent();
			this.getEventTab().currentObj = curComp.animRenderObjs[index].getEvent();
			this.getAnimationTab().currentObj = curComp.animRenderObjs[index].getAnimation();
			this.textFields.getTextFieldById(this.textFields.eventNameSearchId).setText("");
			this.textFields.getTextFieldById(this.textFields.animationNameSearchId).setText("");
			this.getEventTab().members = this.getEventTab().baseMembers;
			this.getAnimationTab().members = this.getAnimationTab().baseMembers;
			this.getEventTab().setPageNum(1);
			this.getAnimationTab().setPageNum(1);
			this.animFieldInput.setSettingsAndFields(curComp.animRenderObjs[index].getSettings());
		} catch(BioMorphoNoCurrentObjectException e) {
			this.specAnim.currentButtonIndex = -1;
			// Let the onGuiUpdate() method reset everything now that there is no current comp
		}
		
	}
	
	public TabEvent getEventTab() { return this.eventTab; }
	public TabAnimation getAnimationTab() { return this.animationTab; }
	public ModelCustom getModel() { return this.model; }

	@Override
	public void mousePressedTextFields(int x, int y, int button) {
		this.textFields.mousePressed(x, y, button);
		this.animFieldInput.mousePressedTextFields(x, y, button);
	}

	@Override
	public boolean mousePressed(int x, int y, int button) {
		if(this.eventTab.mousePressed(x, y, button)) return true;
		if(this.animationTab.mousePressed(x, y, button)) return true;
		if(this.specAnim.mousePressed(x, y, button)) return true;
		if(this.animFieldInput.mousePressed(x, y, button)) return true;
		return false;
	}

	@Override
	public boolean keyPressed(char keyChar, int keyCode) {
		if(this.textFields.keyPressed(keyChar, keyCode)) return true;
		if(this.animFieldInput.keyPressed(keyChar, keyCode)) return true;
		return false;
	}
	
	public void draw(Gui gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		RenderUtils.bindTexture(renderEngine, GuiTextures.ANIMATION_SELECTION_BKGRND);
		int scale = 2; // The scale is 2 because our texture file is 512x512 instead of the default 256x256
		GL11.glScalef(scale, scale, 1F);
		gui.drawTexturedModalRect((this.unscaledLeft+384)/scale, this.unscaledTop/scale, 0, 0, 128/scale, 320/scale);
		GL11.glPopMatrix();
		this.textFields.draw();
		GL11.glColor3f(1F, 1F, 1F); // The text fields alter the color scheme when they draw a vertical line to represent the limit of their String/text
		this.animFieldInput.draw(renderEngine);
		GL11.glColor3f(1F, 1F, 1F); // The text fields alter the color scheme when they draw a vertical line to represent the limit of their String/text
		this.getEventTab().draw(gui, renderEngine, fontRenderer);
		this.getAnimationTab().draw(gui, renderEngine, fontRenderer);
		this.specAnim.draw(gui, renderEngine, fontRenderer);
		BioMorphoPlayerEvents curEvent;
		try { curEvent = this.getEventTab().getCurrent(); }
		catch(BioMorphoNoCurrentObjectException e) { curEvent = null; }
		ModelAnimations curAnimation;
		try { curAnimation = this.getAnimationTab().getCurrent(); }
		catch(BioMorphoNoCurrentObjectException e) { curAnimation = null; }
		this.drawnText.draw(fontRenderer, curEvent, curAnimation);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
