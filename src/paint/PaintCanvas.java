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
	private ArrayList<PaintInfo> symbols = null;
	private PaintInfo info = null;
	private JPanel canvas = new JPanel();
	private BufferedImage bufferedImage = null;
	private Graphics2D bufferedGraphics = null;
	private int prevWidth = 400;
	private int prevHeight = 400;
	
	public PaintCanvas(ArrayList<PaintInfo> symbols, PaintInfo info) {
		//this.setLayout(null);
		this.setSize(prevWidth, prevHeight);
		this.symbols = symbols;
		this.info = info;
		this.symbols.add(this.info);
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
			BufferedImage tmp = bufferedImage;
			bufferedImage = (BufferedImage)this.createImage(width, height);
			bufferedGraphics = (Graphics2D)bufferedImage.getGraphics();
			bufferedGraphics.drawImage(tmp, null, 0, 0);
			tmp = null;
			System.gc();
			prevWidth = width;
			prevHeight = height;
			canvas.setLocation(0, 0);
			canvas.setSize(new Dimension(width, height));
		}

		g2.drawImage(bufferedImage, null, 0, 0);
		if (info.type == DrawType.Pen) paintShape(bufferedGraphics);
		else if (info.draggState) paintShape(g);
	}
	
	void paintShape(Graphics g) {
		g.setColor(info.color);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(info.stroke);
		
		int startX = info.start.x;
		int startY = info.start.y;
		int width = info.end.x - info.start.x;
		int height = info.end.y - info.start.y;
		if (info.end.x < info.start.x) {
			startX = info.end.x;
			width = info.start.x - info.end.x;
		}
		if (info.end.y < info.start.y) {
			startY = info.end.y;
			height = info.start.y - info.end.y;
		}
		
		switch(info.type) {
		case Rect:
			if (info.fill) {
				if (info.color != info.innerColor) g.setColor(info.innerColor);
				g.fillRect(startX, startY, width, height);
			}
			g.setColor(info.color);
			g.drawRect(startX, startY, width, height);
			break;
			
		case Oval:
			if (info.fill) {
				if (info.color != info.innerColor) g.setColor(info.innerColor);
				g.fillOval(startX, startY, width, height);
			}
			g.setColor(info.color);
			g.drawOval(startX, startY, width, height);
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
		info.end = info.start = e.getPoint();
		info.draggState = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		info.end = e.getPoint();
		if (info.draggState) {
			info.draggState = false;
			
			//this.paintShape(bufferedGraphics);
			PaintInfo tmp = new PaintInfo(info);
			symbols.add(tmp);
			ShapeObject Shape = new ShapeObject(tmp);
			Shape.setLocation(Math.min(info.start.x, info.end.x), Math.min(info.start.y, info.end.y));
			this.add(Shape);
			canvas.getParent().setComponentZOrder(canvas, 0);
			//this.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (info.type == DrawType.Pen) {
			info.start = info.end;
		}
		info.end = e.getPoint();
		info.draggState = true;
		this.repaint();
	}
}