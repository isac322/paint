package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 5102320731739599274L;
	private final ArrayList<PaintInfo> drawHistory;
	private final PaintInfo drawInfo;
	private boolean clicked = false;
	private BufferedImage bufferImage = null;
	private Graphics2D bufferGraphics = null;
	private int prevWidth = 400;
	private int prevHeight = 400;
	
	public PaintCanvas(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo) {
		this.setSize(prevWidth, prevHeight);
		this.drawHistory = drawHistory;
		this.drawInfo = drawInfo;
		this.setOpaque(false);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		int width = this.getWidth();
		int height = this.getHeight();
		if (width != prevWidth || height != prevHeight) {
			BufferedImage tmp = bufferImage;
			bufferImage = (BufferedImage)this.createImage(width, height);
			bufferGraphics = (Graphics2D)bufferImage.getGraphics();
			bufferGraphics.drawImage(tmp, null, 0, 0);
			tmp = null;
			System.gc();
			prevWidth = width;
			prevHeight = height;
		}
		
		g2.drawImage(bufferImage, null, 0, 0);
		if (clicked) {
			if (drawInfo.type == DrawType.Pen) paintShape(bufferGraphics);
			else if (drawInfo.draggState) paintShape(g);
		}
	}
	
	void paintShape(Graphics g) {
		g.setColor(drawInfo.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(drawInfo.stroke);
		
		int startX = Math.min(drawInfo.start.x, drawInfo.end.x);
		int startY = Math.min(drawInfo.start.y, drawInfo.end.y);
		int width = Math.abs(drawInfo.end.x - drawInfo.start.x);
		int height = Math.abs(drawInfo.end.y - drawInfo.start.y);
		
		switch(drawInfo.type) {
		case Rect:
			if (drawInfo.fill) {
				if (drawInfo.color != drawInfo.innerColor) g.setColor(drawInfo.innerColor);
				g.fillRect(startX, startY, width, height);
			}
			g.setColor(drawInfo.color);
			g.drawRect(startX, startY, width, height);
			break;
			
		case Oval:
			if (drawInfo.fill) {
				if (drawInfo.color != drawInfo.innerColor) g.setColor(drawInfo.innerColor);
				g.fillOval(startX, startY, width, height);
			}
			g.setColor(drawInfo.color);
			g.drawOval(startX, startY, width, height);
			break;
			
		case Pen :
		case Line :
			g.drawLine(drawInfo.start.x, drawInfo.start.y, drawInfo.end.x, drawInfo.end.y);
			break;
			
		default:
			System.exit(1);
			break;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
		drawInfo.end = drawInfo.start = e.getPoint();
		drawInfo.draggState = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drawInfo.end = e.getPoint();
		if (drawInfo.draggState) {
			drawInfo.draggState = false;
			
			if (drawInfo.type == DrawType.Pen || drawInfo.type == DrawType.Line) {
				paintShape(bufferGraphics);
			} else {
				PaintInfo tmp = new PaintInfo(drawInfo);
				drawHistory.add(tmp);
				ObjectiveShape Shape = new ObjectiveShape(tmp);
				Shape.setLocation(Math.min(drawInfo.start.x, drawInfo.end.x), Math.min(drawInfo.start.y, drawInfo.end.y));
				this.add(Shape);
				this.setComponentZOrder(Shape, 0);
			}
		}
		clicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawInfo.type == DrawType.Pen) {
			drawInfo.start = drawInfo.end;
		}
		drawInfo.end = e.getPoint();
		drawInfo.draggState = true;
		this.repaint();
	}
}