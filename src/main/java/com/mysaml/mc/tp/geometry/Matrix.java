package com.mysaml.mc.tp.geometry;

import java.util.Arrays;

public class Matrix {
    private int rows;
    private int columns;
    private double[][] data;

    /**
     * Creates a new matrix
     * @param rows Number of rows
     * @param columns Number of columns
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(this.data[i], 0);
        }
    }

    public int getColumnsSize() { return this.columns; }
    public int getRowsSize() { return this.rows; }

    /**
     * Sets a value in the matrix
     * @param row Row index
     * @param column Column index
     * @param value Value to set
     */
    public void set(int row, int column, double value) {
        if (!this.isInRange(row, column)) throw new IndexOutOfBoundsException("out of range");
        this.data[row][column] = value;
    }

    /**
     * Sets a row in the matrix
     * @param row Row index
     * @param values Values to set in the row
     */
    public void setRow(int row, double[] values) {
        if (!this.isInRange(row, 0)) throw new IndexOutOfBoundsException("out of range");
        if (values.length != this.columns) throw new IllegalArgumentException("invalid length");
        this.data[row] = Arrays.copyOf(values, values.length);
    }

    /**
     * Sets a column in the matrix
     * @param column Column index
     * @param values Values to set in the column
     */
    public void setColumn(int column, double[] values) {
        if (!this.isInRange(0, column)) throw new IndexOutOfBoundsException("out of range");
        if (values.length != this.rows) throw new IllegalArgumentException("invalid length");
        for (int i = 0; i < this.rows; i++) {
            this.data[i][column] = values[i];
        }
    }

    /**
     * Sets all values in the matrix
     * @param values Values to set in the matrix
     */
    public void setAll(double[][] values) {
        if (values.length != this.rows || values[0].length != this.columns) throw new IllegalArgumentException("invalid length");
        for (int i = 0; i < this.rows; i++) {
            System.arraycopy(values[i], 0, this.data[i], 0, this.columns);
        }
    }

    /**
     * Gets a value in the matrix
     * @param row Row index
     * @param column Column index
     * @return Value at the specified position
     */
    public double get(int row, int column) {
        if (!this.isInRange(row, column)) throw new IndexOutOfBoundsException("out of range");
        return this.data[row][column];
    }

    /**
     * Gets a row in the matrix
     * @param row Row index
     * @return Values in the specified row
     */
    public double[] getRow(int row) {
        if (!this.isInRange(row, 0)) throw new IndexOutOfBoundsException("out of range");
        return Arrays.copyOf(this.data[row], this.columns);
    }

    /**
     * Gets a column in the matrix
     * @param column Column index
     * @return Values in the specified column
     */
    public double[] getColumn(int column) {
        if (!this.isInRange(0, column)) throw new IndexOutOfBoundsException("out of range");
        double[] columnData = new double[this.rows];
        for (int i = 0; i < this.rows; i++) {
            columnData[i] = this.data[i][column];
        }
        return columnData;
    }

    /**
     * Checks if a value is in the matrix
     * @param row Row index
     * @param column Column index
     * @return True if the value is in range, otherwise false
     */
    protected boolean isInRange(int row, int column) {
        return (
            row >= 0 &&
            row < this.rows &&
            column >= 0 &&
            column < this.columns
        );
    }

    /**
     * Iterates over the matrix
     * @param callback Callback function
     */
    public void forEach(MatrixCallback callback) {
        for (int i = 0; i < this.rows; i++)
        for (int j = 0; j < this.columns; j++) {
            callback.apply(this.get(i, j), i, j);
        }
    }

    /**
     * Clones this matrix
     * @return A clone of this matrix
     */
    public Matrix clone() {
        Matrix clone = new Matrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++)
        for (int j = 0; j < this.columns; j++) {
            clone.set(i, j, this.get(i, j));
        }
        return clone;
    }

    /**
     * Multiplies two matrices
     * @param matrix1 The first matrix
     * @param matrix2 The second matrix
     * @return The result of the multiplication
     */
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsSize() != matrix2.getRowsSize()) throw new IllegalArgumentException("invalid dimensions");
        Matrix result = new Matrix(matrix1.getRowsSize(), matrix2.getColumnsSize());
        for (int i = 0; i < result.getRowsSize(); i++)
        for (int j = 0; j < result.getColumnsSize(); j++) {
            double sum = 0;
            for (int k = 0; k < matrix1.getColumnsSize(); k++) {
                sum += matrix1.get(i, k) * matrix2.get(k, j);
            }
            result.set(i, j, sum);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            builder.append("|");
            for (int j = 0; j < this.columns; j++) {
                builder.append(this.data[i][j]);
                if (j < this.columns - 1) {
                    builder.append(", ");
                }
            }
            builder.append("|\n");
        }
        return builder.toString();
    }

    @FunctionalInterface
    public interface MatrixCallback {
        void apply(double value, int row, int column);
    }
}