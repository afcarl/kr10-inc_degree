package edu.pku.id;

public class ProblemGenerator {
	public static void main(String[] args) {
		int N = 2;

		System.out.println(String.format("p cnf %d %d", 2 * N, N * N + 2 * N));

		// pi
		for (int i = 1; i <= N; i++) {
			System.out.println(String.format("%d 0", i));
		}
		// qj
		for (int j = 1; j <= N; j++) {
			System.out.println(String.format("%d 0", N + j));
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				System.out.println(String.format("%d %d 0", -i, -(N + j)));
			}
		}
	}
}
