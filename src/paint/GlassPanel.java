package paint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GlassPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -3513705979529257202L;
	private final PaintCanvas canvas;
	private final ArrayList<PaintInfo> drawHistory;
	private final PaintInfo drawInfo;
	
	public GlassPanel(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo, PaintCanvas canvas) {
		this.setOpaque(false);
		this.drawInfo = drawInfo;
		this.drawHistory = drawHistory;
		this.canvas = canvas;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
		drawInfo.clickState = true;
		drawInfo.end = drawInfo.start = e.getPoint();
		//drawInfo.draggState = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drawInfo.end = e.getPoint();
		if (drawInfo.draggState) {
			drawInfo.draggState = false;
			
			if (drawInfo.type == DrawType.Pen || drawInfo.type == DrawType.Line) {
				canvas.paintShape(canvas.getBufferGraphics());
			} else {
				PaintInfo tmp = new PaintInfo(drawInfo);
				drawHistory.add(tmp);
				ObjectiveShape Shape = new ObjectiveShape(tmp);
				Shape.setLocation(Math.min(drawInfo.start.x, drawInfo.end.x), Math.min(drawInfo.start.y, drawInfo.end.y));
				canvas.add(Shape);
				canvas.setComponentZOrder(Shape, 0);
				canvas.repaint();
			}
		}
		drawInfo.clickState = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawInfo.type == DrawType.Pen) {
			drawInfo.start = drawInfo.end;
		}
		drawInfo.end = e.getPoint();
		drawInfo.draggState = true;
		canvas.repaint();
	}
}
