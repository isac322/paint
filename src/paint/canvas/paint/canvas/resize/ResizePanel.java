package paint.canvas.paint.canvas.resize;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import paint.canvas.ObjectiveShape;

public class ResizePanel extends JComponent {
	private static final long serialVersionUID = 7016269154585604118L;
	private ResizeButton NW = new ResizeNW(), NE = new ResizeNE(), N = new ResizeN(),
			SW = new ResizeSW(), SE = new ResizeSE(), S = new ResizeS(), W = new ResizeW(), E = new ResizeE();
	private ObjectiveShape Shape;
	
	public ResizePanel() {
		this.setLayout(null);
		this.setOpaque(false);
		
		this.add(SE);
		this.add(E);
		this.add(S);
		this.add(NW);
		this.add(NE);
		this.add(N);
		this.add(SW);
		this.add(W);
	}
	
	public void setTarget(ObjectiveShape target) {
		Shape = target;
		SE.setTarget(target);
		E.setTarget(target);
		S.setTarget(target);
		NW.setTarget(target);
		NE.setTarget(target);
		N.setTarget(target);
		SW.setTarget(target);
		W.setTarget(target);
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Rectangle tmpShape = Shape.getBounds();
		tmpShape.width += NW.getWidth() - 2;
		tmpShape.height += NW.getHeight() - 2;
		tmpShape.x -= NW.getWidth()/2 - 1;
		tmpShape.y -= NW.getHeight()/2 - 1;
		this.setBounds(tmpShape);
		
		
		NW.setLocation(0, 0);
		W.setLocation(0, getHeight()/2 - W.getHeight()/2);
		SW.setLocation(0, getHeight() - SW.getHeight());
		N.setLocation(getWidth()/2 - N.getWidth()/2, 0);
		
		S.setLocation(getWidth()/2 - S.getWidth()/2,
				getHeight() - S.getHeight());
		NE.setLocation(getWidth() - NE.getWidth(), 0);
		E.setLocation(getWidth() - E.getWidth(),
				getHeight()/2 - E.getHeight()/2);
		SE.setLocation(getWidth() - SE.getWidth(),
				getHeight() - SE.getHeight());
	}
}