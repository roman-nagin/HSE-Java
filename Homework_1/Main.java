import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    // Здесь храним последний результат
    private static ComplexMatrix lastResult = null;

    public static void main(String[] args) {
        System.out.println("=== Матрицы комплексных чисел ===");
        while (true) {
            System.out.println("\nЧто делаем?");
            System.out.println("1 - Сложение");
            System.out.println("2 - Умножение");
            System.out.println("3 - Деление");
            System.out.println("4 - Транспонирование");
            System.out.println("5 - Определитель");
            System.out.println("6 - Показать последний результат");
            System.out.println("7 - Очистить последний результат");
            System.out.println("0 - Выход");
            System.out.print("Выбор: ");
            int op = readInt();

            try {
                switch (op) {
                    case 1 -> {
                        ComplexMatrix a = getMatrixA();
                        ComplexMatrix b = readMatrix("B");
                        ComplexMatrix res = a.plus(b);
                        System.out.println("A + B:");
                        System.out.println(res);
                        lastResult = res;
                    }
                    case 2 -> {
                        ComplexMatrix a = getMatrixA();
                        ComplexMatrix b = readMatrix("B");
                        ComplexMatrix res = a.times(b);
                        System.out.println("A * B:");
                        System.out.println(res);
                        lastResult = res;
                    }
                    case 3 -> {
                        ComplexMatrix a = getMatrixA();
                        ComplexMatrix b = readMatrix("B");
                        ComplexMatrix res = a.div(b);
                        System.out.println("A / B:");
                        System.out.println(res);
                        lastResult = res;
                    }
                    case 4 -> {
                        ComplexMatrix a = getMatrixA();
                        ComplexMatrix res = a.trans();
                        System.out.println("Транспонированная:");
                        System.out.println(res);
                        lastResult = res;
                    }
                    case 5 -> {
                        ComplexMatrix a = getMatrixA();
                        ComplexNumber d = a.det();
                        System.out.println("Определитель: " + d);
                        // определитель — это число, не матрица,
                        // поэтому в lastResult его не кладём
                    }
                    case 6 -> {
                        if (lastResult == null) {
                            System.out.println("Пока ничего не посчитано.");
                        } else {
                            System.out.println("Последний результат:");
                            System.out.println(lastResult);
                        }
                    }
                    case 7 -> {
                        lastResult = null;
                        System.out.println("Память очищена.");
                    }
                    case 0 -> {
                        System.out.println("Пока!");
                        return;
                    }
                    default -> System.out.println("Нет такого пункта!");
                }
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    // Получить матрицу A: либо из памяти, либо ввести вручную
    private static ComplexMatrix getMatrixA() {
        if (lastResult != null) {
            System.out.print("Использовать последний результат как A? (y/n): ");
            String ans = in.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("д") || ans.equals("yes")) {
                System.out.println("Матрица A (из памяти):");
                System.out.println(lastResult);
                return lastResult;
            }
        }
        return readMatrix("A");
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Это не число, попробуй ещё: ");
            }
        }
    }

    private static ComplexNumber readComplex() {
        while (true) {
            System.out.print("Число (два числа через пробел, напр. '3 4'): ");
            String s = in.nextLine().trim();
            String[] p = s.split("\\s+");
            if (p.length == 2) {
                try {
                    double r = Double.parseDouble(p[0]);
                    double im = Double.parseDouble(p[1]);
                    return new ComplexNumber(r, im);
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Не понял, попробуй снова.");
        }
    }

    private static ComplexMatrix readMatrix(String name) {
        System.out.println("Матрица " + name + ":");
        System.out.print("Строк: ");
        int n = readInt();
        System.out.print("Столбцов: ");
        int m = readInt();
        ComplexMatrix mat = new ComplexMatrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.printf("[%d][%d]:\n", i, j);
                mat.set(i, j, readComplex());
            }
        }
        return mat;
    }
}