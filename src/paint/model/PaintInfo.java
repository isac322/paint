package paint.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class PaintInfo implements Serializable {
	public Point start = new Point(-10, -10);
	public Point end = new Point(-10, -10);
	public DrawType type = DrawType.Pen;
	public Color color = Color.BLACK;
	public Color innerColor = Color.WHITE;
	public boolean clickState = false;
	public boolean dragState = false;
	public boolean fill = false;
	public int fontSize;
	transient public BasicStroke stroke = new BasicStroke(1);
	
	public PaintInfo() {}
	public PaintInfo(PaintInfo info) {
		this.start = info.start;
		this.end = info.end;
		this.type = info.type;
		this.color = info.color;
		this.innerColor = info.innerColor;
		this.dragState = info.dragState;
		this.fill = info.fill;
		this.fontSize = info.fontSize;
		this.stroke = info.stroke;
	}
}