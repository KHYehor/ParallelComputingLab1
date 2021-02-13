package lab1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;
import java.util.Arrays;

public class Matrix {
    private int defaultSize = 200;
    private double[][] _Matrix = new double[defaultSize][defaultSize];
    private boolean inited = false;
    private String name;

    public Matrix(String name) {
        this.name = name;
    }

    public double[][] get_Matrix() {
        return _Matrix;
    }

    private static double kahanSum(double... fa)
    {
        double sum = 0.0;
        double c = 0.0;
        for (double f : fa) {
            double y = f - c;
            double t = sum + y;
            c = (t - sum) - y;
            sum = t;
        }
        return sum;
    }

    public synchronized Matrix initWithRandomValues() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            for (int i = 0; i < defaultSize; i++) {
                for (int j = 0; j < defaultSize; j++) {
                    double randomValue = 100000000.0 * new Random().nextDouble();
                    _Matrix[i][j] = randomValue;
                    bw.write(String.valueOf(randomValue) + ", ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {}
        inited = true;
        return this;
    }

    public synchronized Matrix sort() {
        Arrays.sort(_Matrix, Comparator.comparingDouble(o -> o[0]));
        return this;
    }

    public synchronized Matrix multiplyWithMatrix(Matrix matrix) {
        double[][] newMatrix = new double[defaultSize][defaultSize];
        double[][] inputMatrix = matrix.get_Matrix();
        for (int i = 0; i < defaultSize; i++) {
            for (int j = 0; j < defaultSize; j++) {
                newMatrix[i][j] = kahanSum(newMatrix[i][j], _Matrix[i][j] * inputMatrix[i][j]);
            }
        }
        _Matrix = newMatrix;
        return this;
    }

    public synchronized Matrix sumWithMatrix(Matrix matrix) {
        double[][] newMatrix = new double[defaultSize][defaultSize];
        double[][] inputMatrix = matrix.get_Matrix();
        for (int i = 0; i < defaultSize; i++) {
            for (int j = 0; j < defaultSize; j++) {
                newMatrix[i][j] = kahanSum(newMatrix[i][j], inputMatrix[i][j]);
            }
        }
        _Matrix = newMatrix;
        return this;
    }

    public Matrix printResult() {
        for (int i = 0; i < defaultSize; i++) {
            System.out.println(Arrays.toString(_Matrix[i]));
        }
        return this;
    }
    public Matrix saveFinalResult(String newName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newName));
            for (int i = 0; i < defaultSize; i++) {
                for (int j = 0; j < defaultSize; j++) {
                    bw.write(String.valueOf(_Matrix[i][j]) + ", ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
        }
        return this;
    }
}
