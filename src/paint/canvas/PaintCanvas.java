package paint.canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import paint.canvas.paint.canvas.resize.ResizePanel;
import paint.model.DrawType;
import paint.model.PaintInfo;

public class PaintCanvas extends JPanel {
	private static final long serialVersionUID = 5102320731739599274L;
	private final ResizePanel resizePanel;
	private final PaintInfo drawInfo;
	private BufferedImage bufferImage = null;
	private Graphics2D bufferGraphics = null;
	private int prevWidth = 400;
	private int prevHeight = 400;
	
	public PaintCanvas(PaintInfo drawInfo) {
		this.drawInfo = drawInfo;
		this.setLayout(null);
		this.setSize(prevWidth, prevHeight);
		
		this.resizePanel = new ResizePanel();
		resizePanel.setBounds(10, 10, 100, 100);
		resizePanel.setVisible(false);
		this.add(resizePanel);
		
		this.setOpaque(false);
		this.setBackground(Color.WHITE);
	}
	
	public Graphics2D getBufferGraphics() { return this.bufferGraphics; }
	public ResizePanel getResizePanel() { return resizePanel; }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(10, 10, 50, 50);
		Graphics2D g2 = (Graphics2D) g;
		
		int width = this.getWidth();
		int height = this.getHeight();
		if (width != prevWidth || height != prevHeight) {
			BufferedImage tmp = bufferImage;
			bufferImage = (BufferedImage)this.createImage(width, height);
			bufferGraphics = (Graphics2D)bufferImage.getGraphics();
			bufferGraphics.drawImage(tmp, null, 0, 0);
			tmp = null;
			prevWidth = width;
			prevHeight = height;
		}
		
		g2.drawImage(bufferImage, null, 0, 0);
		if (drawInfo.clickState) {
			if (drawInfo.type == DrawType.Pen) paintShape(bufferGraphics);
			else if (drawInfo.draggState) paintShape(g);
		}
	}
	
	public void paintShape(Graphics g) {
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
}