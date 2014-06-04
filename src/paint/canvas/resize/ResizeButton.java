package paint.canvas.resize;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import paint.canvas.ObjectiveShape;

abstract public class ResizeButton extends JComponent implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -1735976506496086437L;
	protected Cursor cursor = null;
	protected Point anchorPoint = new Point(0, 0);
	protected ObjectiveShape selectedShape;
	
	public ResizeButton() {
		this.setVisible(true);
		this.setOpaque(false);
		this.setSize(15, 15);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void setTarget(ObjectiveShape target) {
		selectedShape = target;
	}
	
	public void setCursor(int type) {
		cursor = Cursor.getPredefinedCursor(type);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
	    g2.setStroke(new BasicStroke(2));
		g2.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		anchorPoint = e.getPoint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) { setCursor(cursor); }
	@Override
	public void mouseExited(MouseEvent e) { setCursor(Cursor.DEFAULT_CURSOR); }
}

class ResizeSE extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeSE() {
		cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point ShapeOnScreen = selectedShape.getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		selectedShape.setSize(mouseOnScreen.x - ShapeOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - ShapeOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.getDrawInfo().end.y = selectedShape.getLocation().y + selectedShape.getHeight();
		selectedShape.getDrawInfo().end.x = selectedShape.getLocation().x + selectedShape.getWidth();
	}
}

class ResizeE extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeE() {
		cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point ShapeOnScreen = selectedShape.getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		selectedShape.setSize(mouseOnScreen.x - ShapeOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				selectedShape.getHeight());
		selectedShape.getDrawInfo().end.x = selectedShape.getLocation().x + selectedShape.getWidth();
	}
}

class ResizeS extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeS() {
		cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point ShapeOnScreen = selectedShape.getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		selectedShape.setSize(selectedShape.getWidth(),
				mouseOnScreen.y - ShapeOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.getDrawInfo().end.y = selectedShape.getLocation().y + selectedShape.getHeight();
	}
}

class ResizeNW extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeNW() {
		cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point canvasOnScreen = selectedShape.getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		Point location = new Point(mouseOnScreen.x - canvasOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - canvasOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.setBounds(location.x, location.y,
				selectedShape.getDrawInfo().end.x - location.x,
				selectedShape.getDrawInfo().end.y - location.y);
		selectedShape.getDrawInfo().start = location;
	}
}

class ResizeNE extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeNE() {
		cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point canvasOnScreen = selectedShape.getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		Point location = new Point(mouseOnScreen.x - canvasOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - canvasOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.setBounds(selectedShape.getX(), location.y,
				location.x - selectedShape.getX(),
				selectedShape.getDrawInfo().end.y - location.y);
		selectedShape.getDrawInfo().start.y = location.y;
		selectedShape.getDrawInfo().end.x = location.x;
	}
}

class ResizeN extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeN() {
		cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point canvasOnScreen = selectedShape.getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		Point location = new Point(mouseOnScreen.x - canvasOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - canvasOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.setBounds(selectedShape.getX(), location.y,
				selectedShape.getWidth(),
				selectedShape.getDrawInfo().end.y - location.y);
		selectedShape.getDrawInfo().start.y = location.y;
	}
}

class ResizeSW extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeSW() {
		cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point canvasOnScreen = selectedShape.getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		Point location = new Point(mouseOnScreen.x - canvasOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - canvasOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.setBounds(location.x, selectedShape.getY(),
				selectedShape.getDrawInfo().end.x - location.x,
				location.y - selectedShape.getDrawInfo().start.y);
		selectedShape.getDrawInfo().start.x = location.x;
		selectedShape.getDrawInfo().end.y = location.y;
	}
}

class ResizeW extends ResizeButton {
	private static final long serialVersionUID = 6204304373474922104L;
	
	public ResizeW() {
		cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		setCursor(cursor);
		Point canvasOnScreen = selectedShape.getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		Point location = new Point(mouseOnScreen.x - canvasOnScreen.x + (this.getWidth()/2 - anchorPoint.x),
				mouseOnScreen.y - canvasOnScreen.y + (this.getHeight()/2 - anchorPoint.y));
		selectedShape.setBounds(location.x, selectedShape.getY(),
				selectedShape.getDrawInfo().end.x - location.x,
				selectedShape.getHeight());
		selectedShape.getDrawInfo().start.x = location.x;
	}
}