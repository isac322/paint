package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;

public class PaintInfo {
	Point start = new Point(-10, -10);
	Point end = new Point(-10, -10);
	DrawType type = DrawType.Pen;
	Color color = Color.BLACK;
	Color innerColor = Color.WHITE;
	boolean draggState = false;
	boolean fill = false;
	int fontSize;
	Stroke stroke = new BasicStroke(1);
	
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