package paint.canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

import paint.canvas.resize.ResizePanel;
import paint.model.PaintInfo;

public class PaintFrame extends JInternalFrame {
	private static final long serialVersionUID = -8697445633303131846L;
	private final ResizePanel resizePanel;
	private final PaintCanvas canvas;
	private final PaintInfo drawInfo;
	private final GlassPanel glass;
	
	public PaintFrame(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo) {
		super("New Image", true, true, true, true);
		this.drawInfo = drawInfo;
		resizePanel = new ResizePanel();
		canvas = new PaintCanvas(drawInfo, resizePanel);
		glass = new GlassPanel(drawHistory, drawInfo, canvas);
		
		resizePanel.setBounds(10, 10, 100, 100);
		this.add(resizePanel);
		resizePanel.setVisible(false);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (resizePanel.isVisible()) {
					resizePanel.setVisible(false);
				}
			}
		});
		
		this.add(canvas);
		this.setGlassPane(glass);
		this.setVisible(true);
		this.setSize(300, 300);
	}
	
	public void disableResizePane() { resizePanel.setVisible(false); }
}
