package paint;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

public class PaintFrame extends JInternalFrame {
	private static final long serialVersionUID = -8697445633303131846L;
	private final PaintCanvas canvas;
	private final PaintInfo drawInfo;
	private final GlassPanel glass;
	
	public PaintFrame(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo) {
		super("New Image", true, true, true, true);
		this.drawInfo = drawInfo;
		canvas = new PaintCanvas(drawInfo);
		glass = new GlassPanel(drawHistory, drawInfo, canvas);
		
		this.getContentPane().add(canvas);
		this.setGlassPane(glass);
		this.setVisible(true);
		this.setSize(300, 300);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (drawInfo.type == DrawType.Select) glass.setVisible(false);
		else glass.setVisible(true);
	}
}
