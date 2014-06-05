package paint;

//toolpanel01handler
//desktop
//toolpanel05 border

//set font and size
//stroke size setting

//class Component: setBounds
//innerframe, setBackground method
//NORTH toolBasketPanel setSize class Component, setSize()
//class Componenet, getHeight, getWidth

/*************************/
/*      2014 OOP program */
/* dead line: 2014.06.10 */
/*************************/

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.*;

import java.util.ArrayList;

import paint.*;
import paint.canvas.PaintFrame;
import paint.model.DrawType;
import paint.model.PaintInfo;

/////////////////////
/* class MainFrame */
/////////////////////
public class MainFrame extends JFrame
{
	//main() method for test :D
	public static void main(String[] args)
	{
		MainFrame mainFrame = new MainFrame();
		
		mainFrame.setVisible(true);
	}

	/* instance variables */
	private PaintInfo                       paintInfo              = new PaintInfo();
	private ArrayList<ArrayList<PaintInfo>> paintingHistoryManager = new ArrayList<ArrayList<PaintInfo>>();
	
	private MenuBar         menuBar         = new MenuBar();
	private ToolBasketPanel toolBasketPanel = new ToolBasketPanel();
	private Desktop         desktop         = new Desktop();
	
	/* constructor */
	public MainFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
	
