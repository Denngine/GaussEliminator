public class Matrix {
    public static void gaussJordan(double[][] matrix, double[][] inverseMatrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            // Pivotelemente suchen
            double pivot = matrix[i][i];
            if (pivot == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (matrix[j][i] != 0) {
                        // Reihen vertauschen
                        double[] temp = matrix[i];
                        matrix[i] = matrix[j];
                        matrix[j] = temp;

                        temp = inverseMatrix[i];
                        inverseMatrix[i] = inverseMatrix[j];
                        inverseMatrix[j] = temp;

                        pivot = matrix[i][i];
                        break;
                    }
                }
            }

            // Pivotelementreihe normieren
            for (int j = 0; j < n; j++) {
                matrix[i][j] /= pivot;
                inverseMatrix[i][j] /= pivot;
            }

            // Anderen Elemente in der aktuellen Spalte nullsetzen
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double factor = matrix[j][i];
                    for (int k = 0; k < n; k++) {
                        matrix[j][k] -= factor * matrix[i][k];
                        inverseMatrix[j][k] -= factor * inverseMatrix[i][k];
                    }
                }
            }
        }
    }
}
