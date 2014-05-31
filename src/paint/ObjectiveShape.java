package paint;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

class ObjectiveShape extends JComponent {
	private static final long serialVersionUID = 7209900239540363764L;
	protected Point anchorPoint;
	protected boolean overbearing = true;
	private PaintInfo drawInfo = null;
	private int strokeWidth = 0;
	
	public ObjectiveShape(PaintInfo drawInfo) {
		this.drawInfo = drawInfo;
		this.addDragListeners();
		this.setOpaque(false);
		strokeWidth = (int) (((BasicStroke) drawInfo.stroke).getLineWidth() + 0.5);
		this.setSize(Math.abs(drawInfo.end.x - drawInfo.start.x)+ 2*strokeWidth,
				Math.abs(drawInfo.end.y - drawInfo.start.y)+ 2*strokeWidth);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(drawInfo.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(drawInfo.stroke);
		strokeWidth = (int) (((BasicStroke) drawInfo.stroke).getLineWidth() + 0.5);
		
		switch(drawInfo.type) {
		case Rect:
			if (drawInfo.fill) {
				if (drawInfo.color != drawInfo.innerColor) g.setColor(drawInfo.innerColor);
				g.fillRect(strokeWidth, strokeWidth,
						getWidth() - 2*strokeWidth,
						getHeight() - 2*strokeWidth);
			}
			g.setColor(drawInfo.color);
			g.drawRect(strokeWidth, strokeWidth,
					getWidth() - 2*strokeWidth,
					getHeight() - 2*strokeWidth);
			break;
			
		case Oval:
			if (drawInfo.fill) {
				if (drawInfo.color != drawInfo.innerColor) g.setColor(drawInfo.innerColor);
				g.fillOval(strokeWidth, strokeWidth,
						getWidth() - 2*strokeWidth,
						getHeight() - 2*strokeWidth);
			}
			g.setColor(drawInfo.color);
			g.drawOval(strokeWidth, strokeWidth,
					getWidth() - 2*strokeWidth,
					getHeight() - 2*strokeWidth);
			break;
			
		default:
			System.exit(1);
			break;
		}
	}
	
	private void addDragListeners() {
		final ObjectiveShape handle = this;
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
				
				if (overbearing) {
					getParent().setComponentZOrder(handle, 0);
				}
			}
		});
	}
}