class Matrix {
    /** @private @type {number} */
    rows;
    /** @private @type {number} */
    columns;
    /** @private @type {number[][]} */
    data;
    /**
     *  @description create a new matrix
     *  @param {number} rows Number of rows
     *  @param {number} columns Number of columns
    */
    constructor(rows, columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = Array.from({ length: rows }, () => Array(columns).fill(0));
    }
    getColumnsSize() { return this.columns; }
    getRowsSize()    { return this.rows; }
    /**
     * @description set a value in the matrix
     * @param {number} rows Number of rows
     * @param {number} columns Number of columns
     * @param {any} value Value to set
     */
    set(row, column, value) {
        if (! this.isInRange(row, column)) throw new Error("out of range");
        this.data[row][column] = value;
    }
    /**
     * @description set a row in the matrix
     * @param {number} row Number of rows
     * @param {any[]} values Values to set in the row
     */
    setRow(row, values) {
        if (! this.isInRange(row, 0)) throw new Error("out of range");
        if (values.length !== this.columns) throw new Error("invalid length");
        this.data[row][i] = [...values];
    }
    setColumn(column, values) {
        if (! this.isInRange(0, column)) throw new Error("out of range");
        if (values.length !== this.rows) throw new Error("invalid length");
        for (let i = 0; i < this.rows; i++) {
            this.data[i][column] = values[i];
        }
    }
    /**
     * @description set all values in the matrix
     * @param {any[][]} values Values to set in the matrix
    */
    setAll(values) {
        if (values.length !== this.rows || values[0].length !== this.columns) throw new Error("invalid length");
        for (let i = 0; i < this.rows; i++)
        for (let j = 0; j < this.columns; j++) {
            this.data[i][j] = values[i][j];
        }
    }
    /**
     * @description get a value in the matrix
     * @param {number} rows Number of rows
     * @param {number} columns Number of columns
     * @returns {any}
     */
    get(row, column) {
        if (! this.isInRange(row, column)) throw new Error("out of range");
        return this.data[row][column];
    }
    /**
     * @description get a row in the matrix
     * @param {number} row Number of rows
     * @returns {any[]}
     */
    getRow(row) {
        if (! this.isInRange(row, 0)) throw new Error("out of range");
        return [...this.data[row]];
    }
    /**
     * @description get a column in the matrix
     * @param {number} column Number of columns
     * @returns {any[]}
     */
    getColumn(column) {
        if (! this.isInRange(0, column)) throw new Error("out of range");
        return this.data.map(row => row[column]);;
    }
    /**
     * @protected
     * @description check if a value is in the matrix
     * @param {number} rows Number of rows
     * @param {number} columns Number of columns
     * @returns {boolean}
     */
    isInRange(row, column) {
        return (
            row >= 0 &&
            row < this.rows &&
            column >= 0 &&
            column < this.columns
        );
    }
    /**
     * @description iterate over the matrix
     * @param {(value: any, row: number, column: number) => void} callback Callback function
    */
    forEach(callback) {
        for (let i = 0; i < this.rows; i++)
        for (let j = 0; j < this.columns; j++) {
            callback(this.get(i, j), i, j);
        }
    }
    /**
     * @description clone this matrix
     * @returns {Matrix}
     */
    clone() {
        const clone = new Matrix(this.rows, this.columns);
        for (let i = 0; i < this.rows; i++)
        for (let j = 0; j < this.columns; j++) {
            clone.set(i, j, this.get(i, j));
        }
        return clone;
    }
    static Multiply(matrix1, matrix2) {
        if (matrix1.getColumnsSize() !== matrix2.getRowsSize()) throw new Error("invalid dimensions");
        const result = new Matrix(matrix1.getRowsSize(), matrix2.getColumnsSize());
        for (let filaCount = 0; filaCount < result.getRowsSize(); filaCount++)
        for (let ColumnCount = 0; ColumnCount < result.getColumnsSize(); ColumnCount++) {
            let sum = 0;
            for (let k = 0; k < matrix1.getColumnsSize(); k++) {
                sum += matrix1.get(filaCount, k) * matrix2.get(k, ColumnCount);
            }
            result.set(filaCount, ColumnCount, sum);
        }
        return result;
    }
    /**
     * @description clone a matrix
     * @param {Matrix | null} matrix Matrix to clone
     * @returns {Matrix}
     */
    static clone(matrix) {
        const clone = new Matrix(this.rows, this.columns);
        for (let i = 0; i < this.rows; i++)
        for (let j = 0; j < this.columns; j++) {
            clone.set(i, j, matrix.get(i, j));
        }
        return clone;
    }
    toString() {
        return this.data.map(row => `|${row.join(", ")}|`).join("\n");
    }
}
export default Matrix;