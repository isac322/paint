package paint.canvas;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComponent;

import paint.canvas.resize.ResizePanel;
import paint.model.PaintInfo;

public class ObjectiveShape extends JComponent implements Serializable {
	private static final long serialVersionUID = 7209900239540363764L;
	private final PaintInfo drawInfo;
	private final ResizePanel resizePanel;
	private Point anchorPoint;
	private int strokeWidth = 0;
	
	public ObjectiveShape(PaintInfo drawInfo, ResizePanel resizePanel) {
		super();
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

		g.setColor(drawInfo.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(drawInfo.stroke);
		strokeWidth = (int)( drawInfo.stroke.getLineWidth() + 0.5 );
		
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
			
		case RoundRect:
			if (drawInfo.fill) {
				if (drawInfo.color != drawInfo.innerColor) g.setColor(drawInfo.innerColor);
				g.fillRoundRect(strokeWidth, strokeWidth,
						getWidth() - 2*strokeWidth,
						getHeight() - 2*strokeWidth, 50, 50);
			}
			g.setColor(drawInfo.color);
			g.drawRoundRect(strokeWidth, strokeWidth,
					getWidth() - 2*strokeWidth,
					getHeight() - 2*strokeWidth, 50, 50);
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
				resizePanel.setVisible(true);
				resizePanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
				resizePanel.setLocation(handle.getLocation());
				
				anchorPoint = e.getPoint();
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				getParent().setComponentZOrder(handle, 0);
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