package paint;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class UI {
	public static void main(String[] args) {
		final ArrayList<ArrayList<PaintInfo>> lists = new ArrayList<ArrayList<PaintInfo>>();
		
		JFrame frm = new JFrame("Paint");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setSize(650, 600);
		final JDesktopPane desktop = new JDesktopPane();

		final PaintInfo info = new PaintInfo();
		
		JPanel panel = new JPanel();
		String [] list = { "Pen", "Rect", "Oval", "Line", "Select" };
		JComboBox<String> box = new JComboBox<String>(list);
		ComboListener listener = new ComboListener(info);
		box.addItemListener(listener);
		
		String [] strokeList = { "1", "2", "3", "4", "5", "6" };
		JComboBox<String> strokeBox = new JComboBox<String>(strokeList);
		strokeBox.addItemListener(listener);
		
		JCheckBox check = new JCheckBox("fill", false);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				info.fill = ((JCheckBox)e.getSource()).isSelected();
			}
		});
		
		JButton newFrameBtn = new JButton("New Window");
		newFrameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JInternalFrame frm = new JInternalFrame("New Image", true, true, true, true);
				ArrayList<PaintInfo> layout = new ArrayList<PaintInfo>();
				lists.add(layout);
				frm.add(new PaintCanvas(layout, info));
				frm.setVisible(true);
				frm.setSize(300, 300);
				desktop.add(frm, BorderLayout.CENTER);
			}
		});
		
		panel.add(new ColorSelectPanel(info));
		panel.add(box);
		panel.add(strokeBox);
		panel.add(check);
		panel.add(newFrameBtn);
		newFrameBtn.doClick();
		
		frm.setLayout(new BorderLayout());
		frm.add(panel, BorderLayout.NORTH);
		frm.add(desktop, BorderLayout.CENTER);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}
}

class ComboListener implements ItemListener {
	private PaintInfo info = null;
	
	public ComboListener(PaintInfo info) {
		this.info = info;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void itemStateChanged(ItemEvent e) {
		String item = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
		
		switch (item) {
		case "Pen" : info.type = DrawType.Pen; break;
		case "Rect" : info.type = DrawType.Rect; break;
		case "Oval" : info.type = DrawType.Oval; break;
		case "Line" : info.type = DrawType.Line; break;
		case "Select" : info.type = DrawType.Select; break;
		case "1" : info.stroke = new BasicStroke(1); break;
		case "2" : info.stroke = new BasicStroke(2); break;
		case "3" : info.stroke = new BasicStroke(3); break;
		case "4" : info.stroke = new BasicStroke(4); break;
		case "5" : info.stroke = new BasicStroke(5); break;
		case "6" : info.stroke = new BasicStroke(6); break;
		}
	}
}