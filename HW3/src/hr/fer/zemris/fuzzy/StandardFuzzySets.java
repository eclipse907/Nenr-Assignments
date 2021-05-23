package hr.fer.zemris.fuzzy;

public class StandardFuzzySets {

	public static IIntUnaryFunction lFunction(int alpha, int beta) {
		return elementIndex -> {
			if (elementIndex < alpha) {
				return 1;
			} else if (elementIndex >= beta) {
				return 0;
			} else {
				return (beta - elementIndex) / (double)(beta - alpha);
			}
		};
	}
	
	public static IIntUnaryFunction gammaFunction(int alpha, int beta) {
		return elementIndex -> {
			if (elementIndex < alpha) {
				return 0;
			} else if (elementIndex >= beta) {
				return 1;
			} else {
				return (elementIndex - alpha) / (double)(beta - alpha);
			}
		};
	}
	
	public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma) {
		return elementIndex -> {
			if (elementIndex < alpha || elementIndex >= gamma) {
				return 0;
			} else if (elementIndex >= alpha && elementIndex < beta) {
				return (elementIndex - alpha) / (double)(beta - alpha);
			} else {
				return (gamma - elementIndex) / (double)(gamma - beta);
			}
		};
	}
	
	public static IIntUnaryFunction piFunction(int alpha, int beta, int gamma, int delta) {
		return elementIndex -> {
			if (elementIndex < alpha || elementIndex >= delta) {
				return 0;
			} else if (elementIndex >= alpha && elementIndex < beta) {
				return (elementIndex - alpha) / (beta - alpha);
			} else if (elementIndex >= beta && elementIndex < gamma) {
				return 1;
			} else {
				return (delta - elementIndex) / (delta - gamma);
			}
		};
	}
	
}
