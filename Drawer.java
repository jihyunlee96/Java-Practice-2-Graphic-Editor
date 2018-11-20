import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Drawer extends JPanel {
	static Graphics2D g2;
	
	static int whichShape = -1;
	static Color color = Color.BLACK;
	static int thickness = 1;
		
	Point startPoint = null;
	Point endPoint = null;
	
	static Sketch current_sketch = null;
	static PolyLine current_pl = null;
	static Eraser current_eraser = null;
	static Spray current_spray = null;
	static Pattern current_pattern = null;
	static Group current_group = null;
	
	static int plMode = -1;	// on: 1, off: -1
		
	public static int fillMode = 0;
	
	public static int angle = 0;
	
	static Vector<Object> object_vector = new Vector<Object>();
	static Vector<Color> color_vector = new Vector<Color>();
	static Vector<Integer> fill_vector = new Vector<Integer>();
	static Vector<Integer> thickness_vector = new Vector<Integer>();
	
	static Vector<Object> temp_copy = new Vector<Object>();
	
	public Drawer ()
	{		
		LineMouseListener lm = new LineMouseListener();
		
		addMouseListener(lm);
		addMouseMotionListener(lm);
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		g2 = (Graphics2D) g;
		g2.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
		
		paintPreviousThings(g2);
		
		if (whichShape != 104 && whichShape != 105)
			temp_copy.setSize(0);
				
		if (startPoint != null)
		{
			g2.setColor(color);
			g2.setStroke(new BasicStroke(thickness));
			
			// line 모드일 때, 현재 만들고 있는 라인 그리기
			if(whichShape == 0)
				drawLine(startPoint, endPoint);
			
			// rect 모드일 때, 현재 만들고 있는 사각형 그리기
			else if(whichShape == 1)
				drawRect(startPoint, endPoint, fillMode, g2);
			
			// circle 모드일 때, 현재 만들고 있는 원 그리기
			else if(whichShape == 2)
				drawEllipse(startPoint, endPoint, fillMode, g2);
			
			// droplet 모드일 때, 현재 만들고 있는 droplet 그리기
			else if(whichShape == 3)
				drawDroplet(startPoint, thickness, g2);
			
			// sketch 모드일 때, 현재 만들고 있는 sketch 그리기
			else if(whichShape == 4)
				current_sketch.draw(g2);
			
			// poly line 모드일 때, 현재 만들고 있는 poly line 그리기
			else if(whichShape == 5)
				current_pl.draw(g2);
			
			else if(whichShape == 6) {
				g2.setStroke(new BasicStroke(thickness*5));
				current_eraser.draw(g2);
			}	
			
			else if(whichShape == 7)
				current_spray.draw(g2);
			
			else if(whichShape == 105 && temp_copy.size() != 0)
				current_pattern.draw(g2);
			
			else if(whichShape == 106 && current_group.get_objects().size() != 0)
				current_group.draw(g2);
		}

	}
	
	class LineMouseListener extends MouseAdapter implements MouseMotionListener
	{
		int move_x;
		int move_y;
		
		Object current_object = null;
		int current_index = 0;
		
		public void mousePressed (MouseEvent e) {
			
			startPoint = e.getPoint();
			
			// 그리기 모드일 때
			if(0 <= whichShape && whichShape != 5 && whichShape < 100) {				
				fill_vector.add(fillMode);
				color_vector.add(color);
				
				if(whichShape == 6)
					thickness_vector.add(thickness*5);
				else
					thickness_vector.add(thickness);
			}
			
			if(whichShape == 4) {
				current_sketch = new Sketch();
				current_sketch.start(startPoint);
			}
			
			else if(whichShape == 6) {
				current_eraser = new Eraser();
				current_eraser.start(startPoint);
			}
			
			else if(whichShape == 7) {
				current_spray = new Spray();
				current_spray.start(startPoint);
			}
			
			else if(whichShape == 105 && temp_copy.size() != 0) {
				current_pattern = new Pattern();
				current_pattern.start(0, 0);
			}
			
			else if(whichShape == 100 || whichShape == 101) {
				int i = 0;
				
				move_x = e.getX();
				move_y = e.getY();
				
				for (i = 0; i < object_vector.size(); i ++) {
					
					if(object_vector.get(i) instanceof Line2D) {
						
						if(((Line2D) object_vector.get(i)).getBounds().contains(move_x, move_y)) {
							current_object = (Line2D)object_vector.get(i);
							break;
						}
							
					}
					
					if(object_vector.get(i) instanceof Rectangle2D) {
						
						if(((Rectangle2D) object_vector.get(i)).getBounds2D().contains(move_x, move_y)) {
							current_object = (Rectangle2D)object_vector.get(i);
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Ellipse2D) {
						
						if(((Ellipse2D) object_vector.get(i)).getBounds().contains(move_x, move_y)) {
							current_object = (Ellipse2D)object_vector.get(i);
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Sketch) {
						
						if(((Sketch) object_vector.get(i)).isContaining(move_x, move_y)) {
							current_object = (Sketch)object_vector.get(i);
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Spray) {
						
						if(((Spray) object_vector.get(i)).isContaining(move_x, move_y)) {
							current_object = (Spray)object_vector.get(i);
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Pattern) {
						
						if(((Pattern) object_vector.get(i)).isContaining(move_x, move_y)) {
							current_object = (Pattern)object_vector.get(i);
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Group) {
						
						if(((Group) object_vector.get(i)).isContaining(move_x, move_y)) {
							current_object = (Group)object_vector.get(i);
							break;
						}
					}
				}
				
				if(i == object_vector.size())
					return;
							
				current_index = i;
			}
		}
		
		public void mouseReleased (MouseEvent e) {
			
			if(whichShape == 0) {
				object_vector.add(drawLine(startPoint, endPoint));
			}
			
			else if(whichShape == 1) {
				object_vector.add(drawRect(startPoint, e.getPoint(), fillMode, g2));
			}
			
			else if(whichShape == 2) {
				object_vector.add(drawEllipse(startPoint, endPoint, fillMode, g2));
			}
			
			else if (whichShape == 4) {
				current_sketch.end(e.getPoint());
				
				object_vector.add(current_sketch);
			}

			else if(whichShape == 6) {
				current_eraser.end(e.getPoint());
				
				object_vector.add(current_eraser);
			}
			
			else if (whichShape == 7) {
				current_spray.start(e.getPoint());
				
				object_vector.add(current_spray);
			}
			
			else if (whichShape == 105 && temp_copy.size() != 0) {
				int dx = e.getX() - move_x;
				int dy = e.getY() - move_y;
				
				current_pattern.add(dx, dy);
				
				object_vector.add(current_pattern);
				color_vector.add(current_pattern.color);
				fill_vector.add(current_pattern.fill);
				thickness_vector.add(current_pattern.thickness);					
			}
			
			else if(whichShape == 100) {
				repaint();
			}

			endPoint = e.getPoint();
			
			repaint();
		}
		
		public void mouseDragged (MouseEvent e) {
			endPoint = e.getPoint();
			
			if (whichShape == 4) {
				
				current_sketch.end(e.getPoint());
				current_sketch.draw(g2);
				repaint();
				
				current_sketch.start(e.getPoint());
			}	
			
			else if(whichShape == 6) {
				current_eraser.end(e.getPoint());
				current_eraser.draw(g2);
				repaint();
				
				current_eraser.start(e.getPoint());
			}	
			
			if (whichShape == 7) {
				current_spray.start(e.getPoint());
				current_spray.draw(g2);
				repaint();				
			}
			
			else if(whichShape == 105 && temp_copy.size() != 0) {
				int dx = e.getX() - move_x;
				int dy = e.getY() - move_y;
				
				current_pattern.add(dx, dy);
				current_pattern.draw(g2);
				repaint();
			}
			
			if (whichShape == 100) {
				int dx = e.getX() - move_x;
				int dy = e.getY() - move_y;
				
				if (current_object instanceof Line2D) {
					
					Line2D current_line = (Line2D)current_object;
					
					if (current_line.getBounds().contains(move_x, move_y))
					{
						current_line.setLine(current_line.getX1() + dx, current_line.getY1() + dy, current_line.getX2() + dx, current_line.getY2() + dy);
						g2.draw(current_line);
						
						repaint();
					}
				}
								
				else if (current_object instanceof Rectangle2D) {
					
					Rectangle2D current_rect = (Rectangle2D)current_object;
					
					if (current_rect.getBounds2D().contains(move_x, move_y))
					{
						current_rect.setRect(current_rect.getX() + dx, current_rect.getY() + dy, current_rect.getWidth(), current_rect.getHeight());
						g2.draw(current_rect);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Ellipse2D) {
					
					Ellipse2D current_ellipse = (Ellipse2D)current_object;
					
					if (current_ellipse.getBounds().contains(move_x, move_y))
					{
						current_ellipse.setFrame(current_ellipse.getX() + dx, current_ellipse.getY() + dy, current_ellipse.getWidth(), current_ellipse.getHeight());
						g2.draw(current_ellipse);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Sketch) {
					
					Sketch current_sketch = (Sketch)current_object;
					
					if (current_sketch.isContaining(move_x, move_y))
					{
						current_sketch.move(dx, dy);
						current_sketch.draw(g2);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Spray) {
					
					Spray current_spray = (Spray)current_object;
					
					if (current_spray.isContaining(move_x, move_y))
					{
						current_spray.move(dx, dy);
						current_spray.draw(g2);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Pattern) {
					
					Pattern current_pattern = (Pattern)current_object;
					
					if (current_pattern.isContaining(move_x, move_y))
					{
						current_pattern.move(dx, dy);
						current_pattern.draw(g2);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Group) {
					
					Group current_group = (Group)current_object;
					
					if (current_group.isContaining(move_x, move_y))
					{
						current_group.move(dx, dy);
						current_group.draw(g2);
						
						repaint();
					}
				}
				
				move_x += dx;
				move_y += dy;
				
			}
			
			else if (whichShape == 101) {
				int dx = e.getX() - move_x;
				int dy = e.getY() - move_y;
							
				if (current_object instanceof Line2D) {
					
					Line2D current_line = (Line2D)current_object;
					
					if (current_line.getBounds().contains(move_x, move_y))
					{
						current_line.setLine(current_line.getX1(), current_line.getY1(), current_line.getX2() + dx, current_line.getY2() + dy);
						g2.draw(current_line);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Rectangle2D)
				{
					Rectangle2D current_rect = (Rectangle2D) current_object;
					
					if (current_rect.getBounds2D().contains(move_x, move_y))
					{
						current_rect.setRect(current_rect.getX(), current_rect.getY(), current_rect.getWidth() + dx, current_rect.getHeight() + dy);
						g2.draw(current_rect);
						
						repaint();
					}
				}
				
				else if (current_object instanceof Ellipse2D)
				{
					Ellipse2D current_ellipse = (Ellipse2D)current_object;
					
					if (current_ellipse.getBounds().contains(move_x, move_y))
					{
						current_ellipse.setFrame(current_ellipse.getX() , current_ellipse.getY(), current_ellipse.getWidth() + dx, current_ellipse.getHeight() + dy);
						g2.draw(current_ellipse);
						
						repaint();
					}
				}
				
				move_x += dx;
				move_y += dy;
			}
			
			else {
				endPoint = e.getPoint();
				repaint();
			}
		}
		
		public void mouseMoved (MouseEvent e) {
			if(whichShape == 5) {
				if (plMode == 1) {
					endPoint = e.getPoint();
					
					current_pl.draw(g2);
					repaint();
				}
			}
			
			// Paste 모드일 때 잔상 남기기. move 코드와 중복 (repaint 제외)
			if (whichShape == 104 && temp_copy.size() != 0) {
				int dx = e.getX() - move_x;
				int dy = e.getY() - move_y;
				
				// temp_copy.get(0) 에는 복사한 object를 저장
				if (temp_copy.get(0) instanceof Line2D) {
					Line2D current_line = (Line2D)temp_copy.get(0);	
					current_line.setLine(current_line.getX1() + dx, current_line.getY1() + dy, current_line.getX2() + dx, current_line.getY2() + dy);
					g2.draw(current_line);		
						
					repaint();
				}
								
				else if (temp_copy.get(0) instanceof Rectangle2D) {
					
					Rectangle2D current_rect = (Rectangle2D)temp_copy.get(0);
					
					if (current_rect.getBounds2D().contains(move_x, move_y))
					{
						current_rect.setRect(current_rect.getX() + dx, current_rect.getY() + dy, current_rect.getWidth(), current_rect.getHeight());
						g2.draw(current_rect);
						
						repaint();
					}
				}
				
				else if (temp_copy.get(0) instanceof Ellipse2D) {
					
					Ellipse2D current_ellipse = (Ellipse2D)temp_copy.get(0);
					
					if (current_ellipse.getBounds().contains(move_x, move_y))
					{
						current_ellipse.setFrame(current_ellipse.getX() + dx, current_ellipse.getY() + dy, current_ellipse.getWidth(), current_ellipse.getHeight());
						g2.draw(current_ellipse);
						
						repaint();
					}
				}
				
				else if (temp_copy.get(0) instanceof Sketch) {
					
					Sketch current_sketch = (Sketch)temp_copy.get(0);
					
					if (current_sketch.isContaining(move_x, move_y))
					{
						current_sketch.move(dx, dy);
						current_sketch.draw(g2);
						
						repaint();
					}
				}
				
				else if (temp_copy.get(0) instanceof Spray) {
					
					Spray current_spray = (Spray)temp_copy.get(0);
					
					if (current_spray.isContaining(move_x, move_y))
					{
						current_spray.move(dx, dy);
						current_spray.draw(g2);
						
						repaint();
					}
				}
				
				else if (temp_copy.get(0) instanceof Group) {
					
					Group current_group = (Group)temp_copy.get(0);
					
					if (current_group.isContaining(move_x, move_y))
					{
						current_group.move(dx, dy);
						current_group.draw(g2);
						
						repaint();
					}
				}
				
				move_x += dx;
				move_y += dy;
				
			}
		}
		
		public void mouseClicked(MouseEvent e)
		{
			if (whichShape == 5) {
				
				// double click
				if (e.getClickCount() == 2) {
					
					plMode *= -1;
						
					if (plMode == 1) {						
						current_pl = new PolyLine();
						
						current_pl.start(e.getPoint());
					}
					else {
						current_pl.end(e.getPoint());
						
						object_vector.add(current_pl);
						
						fill_vector.add(fillMode);
						color_vector.add(color);
						thickness_vector.add(thickness);
					}
				}
				
				//click
				else {
					current_pl.end(e.getPoint());
					current_pl.draw(g2);
					repaint();
					
					current_pl.start(e.getPoint());
				}
			}
			
			// grouping 모드 종료
			if (whichShape == 106 && e.getClickCount() == 2 && current_group != null) {
				System.out.print("grouped");
				
				object_vector.add(current_group);
				color_vector.add(Color.BLACK);
				fill_vector.add(-1);
				thickness_vector.add(1);
				
				JOptionPane.showMessageDialog(null, current_group.get_objects().size() + " object(s) grouped together.");
				
				current_group = null;				
				
				return;
			}
			
			// remove 모드 || copy 모드 || pattern 모드 || grouping 모드 || ungrouping 모드 || rotate 모드
			if (whichShape == 102 || (whichShape == 104 && temp_copy.size() == 0) || (whichShape == 105 && temp_copy.size() == 0)
					|| whichShape == 106 || whichShape == 107 || whichShape == 103)
			{
				int i = 0;
				
				move_x = e.getX();
				move_y = e.getY();
				
				for (i = 0; i < object_vector.size(); i ++) {
					
					if(whichShape == 107 && !(object_vector.get(i) instanceof Group))
						continue;
					
					if(object_vector.get(i) instanceof Line2D) {
						
						if(((Line2D) object_vector.get(i)).getBounds().contains(move_x, move_y)) {
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Rectangle2D) {
						
						if(((Rectangle2D) object_vector.get(i)).getBounds2D().contains(move_x, move_y)) {
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Ellipse2D) {
						
						if(((Ellipse2D) object_vector.get(i)).getBounds().contains(move_x, move_y)) {
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Sketch) {
						
						if(((Sketch) object_vector.get(i)).isContaining(move_x, move_y)) {
							break;
						}
					}
					
					if(object_vector.get(i) instanceof Spray) {
						
						if(((Spray) object_vector.get(i)).isContaining(move_x, move_y)) {
							break;
						}
					}
					
					if (object_vector.get(i) instanceof Pattern) {
												
						if (((Pattern) object_vector.get(i)).isContaining(move_x, move_y))
							break;
					}
					
					if (object_vector.get(i) instanceof Group) {
						
						if (((Group) object_vector.get(i)).isContaining(move_x, move_y))
							break;
					}
				}
				
				// 선택 못 받을 경우 리턴
				if(i == object_vector.size())
					return;
				
				// remove 모드
				if (whichShape == 102) {
					object_vector.remove(i);
					color_vector.remove(i);
					fill_vector.remove(i);
					thickness_vector.remove(i);
				}
				
				// copy 모드
				else if (whichShape == 104) {
					if (i != object_vector.size())
					{
						JOptionPane.showMessageDialog(null, "Paste!");
						
						Edit_Copy.deep_copy_to_temp (object_vector.get(i), color_vector.get(i), fill_vector.get(i), thickness_vector.get(i));
					}
				}
				
				// pattern 모드
				else if (whichShape == 105 && temp_copy.size() == 0) {
					if (i != object_vector.size())
					{
						JOptionPane.showMessageDialog(null, "Selected!");
						
						Edit_Copy.deep_copy_to_temp (object_vector.get(i), color_vector.get(i), fill_vector.get(i), thickness_vector.get(i));
					}
				}
				
				// grouping 모드
				else if (whichShape == 106) {
					
					if(current_group == null)
						current_group = new Group();
					
					// 선택했을 때
					if (i != object_vector.size())
					{						
						current_group.add(object_vector.get(i), color_vector.get(i), fill_vector.get(i), 
								thickness_vector.get(i), i);
						
						JOptionPane.showMessageDialog(null, current_group.get_objects().size() + " object(s) selected!\nDouble click to finish grouping.");						
					}
				}
				
				// ungrouping 모드
				else if (whichShape == 107) {

					// 선택했을 때
					if (i != object_vector.size())
					{						
						JOptionPane.showMessageDialog(null, ((Group) object_vector.get(i)).get_objects().size() + " object(s) ungrouped!");						

						
						((Group) object_vector.get(i)).ungroup();
						
						object_vector.remove(i);
						color_vector.remove(i);
						fill_vector.remove(i);
						thickness_vector.remove(i);						
					}
				}
				
				// rotate 모드
				else if (whichShape == 103) {
					
					if (object_vector.get(i) instanceof Line2D) {
						
						Line2D current_line = (Line2D)object_vector.get(i);
																
						AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle), 
								(current_line.getX1() + current_line.getX2() / 2), 
								(current_line.getY1() + current_line.getY2()) / 2);
													
						g2.draw(at.createTransformedShape(current_line));	
						object_vector.set(current_index, at.createTransformedShape(current_line));						

						repaint();
					}
					
					else if (object_vector.get(i) instanceof Rectangle2D) {
						
						Rectangle2D current_rect = (Rectangle2D)object_vector.get(i);
																
						AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle),
								current_rect.getCenterX(), current_rect.getCenterY());
						
						System.out.print(angle);
													
						g2.draw(at.createTransformedShape(current_rect));	
						object_vector.set(current_index, at.createTransformedShape(current_rect));						

						repaint();
					}
				}
			}
			
			// paste 모드
			if (whichShape == 104 && temp_copy.size() != 0)
			{
				object_vector.add(temp_copy.get(0));
				color_vector.add((Color)temp_copy.get(1));
				fill_vector.add((Integer)temp_copy.get(2));
				thickness_vector.add((Integer)temp_copy.get(3));		
				
				Edit_Copy.deep_copy_to_temp (temp_copy.get(0), (Color)temp_copy.get(1), (Integer)temp_copy.get(2), (Integer)temp_copy.get(3));
			}
		}
	}
	
	public static int getWhichShape() { return whichShape; }
	
	public void setWhichShape(int n)
	{
		whichShape = n;
	}
	
	public static void setColor(Color in_color)
	{
		color = in_color;
	}
	
	public static void setThickness(int in_thickness)
	{
		thickness = in_thickness;
	}
	
	public static Line2D drawLine(Point startPoint, Point endPoint)
	{
		Line2D line = null;
		
		line = new Line2D.Double(startPoint, endPoint);
		g2.draw(line);
		
		return line;
	}
	
	public static Rectangle2D drawRect(Point startPoint, Point endPoint, int fillMode, Graphics2D g)
	{
		Rectangle2D rect;
		
		if (startPoint.x < endPoint.x)
		{
			if(startPoint.y < endPoint.y) {
				rect = new Rectangle2D.Double(startPoint.x, startPoint.y, endPoint.x - startPoint.x, endPoint.y - startPoint.y);				
			}
			else {
				rect = new Rectangle2D.Double(startPoint.x, endPoint.y, endPoint.x - startPoint.x, startPoint.y - endPoint.y);
			}
		}
		
		else
		{
			if(startPoint.y < endPoint.y) {
				rect = new Rectangle2D.Double(endPoint.x, startPoint.y, startPoint.x - endPoint.x, endPoint.y - startPoint.y);
			}
			else {
				rect = new Rectangle2D.Double(endPoint.x, endPoint.y, startPoint.x - endPoint.x, startPoint.y - endPoint.y);
			}
		}
		
		g.draw(rect);
		
		if(fillMode == 1)
			g2.fill(rect);
		
		return rect;
	}
	
	public static Ellipse2D drawEllipse(Point startPoint, Point endPoint, int fillMode, Graphics2D g)
	{
		Ellipse2D ellipse;
		
		int width = Math.abs(startPoint.x - endPoint.x);
		int height =  Math.abs(startPoint.y - endPoint.y);
		
		if(startPoint.x <= endPoint.x) {
			if(startPoint.y <= endPoint.y) {
				ellipse = new Ellipse2D.Double(startPoint.x, startPoint.y, width, height);								
			}
			else {
				ellipse = new Ellipse2D.Double(startPoint.x, endPoint.y, width, height);
			}
		}
		else {
			if(startPoint.y <= endPoint.y) {
				ellipse = new Ellipse2D.Double(endPoint.x, startPoint.y, width, height);
			}
			else {
				ellipse = new Ellipse2D.Double(endPoint.x, endPoint.y, width, height);
			}
		}
		
		g2.draw(ellipse);
		
		if (fillMode == 1)
			g2.fill(ellipse);
		
		return ellipse;
	}
	
	public static void drawDroplet (Point startPoint, int in_thickness, Graphics2D g)
	{
		g.drawOval(startPoint.x, startPoint.y, in_thickness*2, in_thickness*2);
		g.fillOval(startPoint.x, startPoint.y, in_thickness*2, in_thickness*2);
	}
	
	public void paintPreviousThings(Graphics2D g2)
	{	

		for (int i = 0; i < object_vector.size(); i ++)
		{
			g2.setStroke(new BasicStroke(thickness_vector.get(i)));
			g2.setColor(color_vector.get(i));
			
			if (object_vector.get(i) instanceof Line2D) {				
				Line2D line = (Line2D)object_vector.get(i);
			
				g2.draw(line);					
			}
			
			else if (object_vector.get(i) instanceof Rectangle2D) {
				Rectangle2D rect = (Rectangle2D)object_vector.get(i);
				
				g2.draw(rect);
				if(fill_vector.get(i) == 1)
					g2.fill(rect);				
			}
			
			else if (object_vector.get(i) instanceof Ellipse2D) {
				Ellipse2D ellipse = (Ellipse2D)object_vector.get(i);
				
				g2.draw(ellipse);
				if(fill_vector.get(i) == 1)
					g2.fill(ellipse);				
			}
		
			else if (object_vector.get(i) instanceof Sketch) {
				Sketch sketch = (Sketch)object_vector.get(i);
				
				sketch.draw(g2);
			}
			
			else if (object_vector.get(i) instanceof PolyLine) {
				PolyLine pl = (PolyLine)object_vector.get(i);
				
				pl.draw(g2);
			}
			
			else if (object_vector.get(i) instanceof Spray) {
				Spray spray = (Spray)object_vector.get(i);
				
				spray.draw(g2);
			}
		
			else if (object_vector.get(i) instanceof Eraser) {
				Eraser eraser = (Eraser)object_vector.get(i);
				
				eraser.draw(g2);
			}
			
			else if (object_vector.get(i) instanceof Pattern) {
				Pattern pattern = (Pattern)object_vector.get(i);
				
				pattern.draw(g2);
			}
			
			else if (object_vector.get(i) instanceof Group) {
				Group group = (Group)object_vector.get(i);
				
				group.draw(g2);
			}
			
			else {
				Shape shape = (Shape)object_vector.get(i);
				g2.draw(shape);
			}
			
		}
		
		// 카피 모드 시 임시로 저장된 객체 그리기. 위 코드와 거의 중복
		if (whichShape == 104 && temp_copy.size() != 0)
		{
			g2.setStroke(new BasicStroke((int)temp_copy.get(3)));
			g2.setColor((Color)temp_copy.get(1));
			
			if (temp_copy.get(0) instanceof Line2D) {				
				Line2D line = (Line2D)temp_copy.get(0);
			
				g2.draw(line);					
			}
			
			if (temp_copy.get(0) instanceof Rectangle2D) {
				Rectangle2D rect = (Rectangle2D)temp_copy.get(0);
				
				g2.draw(rect);
				if((int)temp_copy.get(2) == 1)
					g2.fill(rect);				
			}
			
			if (temp_copy.get(0) instanceof Ellipse2D) {
				Ellipse2D ellipse = (Ellipse2D)temp_copy.get(0);
				
				g2.draw(ellipse);
				if((int)temp_copy.get(2) == 1)
					g2.fill(ellipse);				
			}
		
			if (temp_copy.get(0) instanceof Sketch) {
				Sketch sketch = (Sketch)temp_copy.get(0);
				
				sketch.draw(g2);
			}
			
			if (temp_copy.get(0) instanceof PolyLine) {
				PolyLine pl = (PolyLine)temp_copy.get(0);
				
				pl.draw(g2);
			}
			
			if (temp_copy.get(0) instanceof Spray) {
				Spray spray = (Spray)temp_copy.get(0);
				
				spray.draw(g2);
			}
		
			if (temp_copy.get(0) instanceof Eraser) {
				Eraser eraser = (Eraser)temp_copy.get(0);
				
				eraser.draw(g2);
			}
			
			if (temp_copy.get(0) instanceof Group) {
				Group group = (Group)temp_copy.get(0);
				
				group.draw(g2);
			}
		}
	}
	
	public void reset()
	{	
		startPoint = null;
		endPoint = null;
		
		whichShape = -1;
		
		object_vector = new Vector<Object>();		
		color_vector = new Vector<Color>();
		fill_vector = new Vector<Integer>();
		thickness_vector = new Vector<Integer>();
		
		temp_copy.setSize(0);
		
		repaint();
	}
	
	public void undo ()
	{	
		int last_index = object_vector.size() - 1;
		
		startPoint = null;
		endPoint = null;
		
		Edit_Redo.redo_save(object_vector.get(last_index), color_vector.get(last_index), fill_vector.get(last_index), thickness_vector.get(last_index));
		
		object_vector.setSize(last_index);
		color_vector.setSize(last_index);
		fill_vector.setSize(last_index);
		thickness_vector.setSize(last_index);
		
		repaint();
	}
	
	public void redo (int in_whichShape)
	{
		startPoint = null;
		endPoint = null;
		
		Edit_Redo.redo_last(object_vector, color_vector, fill_vector, thickness_vector);
		
		repaint();
	}
}

