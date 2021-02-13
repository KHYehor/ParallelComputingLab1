package lab1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Vector {
    private int defaultSize = 200;
    private double[] _Vector = new double[defaultSize];
    private boolean inited = false;
    private String name;

    public Vector(String name) {
        this.name = name;
    }

    public synchronized Vector initWithRandomValues() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            for (int i = 0; i < defaultSize; i++) {
                double randomValue = 100000000.0 * new Random().nextDouble();
                _Vector[i] = randomValue;
                bw.write(String.format("%.7f", randomValue) + ", ");
            }
            bw.flush();
        } catch (IOException e) {}
        inited = true;
        return this;
    }

    public Vector saveFinalResult(String newName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newName));
            for (int i = 0; i < defaultSize; i++) {
                bw.write(String.format("%.7f", _Vector[i]) + ", ");
            }
            bw.flush();
        } catch (IOException e) {}
        return this;
    }

    public synchronized Vector sort() {
        if (!inited) throw new Error("Can't be called before init");
        Arrays.sort(_Vector);
        return this;
    }

    public synchronized Vector multiplyWithMatrix(Matrix matrix) {
        if (!inited) throw new Error("Can't be called before init");
        double[] newVector = new double[defaultSize];
        double[][] matrixData = matrix.get_Matrix();
        for (int i = 0; i < defaultSize; i++) {
            for (int j = 0; j < defaultSize; j++) {
                newVector[i] += _Vector[j] * matrixData[j][i];
            }
        }
        _Vector = newVector;
        return this;
    }

    public Vector printResult() {
        System.out.println(Arrays.toString(_Vector));
        return this;
    }
}
