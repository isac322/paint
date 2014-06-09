package paint.canvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import paint.model.DrawType;
import paint.model.PaintInfo;

/**
 * 마우스를 인식해서  drawInfo에 기록하고, 선택된 레이어에 선택된 그림을 그리도록 호출한다.
 * @author 병훈
 */

public class GlassPanel extends JPanel implements MouseListener, MouseMotionListener, Serializable {
	private static final long serialVersionUID = -3513705979529257202L;
	private final ArrayList<PaintInfo> drawHistory;
	private PaintCanvas canvas;
	private final PaintInfo drawInfo;
	
	public GlassPanel(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo, PaintCanvas canvas) {
		this.setOpaque(false);
		this.drawInfo = drawInfo;
		this.drawHistory = drawHistory;
		this.canvas = canvas;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	/**
	 * 그림 그릴 레이어 캔버스를 바꾼다.
	 * @param canvas 그림 그릴 레이어 캔버스
	 */
	public void setTargetCanvas(PaintCanvas canvas) { this.canvas = canvas; }
	
	@Override
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		
		if (flag) this.getParent().setComponentZOrder(this, 0);
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
		drawInfo.draggState = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drawInfo.end = e.getPoint();
		if (drawInfo.draggState) {
			drawInfo.draggState = false;
			
			if (drawInfo.type == DrawType.Pen || drawInfo.type == DrawType.Line) {
				canvas.paintShape(canvas.getBufferGraphics());
			} else {
				if (drawInfo.start.x > drawInfo.end.x) {
					int tmp = drawInfo.end.x;
					drawInfo.end.x = drawInfo.start.x;
					drawInfo.start.x = tmp;
				}
				if (drawInfo.start.y > drawInfo.end.y) {
					int tmp = drawInfo.end.y;
					drawInfo.end.y = drawInfo.start.y;
					drawInfo.start.y = tmp;
				}
				PaintInfo tmp = new PaintInfo(drawInfo);
				drawHistory.add(tmp);
				ObjectiveShape Shape = new ObjectiveShape(tmp, canvas.getResizePanel());
				
				Shape.setLocation(drawInfo.start.x - (int)(drawInfo.stroke.getLineWidth() + 0.5),
						drawInfo.start.y - (int)(drawInfo.stroke.getLineWidth() + 0.5));
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
