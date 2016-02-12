package bioMorpho.gui;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiMyButton {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int id;
	
	public GuiMyButton(int x, int y, int width, int height, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public boolean mousePressed(int x, int y, int button) {
		return
		x > this.x &&
		x <= this.x+this.width &&
		y > this.y &&
		y <= this.y+this.height;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
}
