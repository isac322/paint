package paint;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

class ShapeObject extends JComponent {
	protected Point anchorPoint;
	protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	protected boolean overbearing = true;
	private static final long serialVersionUID = 7209900239540363764L;
	private PaintInfo info = null;
	private int width, height;
	
	public ShapeObject(PaintInfo info) {
		this.info = info;
		this.addDragListeners();
		this.setOpaque(false);
		this.width = Math.abs(info.end.x - info.start.x);
		this.height = Math.abs(info.end.y - info.start.y);
		this.setSize(new Dimension(width + 1, height + 1));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.paintshape_s(g);
	}
	
	void paintshape_s(Graphics g) {
		g.setColor(info.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(info.stroke);
		
		switch(info.type) {
		case Rect:
			if (info.fill) {
				if (info.color != info.innerColor) g.setColor(info.innerColor);
				g.fillRect(0, 0, width, height);
			}
			g.setColor(info.color);
			g.drawRect(0, 0, width, height);
			break;
			
		case Oval:
			if (info.fill) {
				if (info.color != info.innerColor) g.setColor(info.innerColor);
				g.fillOval(0, 0, width, height);
			}
			g.setColor(info.color);
			g.drawOval(0, 0, width, height);
			break;
			
		case Pen :
		case Line :
			g.drawLine(info.start.x, info.start.y, info.end.x, info.end.y);
			break;
			
		default:
			System.exit(1);
			break;
		}
	}
	
	private void addDragListeners() {
		final ShapeObject handle = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				anchorPoint = e.getPoint();
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int anchorX = anchorPoint.x;
				int anchorY = anchorPoint.y;

				Point parentOnScreen = getParent().getLocationOnScreen();
				Point mouseOnScreen = e.getLocationOnScreen();
				Point position = new Point(mouseOnScreen.x - parentOnScreen.x
						- anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
				setLocation(position);
 
				// Change Z-Buffer if it is "overbearing"
				if (overbearing) {
					getParent().setComponentZOrder(handle, 0);
					repaint();
				}
			}
		});
	}
}