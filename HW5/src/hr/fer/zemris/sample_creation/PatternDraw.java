package hr.fer.zemris.sample_creation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PatternDraw extends JFrame {

	private static final long serialVersionUID = 1L;
	private PatternDrawArea patternDrawComponent;
	private int M;
	private String patternDrawn;
	
	public PatternDraw() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Pattern Draw");
		setLocation(200, 200);
		setSize(500, 500);
		M = 30;
		patternDrawn = "";
		initGUI();
	}
	
	private void initGUI() {
		patternDrawComponent = new PatternDrawArea();
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		String[] patterns = {"alpha", "beta", "gamma", "delta", "epsilon"};
		JComboBox<String> patternList = new JComboBox<>(patterns);
		patternList.setSelectedIndex(-1);
		patternList.addActionListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
	        patternDrawn = (String)cb.getSelectedItem();
		});
		buttonsPanel.add(patternList);
		JButton saveButton = new JButton("Save pattern");
		saveButton.addActionListener(e -> {
			List<Point> patternPoints = patternDrawComponent.getPatternPoints();
			Point2D.Double Tc = new Point2D.Double(0.0, 0.0);
			for (Point point : patternPoints) {
				Tc.x += point.x;
				Tc.y += point.y;
			}
			Tc.x /= patternPoints.size();
			Tc.y /= patternPoints.size();
			List<Point2D.Double> patternPointsDouble = new ArrayList<>();
			for (Point point : patternPoints) {
				patternPointsDouble.add(new Point2D.Double(point.x - Tc.x, point.y - Tc.y));
			}
			double mx = Double.MIN_VALUE;
			double my = Double.MIN_VALUE;
			for (Point2D.Double point : patternPointsDouble) {
				if (mx < Math.abs(point.x)) {
					mx = Math.abs(point.x);
				}
				if (my < Math.abs(point.y)) {
					my = Math.abs(point.y);
				}
			}
			double m = Math.max(mx, my);
			for (Point2D.Double point : patternPointsDouble) {
				point.x /= m;
				point.y /= m;
			}
			double D = 0;
			Point2D.Double lastPoint = null;
			for (Point2D.Double point : patternPointsDouble) {
				if (lastPoint == null) {
					lastPoint = point;
					continue;
				}
				D += lastPoint.distance(point);
				lastPoint = point;
			}
			List<Point2D.Double> pointsToSave = new ArrayList<>();
			pointsToSave.add(patternPointsDouble.get(0));
			double currentLength = 0;
			int lastPointIndex = 0;
			for (int k = 1; k < M; k++) {
				if (k == M - 1) {
					pointsToSave.add(patternPointsDouble.get(patternPointsDouble.size() - 1));
					break;
				}
				double distanceToAddFromStart = (k * D) / (M - 1);
				for (int i = lastPointIndex + 1; i < patternPointsDouble.size(); i++) {
					double nextDistance = patternPointsDouble.get(lastPointIndex).distance(patternPointsDouble.get(i));
					if (distanceToAddFromStart >= currentLength && distanceToAddFromStart <= currentLength + nextDistance) {
						if (distanceToAddFromStart - currentLength < (currentLength + nextDistance) - distanceToAddFromStart) {
							pointsToSave.add(patternPointsDouble.get(lastPointIndex));
						} else {
							pointsToSave.add(patternPointsDouble.get(i));
						}
						break;
					}
					currentLength += nextDistance;
					lastPointIndex = i;
				}
			}
			try {
				StringBuilder sb = new StringBuilder();
				for (Point2D.Double point : pointsToSave) {
					sb.append(Double.toString(point.x) + "," + Double.toString(point.y) + ",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append("->");
				if (patternDrawn.equals("alpha")) {
					sb.append("1,0,0,0,0");
				} else if (patternDrawn.equals("beta")) {
					sb.append("0,1,0,0,0");
				} else if (patternDrawn.equals("gamma")) {
					sb.append("0,0,1,0,0");
				} else if (patternDrawn.equals("delta")) {
					sb.append("0,0,0,1,0");
				} else {
					sb.append("0,0,0,0,1");
				}
				Files.writeString(Paths.get("uzorci.txt"), sb.toString() + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
			} catch (IOException ex) {
				System.out.println("Error while saving pattern to file.");
				System.exit(1);
			}
			patternDrawn = "";
			patternList.setSelectedIndex(-1);
			patternDrawComponent.clear();
		});
		buttonsPanel.add(saveButton);
		cp.add(buttonsPanel, BorderLayout.NORTH);
		cp.add(patternDrawComponent, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PatternDraw().setVisible(true);
		});
	}
	
}
