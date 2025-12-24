class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = searchRow(matrix, target);
        if (row == -1) return false;
        int col = searchCol(matrix[row], target);
        return col != -1;
    }

    public int searchCol(int[] row, int target) {
        int firstCol = 0;
        int lastCol = row.length;
        int col = -1;
        while (firstCol < lastCol) {
            int midCol = firstCol + (lastCol - firstCol) / 2;
            if (row[midCol] == target) {
                return midCol;
            } else if (row[midCol] < target) {
                firstCol = midCol + 1;
            } else {
                lastCol = midCol;
            }
        }
        return -1;
    }

    public int searchRow(int[][] matrix, int target) {
        int firstRow = 0;
        int lastRow = matrix.length;
        int row = -1;
        while (firstRow < lastRow) {
            int midRow = firstRow + (lastRow - firstRow) / 2;
            if (matrix[midRow][0] <= target) {
                row = midRow;
                firstRow = midRow + 1;
            } else {
                lastRow = midRow;
            }
        }
        return row;
    }
}
