import acm.program.ConsoleProgram;

// ორი სურათის ჰორიზონტალურად გადაბმის ამოცანის ამოხსნა.
public class ll extends ConsoleProgram {
	
	private static final int LEFT_EDGE = 1;
	private static final int RIGHT_EDGE = 2;
	
	private boolean[][] pictureUnion(boolean [][] p1, boolean [][] p2){
		int height = p1.length;
		if (height == 0) return new boolean[0][0];
		
		int[] leftDistances = getDistancesFromEdge(p2, LEFT_EDGE);
		int[] rightDistances = getDistancesFromEdge(p1, RIGHT_EDGE);
		// printArray(leftDistances);
		// printArray(rightDistances);
	
		int gap = findLargestPossibleGap(rightDistances, leftDistances);
		int offset = Math.max(gap - 1, 0); // Black cells must not touch.
		int leftImageWidth = p1[0].length;
		int totalWidth = p1[0].length + p2[0].length - offset;
		
		boolean[][] result = new boolean[height][totalWidth];
		addImageToMatrix(p1, result, 0);
		addImageToMatrix(p2, result, leftImageWidth - offset);

		return result;
	}

	// Counts distances from edge to black cells for each line
	private int[] getDistancesFromEdge(boolean[][] image, int edge) {
		int height = image.length;
		int width = image[0].length;
		int[] distances = new int[height];
		
		// default RIGHT_EDGE
		int loopStartIndex = width - 1;
		
		if (edge == LEFT_EDGE) {
			loopStartIndex = 0;
		}
		
		for (int i = 0; i < height; i++) {
			int j = loopStartIndex;
			while (j >= 0 && j <= width-1 && !image[i][j]) {
				distances[i]++;
				if (edge == LEFT_EDGE) {
					j++;
				} else {
					j--;
				}
			}
		}
		
		return distances;
	}
	
	
	private int findLargestPossibleGap(int[] arr1, int[] arr2) {
		int total = arr1.length;
		int[] result = new int[total];
		
		// For each line, find minumum of these three sums (so diagonals are considered)
		// { arr1[i] + arr2[i],  arr1[i] + arr2[i-1],  arr1[i] + arr2[i+1] }
		
		for (int i = 0; i < total; i++) {
			result[i] = arr1[i] + arr2[i];
			if (i > 0) {
				result[i] = Math.min(result[i], arr1[i] + arr2[i-1]);
			}
			if (i < total - 1) {
				result[i] = Math.min(result[i], arr1[i] + arr2[i+1]);
			}
		}
		
		
		// Finally find one minimum number among all lines
		int min = result[0];
		for (int i = 0; i < total; i++) {
			min = Math.min(min, result[i]);
		}
		
		return min;
	}
	
	
	// Method modifies existing matrix
	private void addImageToMatrix(boolean [][] image, boolean [][] matrix, int offset) {
		int height = image.length;
		int width = image[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				matrix[i][j + offset] = image[i][j] || matrix[i][j + offset]; // Do not overwrite black cells
			}
		}
	}
	
	
	private void printMatrix(boolean[][] arr) {
		if (arr.length == 0) return;
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++){
				print(arr[i][j] ? "X " : "0 ");
			}
			println("");
		}
		println("");
	}
	
	private void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			print(arr[i] + " ");
		}
		println("");
	}
	
	public void run(){
		boolean[][] a = {
				{true, false, true, false},
				{false, true, false, false},
				{true, false, false, false},
		};
		
		boolean[][] b = {
				{false, false, true},
				{false, true, false},
				{true, false, false},
		};
		
		printMatrix(pictureUnion(a,b));
		
	}
}
