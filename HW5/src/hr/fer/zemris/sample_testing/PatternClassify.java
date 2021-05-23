package hr.fer.zemris.sample_testing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.neural_net.NeuralNetwork;

public class PatternClassify extends JFrame {

	private static final long serialVersionUID = 1L;
	private PatternDrawArea patternDrawComponent;
	private int M;
	private NeuralNetwork neuralNetwork;
	
	private PatternClassify() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Classify pattern");
		setLocation(200, 200);
		setSize(500, 500);
		M = 30;
		initGUI();
	}
	
	public PatternClassify(NeuralNetwork neuralNetwork) {
		this();
		this.neuralNetwork = neuralNetwork;
	}
	
	private void initGUI() {
		patternDrawComponent = new PatternDrawArea();
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		JButton classifyButton = new JButton("Classify pattern");
		classifyButton.addActionListener(e -> {
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
			List<Double> pointsToSave = new ArrayList<>();
			pointsToSave.add(patternPointsDouble.get(0).x);
			pointsToSave.add(patternPointsDouble.get(0).y);
			double currentLength = 0;
			int lastPointIndex = 0;
			for (int k = 1; k < M; k++) {
				if (k == M - 1) {
					pointsToSave.add(patternPointsDouble.get(patternPointsDouble.size() - 1).x);
					pointsToSave.add(patternPointsDouble.get(patternPointsDouble.size() - 1).y);
					break;
				}
				double distanceToAddFromStart = (k * D) / (M - 1);
				for (int i = lastPointIndex + 1; i < patternPointsDouble.size(); i++) {
					double nextDistance = patternPointsDouble.get(lastPointIndex).distance(patternPointsDouble.get(i));
					if (distanceToAddFromStart >= currentLength && distanceToAddFromStart <= currentLength + nextDistance) {
						if (distanceToAddFromStart - currentLength < (currentLength + nextDistance) - distanceToAddFromStart) {
							pointsToSave.add(patternPointsDouble.get(lastPointIndex).x);
							pointsToSave.add(patternPointsDouble.get(lastPointIndex).y);
						} else {
							pointsToSave.add(patternPointsDouble.get(i).x);
							pointsToSave.add(patternPointsDouble.get(i).y);
						}
						break;
					}
					currentLength += nextDistance;
					lastPointIndex = i;
				}
			}
			List<Double> classificationResult = neuralNetwork.forwardPass(pointsToSave);
			int maxIndex = 0;
			for (int i = 1; i < classificationResult.size(); i++) {
				if (classificationResult.get(maxIndex) < classificationResult.get(i)) {
					maxIndex = i;
				}
			}
			String result;
			if (maxIndex == 0) {
				result = "Alpha";
			} else if (maxIndex == 1) {
				result = "Beta";
			} else if (maxIndex == 2) {
				result = "Gamma";
			} else if (maxIndex == 3) {
				result = "Delta";
			} else {
				result = "Epsilon";
			}
			String message = "Alpha=" + classificationResult.get(0) + "\nBeta=" + classificationResult.get(1) +
							 "\nGamma=" + classificationResult.get(2) + "\nDelta=" + classificationResult.get(3) +
							 "\nEpsilon=" + classificationResult.get(4) + "\n\nPattern is classified as " + result;
			JOptionPane.showMessageDialog(this, message, "Results", JOptionPane.INFORMATION_MESSAGE);
		});
		buttonsPanel.add(classifyButton);
		cp.add(buttonsPanel, BorderLayout.NORTH);
		cp.add(patternDrawComponent, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PatternClassify().setVisible(true);
		});
	}
	
}
