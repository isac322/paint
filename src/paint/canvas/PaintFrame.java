package paint.canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import paint.canvas.resize.ResizePanel;
import paint.model.PaintInfo;

/**
 * <i>JDesktopPane</i>에 들어갈 하나의 창
 * @author 병훈
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
		//overlay.setOpaque(false);
		//overlay.setLayout(new OverlayLayout(overlay));
		//overlay.setPreferredSize(new Dimension(width, height));
		
		//this.getContentPane().setLayout(null);
		//this.add(overlay, BorderLayout.CENTER);
		//this.setContentPane(overlay);
		
		this.getContentPane().setLayout(null);
		
		this.drawInfo = drawInfo;
		resizePanel = new ResizePanel();
		canvas = new PaintCanvas(drawInfo, resizePanel);
		glass = new GlassPanel(drawHistory, drawInfo, canvas);
		
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
		this.add(canvas);
		JButton btn = new JButton("a");
		btn.setBounds(10, 10, 60, 60);
		canvas.add(btn);
		this.setGlassPane(glass);
		this.setVisible(true);
		this.getContentPane().setComponentZOrder(resizePanel, 0);
	}
	
	/**
	 * resize panel을 사라지게한다.
	 */
	public void disableResizePane() { resizePanel.setVisible(false); }
	
	
	/**
	 * 프레임에 저장된 레이어 캔버스 리스트를 반환한다.
	 * @return 프레임에 저장된 레이어 캔버스 리스트
	 */
	public ArrayList<PaintCanvas> getLayoutList() { return layout; }
	
	
	/**
	 * 그림 그릴 캔버스를 레이어 리스트에서 <i>index</i>에 해당하는 캔버스로 바꾼다.
	 * @param index 레이어 리스트에서의 인덱스
	 */
	public void setTargetCanvas(int index) { 
		//System.out.println(layout.get(index));
		glass.setTargetCanvas(layout.get(index));
		this.getContentPane().setComponentZOrder(layout.get(index), 1);
	}
	
	
	/**
	 * 지금 프레임에 레이어 캔버스를 추가한다.
	 */
	public void addLayout() {
		PaintCanvas newCanvas = new PaintCanvas(drawInfo, resizePanel);
		layout.add(newCanvas);
		this.add(newCanvas);
		this.getContentPane().validate();
	}
}