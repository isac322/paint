package paint.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;

public class PaintInfo {
	public Point start = new Point(-10, -10);
	public Point end = new Point(-10, -10);
	public DrawType type = DrawType.Pen;
	public Color color = Color.BLACK;
	public Color innerColor = Color.WHITE;
	public boolean clickState = false;
	public boolean draggState = false;
	public boolean fill = false;
	public int fontSize;
	public BasicStroke stroke = new BasicStroke(1);
	
	public PaintInfo() {}
	public PaintInfo(PaintInfo info) {
		this.start = info.start;
		this.end = info.end;
		this.type = info.type;
		this.color = info.color;
		this.innerColor = info.innerColor;
		this.draggState = info.draggState;
		this.fill = info.fill;
		this.fontSize = info.fontSize;
		this.stroke = info.stroke;
	}
}