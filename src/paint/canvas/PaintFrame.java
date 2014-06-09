package paint.canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import paint.canvas.resize.ResizePanel;
import paint.model.PaintInfo;

/**
 * <i>JDesktopPane</i>�� �� �ϳ��� â
 * @author ����
 */

public class PaintFrame extends JInternalFrame implements Serializable {
	private static final long serialVersionUID = -8697445633303131846L;
	private final ResizePanel resizePanel;
	private final PaintCanvas canvas;
	private final PaintInfo drawInfo;
	private final GlassPanel glass;
	private final JPanel overlay;
	private final ArrayList<PaintCanvas> layout = new ArrayList<PaintCanvas>();
	
	public PaintFrame(ArrayList<PaintInfo> drawHistory, PaintInfo drawInfo, int width, int height) {
		super("New Image", true, true, true, true);
		
		overlay = new JPanel() {
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		overlay.setOpaque(false);
		overlay.setLayout(new OverlayLayout(overlay));
		overlay.setPreferredSize(new Dimension(width, height));
		
		this.setLayout(new BorderLayout());
		this.add(overlay, BorderLayout.CENTER);
		
		//this.getContentPane().setLayout(new OverlayLayout(this.getContentPane()));
		this.drawInfo = drawInfo;
		resizePanel = new ResizePanel();
		canvas = new PaintCanvas(drawInfo, resizePanel);
		glass = new GlassPanel(drawHistory, drawInfo, canvas);
		
		//resizePanel.setBounds(10, 10, 100, 100);
		resizePanel.setVisible(false);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (resizePanel.isVisible()) {
					resizePanel.setVisible(false);
				}
			}
		});
		
		layout.add(canvas);
		overlay.add(canvas);
		this.add(resizePanel);
		this.setGlassPane(glass);
		this.setVisible(true);
	}
	
	/**
	 * resize panel�� ��������Ѵ�.
	 */
	public void disableResizePane() { resizePanel.setVisible(false); }
	
	/**
	 * �����ӿ� ����� ���̾� ĵ���� ����Ʈ�� ��ȯ�Ѵ�.
	 * @return �����ӿ� ����� ���̾� ĵ���� ����Ʈ
	 */
	public ArrayList<PaintCanvas> getLayoutList() { return layout; }
	
	/**
	 * �׸� �׸� ĵ������ ���̾� ����Ʈ���� <i>index</i>�� �ش��ϴ� ĵ������ �ٲ۴�.
	 * @param index ���̾� ����Ʈ������ �ε���
	 */
	public void setTargetCanvas(int index) { 
		System.out.println(layout.get(index));
		glass.setTargetCanvas(layout.get(index));
	}
	
	/**
	 * ���� �����ӿ� ���̾� ĵ������ �߰��Ѵ�.
	 */
	public void addLayout() {
		PaintCanvas newCanvas = new PaintCanvas(drawInfo, resizePanel);
		layout.add(newCanvas);
		overlay.add(newCanvas);
		//for (int i = 0; i < layout.size(); i++) {
		//	overlay.setComponentZOrder(layout.get(i), 0);
		//}
		//overlay.setComponentZOrder(resizePanel, 0);
	}
}