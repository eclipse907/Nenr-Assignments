package hr.fer.zemris.neuralnet;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dataset implements Iterable<AbstractMap.SimpleImmutableEntry<Double[], Double[]>> {
	
	private List<AbstractMap.SimpleImmutableEntry<Double[], Double[]>> dataset;

	public Dataset(String fileName) {
		dataset = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] splits = line.trim().split("\\t");
				Double[] data = new Double[2];
				data[0] = Double.parseDouble(splits[0].trim());
				data[1] = Double.parseDouble(splits[1].trim());
				Double[] label = new Double[3];
				label[0] = Double.parseDouble(splits[2].trim());
				label[1] = Double.parseDouble(splits[3].trim());
				label[2] = Double.parseDouble(splits[4].trim());
				dataset.add(new AbstractMap.SimpleImmutableEntry<>(data, label));
			}
		} catch (NumberFormatException ex) {
			System.out.println("Error while parsing data from file.");
			System.exit(1);
		} catch (Exception ex) {
			System.out.println("Error while loading dataset from file.");
			System.exit(1);
		}
	}
	
	public int numOfData() {
		return dataset.size();
	}
	
	public AbstractMap.SimpleImmutableEntry<Double[], Double[]> getEntry(int index) {
		if (index < 0 || index >= dataset.size()) {
			throw new IndexOutOfBoundsException();
		}
		return dataset.get(index);
	}

	@Override
	public Iterator<SimpleImmutableEntry<Double[], Double[]>> iterator() {
		return dataset.iterator();
	}
	
}