		setJMenuBar(menuBar);		
		getContentPane().add(BorderLayout.NORTH, toolBasketPanel);
		getContentPane().add(BorderLayout.CENTER, desktop);
	}
	
	/* methods */
	public JFrame getMainFrame()
	{
		return this;
	}
	
	////////////////////////////////////////
	/* inner class MenuBar from MainFrame */
	////////////////////////////////////////
	private class MenuBar extends JMenuBar
	{
		/* instance variables */
		public JMenu     file = new JMenu("File");
		public JMenuItem save = new JMenuItem("Save");
		public JMenuItem open = new JMenuItem("Open");
		
		public JMenu     color        = new JMenu("Color");
		public JMenuItem colorChooser = new JMenuItem("Color Chooser");
		
		/* constructor */
		public MenuBar()
		{
			file.add(save);
			file.add(open);
			
			color.add(colorChooser);
			
			MenuBarHandler01 menuBarHandler01 = new MenuBarHandler01();
			MenuBarHandler02 menuBarHandler02 = new MenuBarHandler02();
			
			save.addActionListener(menuBarHandler01);
			open.addActionListener(menuBarHandler01);
			
			colorChooser.addActionListener(menuBarHandler02);
			
			add(file);
			add(color);
		}
		
		private class MenuBarHandler01 implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser jFileChooser = new JFileChooser();
			
				if(e.getSource() == save)
				{
					jFileChooser.showSaveDialog(getMainFrame());
				}
				
				if(e.getSource() == open)
				{
					jFileChooser.showOpenDialog(getMainFrame());
				}
			}
		}
		
		private class MenuBarHandler02 implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				JColorChooser jColorChooser = new JColorChooser();
				Color colorChoose = jColorChooser.showDialog(getMainFrame(), "Color Chooser", paintInfo.color);
				
				toolBasketPanel.toolPanel05.getCurrentFocusPanel().setBackground(colorChoose);
				
				if(toolBasketPanel.toolPanel05.getCurrentFocusPanel() == toolBasketPanel.toolPanel05.colorPanel01)
				{
					paintInfo.color = colorChoose;
				}
				
				if(toolBasketPanel.toolPanel05.getCurrentFocusPanel() == toolBasketPanel.toolPanel05.colorPanel02)
				{
					paintInfo.innerColor = colorChoose;
				}
			}
		}
	}
	
	/////////////////////////////////////////////////
	/* inner class ToolBaseketPanel from MainFrame */
	/////////////////////////////////////////////////
	private class ToolBasketPanel extends JPanel
	{
		/* instance variables */
		public ToolPanel01 toolPanel01 = new ToolPanel01();
		public ToolPanel02 toolPanel02 = new ToolPanel02();
		public ToolPanel03 toolPanel03 = new ToolPanel03();
		public ToolPanel04 toolPanel04 = new ToolPanel04();
		public ToolPanel05 toolPanel05 = new ToolPanel05();
		public ToolPanel06 toolPanel06 = new ToolPanel06();
		
		/* constructor */
		public ToolBasketPanel()
		{
			setLayout(new GridLayout(1, 6));
			
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			add(toolPanel01);
			add(toolPanel02);
			add(toolPanel03);
			add(toolPanel04);
			add(toolPanel05);
			add(toolPanel06);
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel01 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel01 extends JPanel
		{
			/* instance variables */
			public JButton newPaintFrame       = new JButton("New Paint Frame");
			public JButton deleteAllPaintFrame = new JButton("Delete All Paint Frame");
			
			/* constructor */
			public ToolPanel01()
			{
				setLayout(new GridLayout(1, 2));
				
				ToolPanel01Handler01 toolPanel01Handler01 = new ToolPanel01Handler01();
				
				newPaintFrame.addActionListener(toolPanel01Handler01);
				add(newPaintFrame);
				
				deleteAllPaintFrame.addActionListener(toolPanel01Handler01);
				add(deleteAllPaintFrame);
			}
			
			private class ToolPanel01Handler01 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == newPaintFrame)
					{
						desktop.addPaintFrame();
					}
					
					if(e.getSource() == deleteAllPaintFrame)
					{
						desktop.deleteAllPaintFrame();
					}
				}
			}
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel02 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel02 extends JPanel
		{
			/* instance variables */
			public JComboBox penSizeSelectBox;
			public String[]  penSizeList;
			
			public JButton   pen    = new JButton("Pen");
			public JButton   select = new JButton("Select");
			public JButton   fill   = new JButton("Fill");
			
			/* constructor */
			public ToolPanel02()
			{
				penSizeList = new String[16];
				for(int i = 0; i < penSizeList.length; i++)
				{
					penSizeList[i] = new String(String.format("%d", i + 1));
				}
				
				penSizeSelectBox = new JComboBox(penSizeList);
				
				penSizeSelectBox.addActionListener(new ToolPanel02Handler01());
				add(penSizeSelectBox);
				
				pen.addActionListener(new ToolPanel02Handler02());
				add(pen);
				select.addActionListener(new ToolPanel02Handler03());
				add(select);
				fill.addActionListener(new ToolPanel02Handler04());
				fill.setBorder(BorderFactory.createRaisedBevelBorder());
				add(fill);
			}
			
			private class ToolPanel02Handler01 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					paintInfo.stroke = new BasicStroke(Integer.parseInt((String)penSizeSelectBox.getSelectedItem()));
				}
			}
			
			private class ToolPanel02Handler02 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					paintInfo.type = DrawType.Pen;
					JInternalFrame[] frms = desktop.getAllFrames();
					for (JInternalFrame f : frms) {
						f.getGlassPane().setVisible(true);
					}
				}
			}
			
			private class ToolPanel02Handler03 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					paintInfo.type = DrawType.Select;
					JInternalFrame[] frms = desktop.getAllFrames();
					for (JInternalFrame f : frms) {
						f.getGlassPane().setVisible(false);
					}
				}
			}
			
			private class ToolPanel02Handler04 implements ActionListener
			{
				int i = 0;
			
				public void actionPerformed(ActionEvent e)
				{
					if(i % 2 == 0)
					{
						paintInfo.fill = true;
						fill.setBorder(BorderFactory.createLoweredBevelBorder());
					}
					else
					{
						paintInfo.fill = false;
						fill.setBorder(BorderFactory.createRaisedBevelBorder());
					}
					
					i++;
				}
			}
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel03 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel03 extends JPanel
		{
			/* instance variables */
			public JButton line      = new JButton("Line");
			public JButton rect      = new JButton("Rect");
			public JButton oval      = new JButton("Oval");
			public JButton roundRect = new JButton("Round Rect");

			/* constructor */
			public ToolPanel03()
			{
				setLayout(new GridLayout(1, 4));
			
				ToolPanel03Handler01 toolPanel03Handler01 = new ToolPanel03Handler01();
				
				line.addActionListener(toolPanel03Handler01);
				add(line);
				
				rect.addActionListener(toolPanel03Handler01);
				add(rect);
				
				oval.addActionListener(toolPanel03Handler01);
				add(oval);
				
				roundRect.addActionListener(toolPanel03Handler01);
				add(roundRect);
			}
			
			private class ToolPanel03Handler01 implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource() == line)					
					{
						paintInfo.type = DrawType.Line;
					}
					
					if(e.getSource() == rect)
					{
						paintInfo.type = DrawType.Rect;
					}
					
					if(e.getSource() == oval)
					{
						paintInfo.type = DrawType.Oval;
					}
					
					if(e.getSource() == roundRect)
					{
						paintInfo.type = DrawType.RoundRect;
					}
					JInternalFrame[] frms = desktop.getAllFrames();
					for (JInternalFrame f : frms) {
						f.getGlassPane().setVisible(true);
					}
				}
			}
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel04 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel04 extends JPanel
		{
			/* instance variables */
			public JButton      string = new JButton("String");
			
			public JComboBox    fontSelectBox;
			public JTextField[] fontList;
			
			public JComboBox    fontSizeSelectBox;
			public String[]     fontSizeList;
			
			/* constructor */
			public ToolPanel04()
			{
				fontList = new JTextField[3];
				fontList[0] = new JTextField("Plain");
				fontList[1] = new JTextField("Bold");
				fontList[2] = new JTextField("Italic");
				fontList[0].setFont(new Font(Font.SERIF, Font.PLAIN, 8));
				fontList[1].setFont(new Font(Font.SERIF, Font.BOLD, 8));
				fontList[2].setFont(new Font(Font.SERIF, Font.ITALIC, 8));
				fontSelectBox = new JComboBox(fontList);
				
				fontSizeList = new String[16];
				for(int i = 0; i < fontSizeList.length; i++)
				{
					fontSizeList[i] = new String(String.format("%d", i + 8));
				}
				fontSizeSelectBox = new JComboBox(fontSizeList);
			
				add(string);
				add(fontSelectBox);
				add(fontSizeSelectBox);
			}
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel05 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel05 extends JPanel
		{
			/* instance variables */
			public JPanel colorPanel01 = new JPanel();
			public JPanel colorPanel02 = new JPanel();
			
			private JPanel currentFocusPanel;

			/* constructor */
			public ToolPanel05()
			{
				setLayout(new GridLayout(1, 2));
				
				colorPanel01.setBackground(Color.BLACK);
				colorPanel02.setBackground(Color.WHITE);
				colorPanel01.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
				colorPanel02.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
				setCurrentFocusPanel(colorPanel01);
				
				ToolPanel05Handler01 toolPanel05Handler01 = new ToolPanel05Handler01();
				
				colorPanel01.addMouseListener(toolPanel05Handler01);
				colorPanel02.addMouseListener(toolPanel05Handler01);
				
				add(colorPanel01);
				add(colorPanel02);
			}
			
			/* methods */
			public void setCurrentFocusPanel(JPanel currentFocusPanel)
			{
				this.currentFocusPanel = currentFocusPanel;
			}
			public JPanel getCurrentFocusPanel()
			{
				return currentFocusPanel;
			}
			
			private class ToolPanel05Handler01 extends MouseAdapter
			{
				public void mousePressed(MouseEvent e)
				{
					if(e.getSource() == colorPanel01)
					{
						setCurrentFocusPanel(colorPanel01);
						
						colorPanel01.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
						colorPanel02.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}
					
					if(e.getSource() == colorPanel02)
					{
						setCurrentFocusPanel(colorPanel02);
						
						colorPanel02.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
						colorPanel01.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					}
				}
			}
		}
		
		/////////////////////////////////////////////////
		/* inner class ToolPanel06 from ToolBasketPanel*/
		/////////////////////////////////////////////////
		private class ToolPanel06 extends JPanel
		{
			/* instance variables */
			public Color[]  basicColors = {Color.WHITE, Color.WHITE, Color.BLACK, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.RED, Color.ORANGE, Color.PINK, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};
			public JPanel[] basicColorPanels;
			
			/* constructor */
			public ToolPanel06()
			{
				setLayout(new GridLayout(2, 7));
				
				basicColorPanels = new JPanel[basicColors.length];
			
				ToolPanel06Handler01 toolPanel06Handler01 = new ToolPanel06Handler01();
			
				for(int i = 0; i < basicColorPanels.length; i++)
				{
					basicColorPanels[i] = new JPanel();
					basicColorPanels[i].setBorder(BorderFactory.createLoweredBevelBorder());
					basicColorPanels[i].setBackground(basicColors[i]);
					basicColorPanels[i].addMouseListener(toolPanel06Handler01);
					add(basicColorPanels[i]);
				}
			}
			
			private class ToolPanel06Handler01 extends MouseAdapter
			{
				public void mousePressed(MouseEvent e)
				{
					for(int i = 0; i < basicColorPanels.length; i++)
					{
						if(e.getSource() == basicColorPanels[i])
						{
							toolPanel05.getCurrentFocusPanel().setBackground(basicColors[i]);
							
							if(toolPanel05.getCurrentFocusPanel() == toolPanel05.colorPanel01)
							{
								paintInfo.color = basicColors[i];
							}
							
							if(toolPanel05.getCurrentFocusPanel() == toolPanel05.colorPanel02)
							{
								paintInfo.innerColor = basicColors[i];
							}
						}
					}
				}
			}
		}
	}
	
	////////////////////////////////////////
	/* inner class Desktop from MainFrame */
	////////////////////////////////////////
	private class Desktop extends JDesktopPane
	{
		/* instance variables */
		public ArrayList<PaintFrame> paintFrameList = new ArrayList<PaintFrame>();
		
		private int popX;
		private int popY;
		
		/* constructor */
		public Desktop()
		{
			setBackground(Color.LIGHT_GRAY);
		}
		
		/* methods */
		public void addPaintFrame()
		{
			ArrayList<PaintInfo> temp = new ArrayList<PaintInfo>();
			paintingHistoryManager.add(temp);
						
			PaintFrame temp2 = new PaintFrame(temp, paintInfo);
			paintFrameList.add(temp2);
			
			temp2.setBounds(popX, popY, getMainFrame().getWidth() / 2, getMainFrame().getHeight() / 2);
			popX = (popX + 30) % 300;
			popY = (popY + 30) % 300;
			
			temp2.getGlassPane().setVisible(true);
			add(paintFrameList.get(paintFrameList.indexOf(temp2)));
		}
		
		public void deleteAllPaintFrame()
		{
			JInternalFrame[] targets = getAllFrames();
						
			for(JInternalFrame target : targets)
			{
				target.dispose();
			}
						
			paintingHistoryManager.clear();
			
			popX = 0;
			popY = 0;
		}
	}
}