package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ColorSelectPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = -5692867526799936540L;
	private final PaintInfo info;
	private Color [] arr = { Color.BLACK, Color.WHITE, Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN
			, Color.BLUE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.CYAN, Color.MAGENTA, Color.WHITE };
	private IndividualColorBox customColor = null;
	
	public ColorSelectPanel(PaintInfo info) {
		this.info = info;

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Box colorBoxPanel = Box.createVerticalBox();
		Box upBox = Box.createHorizontalBox();
		Box underBox = Box.createHorizontalBox();
		
		this.add(Box.createHorizontalStrut(50));
		this.add(colorBoxPanel);
		
		colorBoxPanel.add(upBox);
		colorBoxPanel.add(Box.createVerticalStrut(5));
		colorBoxPanel.add(underBox);
		
		for (int i = 0, len = arr.length; i < len; i++) {
			IndividualColorBox colorBox = new IndividualColorBox(arr[i]);
			if (i == len - 1) customColor = colorBox;
			colorBox.addMouseListener(this);
			
			(i >= len / 2 ? upBox : underBox).add(Box.createHorizontalStrut(10));
			(i >= len / 2 ? upBox : underBox).add(colorBox);
		}
		upBox.add(Box.createHorizontalStrut(10));
		underBox.add(Box.createHorizontalStrut(10));
		
		JButton moreColorBtn = new JButton("More");
		moreColorBtn.setSize(60, 60);
		moreColorBtn.addActionListener(this);
		this.add(moreColorBtn);
		this.setPreferredSize(new Dimension(310, 60));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color tmp = g.getColor();
		
		g.setColor(info.color);
		g.fillRect(10, 0, 40, 40);
		g.setColor(tmp);
		g.drawRect(10, 0, 40, 40);
		
		g.setColor(info.innerColor);
		g.fillRect(0, 10, 40, 40);
		g.setColor(tmp);
		g.drawRect(0, 10, 40, 40);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		arr[13] = JColorChooser.showDialog(null, "Choose Drawing Color", arr[13]);
		customColor.setColor(arr[13]);
		customColor.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Color selectedColor = ((IndividualColorBox)e.getSource()).getColor();
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			info.color = selectedColor;
			break;
		case MouseEvent.BUTTON3:
			info.innerColor = selectedColor;
			break;
		}
		
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}


class IndividualColorBox extends JComponent {
	private static final long serialVersionUID = -5692867526799936540L;
	private Color color = null;
	
	public IndividualColorBox(Color color) { this.color = color; }
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setSize(new Dimension(20, 20));
		g.setColor(color);
		g.fillRect(0, 0, 19, 19);
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
}