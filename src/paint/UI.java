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

import paint.canvas.PaintFrame;
import paint.model.DrawType;
import paint.model.PaintInfo;

public class UI {
	public static void main(String[] args) {
		final ArrayList<ArrayList<PaintInfo>> lists = new ArrayList<ArrayList<PaintInfo>>();
		final PaintInfo drawInfo = new PaintInfo();
		
		JFrame frm = new JFrame("Paint");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setSize(650, 600);
		final JDesktopPane desktop = new JDesktopPane();

		
		JPanel panel = new JPanel();
		String [] list = { "Pen", "Rect", "Oval", "Line", "Select" };
		JComboBox<String> box = new JComboBox<String>(list);
		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("unchecked")
				String item = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
		
				switch (item) {
				case "Pen" : drawInfo.type = DrawType.Pen; break;
				case "Rect" : drawInfo.type = DrawType.Rect; break;
				case "Oval" : drawInfo.type = DrawType.Oval; break;
				case "Line" : drawInfo.type = DrawType.Line; break;
				}
		
				JInternalFrame[] frms = desktop.getAllFrames();
				for (JInternalFrame f : frms) {
					f.getGlassPane().setVisible(item.equals("Select") ? false : true);
				}
			}
		});
		
		String [] strokeList = { "1", "2", "3", "4", "5", "6" };
		JComboBox<String> strokeBox = new JComboBox<String>(strokeList);
		strokeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				@SuppressWarnings("unchecked")
				String item = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
		
				switch (item) {
				case "1" : drawInfo.stroke = new BasicStroke(1); break;
				case "2" : drawInfo.stroke = new BasicStroke(2); break;
				case "3" : drawInfo.stroke = new BasicStroke(3); break;
				case "4" : drawInfo.stroke = new BasicStroke(4); break;
				case "5" : drawInfo.stroke = new BasicStroke(5); break;
				case "6" : drawInfo.stroke = new BasicStroke(6); break;
				}
			}
		});
		
		JCheckBox check = new JCheckBox("fill", false);
		check.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				drawInfo.fill = ((JCheckBox)e.getSource()).isSelected();
			}
		});
		
		JButton newFrameBtn = new JButton("New Window");
		newFrameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<PaintInfo> layout = new ArrayList<PaintInfo>();
				lists.add(layout);
				PaintFrame frm = new PaintFrame(layout, drawInfo);
				frm.getGlassPane().setVisible(true);
				desktop.add(frm);
			}
		});
		
		panel.add(new ColorSelectPanel(drawInfo));
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

