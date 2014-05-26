package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -6029427500054621506L;
	private final ArrayList<PaintInfo> drawHistory;
	private final PaintInfo drawInfo;
	private JPanel canvas = new JPanel();
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
		
		canvas.setOpaque(false);
		canvas.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		canvas.setBackground(Color.RED);
		this.add(canvas);
		canvas.setLocation(0, 0);
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
			canvas.setLocation(0, 0);
			canvas.setSize(new Dimension(width, height));
		}

		g2.drawImage(bufferImage, null, 0, 0);
		if (drawInfo.type == DrawType.Pen) paintShape(bufferGraphics);
		else if (drawInfo.draggState) paintShape(g);
	}
	
	void paintShape(Graphics g) {
		g.setColor(drawInfo.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(drawInfo.stroke);
		
		int startX = drawInfo.start.x;
		int startY = drawInfo.start.y;
		int width = drawInfo.end.x - drawInfo.start.x;
		int height = drawInfo.end.y - drawInfo.start.y;
		if (drawInfo.end.x < drawInfo.start.x) {
			startX = drawInfo.end.x;
			width = drawInfo.start.x - drawInfo.end.x;
		}
		if (drawInfo.end.y < drawInfo.start.y) {
			startY = drawInfo.end.y;
			height = drawInfo.start.y - drawInfo.end.y;
		}
		
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
		drawInfo.end = drawInfo.start = e.getPoint();
		drawInfo.draggState = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drawInfo.end = e.getPoint();
		if (drawInfo.draggState) {
			drawInfo.draggState = false;
			
			//this.paintShape(bufferGraphics);
			PaintInfo tmp = new PaintInfo(drawInfo);
			drawHistory.add(tmp);
			ObjectiveShape Shape = new ObjectiveShape(tmp);
			Shape.setLocation(Math.min(drawInfo.start.x, drawInfo.end.x), Math.min(drawInfo.start.y, drawInfo.end.y));
			this.add(Shape);
			canvas.getParent().setComponentZOrder(canvas, 0);
			//this.repaint();
		}
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