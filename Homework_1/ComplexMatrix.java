public class ComplexMatrix {
    private final ComplexNumber[][] cells;
    private final int n;
    private final int m;

    public ComplexMatrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.cells = new ComplexNumber[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                cells[i][j] = new ComplexNumber(0, 0);
    }

    public int getN() { return n; }
    public int getM() { return m; }

    public ComplexNumber get(int i, int j) { return cells[i][j]; }
    public void set(int i, int j, ComplexNumber val) { cells[i][j] = val; }

    public ComplexMatrix plus(ComplexMatrix b) {
        if (this.n != b.n || this.m != b.m) {
            throw new IllegalArgumentException("Размеры не совпадают!");
        }
        ComplexMatrix res = new ComplexMatrix(n, m);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res.set(i, j, this.cells[i][j].plus(b.cells[i][j]));
        return res;
    }

    public ComplexMatrix times(ComplexMatrix b) {
        if (this.m != b.n) {
            throw new IllegalArgumentException("Столбцы первой не равны строкам второй!");
        }
        ComplexMatrix res = new ComplexMatrix(this.n, b.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < b.m; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.m; k++) {
                    sum = sum.plus(this.cells[i][k].times(b.cells[k][j]));
                }
                res.set(i, j, sum);
            }
        }
        return res;
    }

    public ComplexMatrix div(ComplexMatrix b) {
        if (this.n != b.n || this.m != b.m) {
            throw new IllegalArgumentException("Для деления размеры должны совпадать!");
        }
        ComplexMatrix res = new ComplexMatrix(n, m);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res.set(i, j, this.cells[i][j].div(b.cells[i][j]));
        return res;
    }

    public ComplexMatrix trans() {
        ComplexMatrix res = new ComplexMatrix(m, n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res.set(j, i, this.cells[i][j]);
        return res;
    }

    public ComplexNumber det() {
        if (n != m) {
            throw new IllegalArgumentException("Матрица должна быть квадратной!");
        }
        int size = n;
        ComplexNumber[][] tmp = new ComplexNumber[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                tmp[i][j] = new ComplexNumber(cells[i][j].getReal(), cells[i][j].getImag());

        ComplexNumber result = new ComplexNumber(1, 0);

        for (int i = 0; i < size; i++) {
            int pivot = i;
            for (int j = i; j < size; j++) {
                if (!tmp[j][i].isNull()) {
                    pivot = j;
                    break;
                }
            }
            if (tmp[pivot][i].isNull()) {
                return new ComplexNumber(0, 0);
            }
            if (pivot != i) {
                ComplexNumber[] row = tmp[i];
                tmp[i] = tmp[pivot];
                tmp[pivot] = row;
                result = result.times(new ComplexNumber(-1, 0));
            }

            result = result.times(tmp[i][i]);

            for (int j = i + 1; j < size; j++) {
                ComplexNumber coef = tmp[j][i].div(tmp[i][i]);
                for (int k = i; k < size; k++) {
                    tmp[j][k] = tmp[j][k].minus(coef.times(tmp[i][k]));
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("[ ");
            for (int j = 0; j < m; j++) {
                sb.append(cells[i][j]);
                if (j < m - 1) sb.append(", ");
            }
            sb.append(" ]\n");
        }
        return sb.toString();
    }
}