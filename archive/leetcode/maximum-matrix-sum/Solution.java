class Solution {
    public long maxMatrixSum(int[][] matrix) {
        int n = matrix.length;
        // Observations:
        // - Start in 1d, i.e. an array. The result depends on how many
        // negative number are there.
        // - Assuming there is no 0 in the array (which is a special case).
        //   - Case 1: If there is an even number of -, we can always convert
        //             all of them into +, by putting pairs of - together.
        //   - Case 2: If there is an odd number of -, we can only reduce
        //             down to 1 - at minimum (unless there is a 0).
        // - The same property applies for a 2d array.
        int numZeros = 0;
        int numNeg = 0;
        long sum = 0;
        int minAbs = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = matrix[i][j];
                int absX = Math.abs(x);
                sum += Math.abs(absX);
                minAbs = Math.min(minAbs, absX);
                if (x < 0) {
                    numNeg++;
                } else if (x == 0) {
                    numZeros++;
                }
            }
        }
        if (numNeg % 2 == 0) {
            return sum;
        } else {
            if (numZeros > 0) {
                return sum;
            } else {
                return sum - 2 * minAbs;
            }
        }
    }
}
