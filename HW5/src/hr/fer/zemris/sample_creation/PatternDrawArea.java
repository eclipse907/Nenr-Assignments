package hr.fer.zemris.sample_creation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class PatternDrawArea extends JComponent {

	private static final long serialVersionUID = 7152665462366517906L;
	private Image pattern;
	private Graphics2D g2d;
	private List<Point> patternPoints;
	
	public PatternDrawArea() {
		patternPoints = new ArrayList<>();
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				clear();
				patternPoints.clear();
				patternPoints.add(new Point(e.getX(), e.getY()));
			}
			
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (g2d != null) {
					Point current = new Point(e.getX(), e.getY());
					Point last = patternPoints.get(patternPoints.size() - 1);
					g2d.drawLine(last.x, last.y, current.x, current.y);
					repaint();
					patternPoints.add(current);
				}
			}
			
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (pattern == null) {
			pattern = createImage(getSize().width, getSize().height);
			g2d = (Graphics2D)pattern.getGraphics();
			clear();
		}
		g.drawImage(pattern, 0, 0, null);
	}
	
	public void clear() {
		if (g2d != null) {
			g2d.setPaint(Color.white);
			g2d.fillRect(0, 0, getSize().width, getSize().height);
			g2d.setPaint(Color.black);
			repaint();
		}
	}

	public List<Point> getPatternPoints() {
		return patternPoints;
	}
	
}
