package paint.canvas;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import paint.canvas.paint.canvas.resize.ResizePanel;
import paint.model.PaintInfo;

public class ObjectiveShape extends JComponent {
	private static final long serialVersionUID = 7209900239540363764L;
	private final PaintInfo drawInfo;
	private final ResizePanel resizePanel;
	private Point anchorPoint;
	private int strokeWidth = 0;
	
	public ObjectiveShape(PaintInfo drawInfo, ResizePanel resizePanel) {
		this.resizePanel = resizePanel;
		this.drawInfo = drawInfo;
		this.addDragListeners();
		this.setOpaque(false);
		strokeWidth = (int)( drawInfo.stroke.getLineWidth() + 0.5 );
		this.setSize(drawInfo.end.x - drawInfo.start.x + 2*strokeWidth,
				drawInfo.end.y - drawInfo.start.y + 2*strokeWidth);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(getDrawInfo().color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(getDrawInfo().stroke);
		strokeWidth = (int)( getDrawInfo().stroke.getLineWidth() + 0.5 );
		
		switch(getDrawInfo().type) {
		case Rect:
			if (getDrawInfo().fill) {
				if (getDrawInfo().color != getDrawInfo().innerColor) g.setColor(getDrawInfo().innerColor);
				g.fillRect(strokeWidth, strokeWidth,
						getWidth() - 2*strokeWidth,
						getHeight() - 2*strokeWidth);
			}
			g.setColor(getDrawInfo().color);
			g.drawRect(strokeWidth, strokeWidth,
					getWidth() - 2*strokeWidth,
					getHeight() - 2*strokeWidth);
			break;
			
		case Oval:
			if (getDrawInfo().fill) {
				if (getDrawInfo().color != getDrawInfo().innerColor) g.setColor(getDrawInfo().innerColor);
				g.fillOval(strokeWidth, strokeWidth,
						getWidth() - 2*strokeWidth,
						getHeight() - 2*strokeWidth);
			}
			g.setColor(getDrawInfo().color);
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
				resizePanel.setTarget(handle);
				getParent().setComponentZOrder(resizePanel, 0);
				resizePanel.setVisible(true);
				resizePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
				resizePanel.setLocation(handle.getLocation());
				
				anchorPoint = e.getPoint();
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				getParent().setComponentZOrder(handle, 1);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				resizePanel.setLocation(handle.getLocation().x - 7, handle.getLocation().y - 7);
				int anchorX = anchorPoint.x;
				int anchorY = anchorPoint.y;

				Point parentOnScreen = getParent().getLocationOnScreen();
				Point mouseOnScreen = e.getLocationOnScreen();
				Point position = new Point(mouseOnScreen.x - parentOnScreen.x
						- anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);
				setLocation(position);
				drawInfo.start = position;
				drawInfo.end.x = position.x + getWidth();
				drawInfo.end.y = position.y + getHeight();
			}
		});
	}

	public PaintInfo getDrawInfo() { return drawInfo; }
}