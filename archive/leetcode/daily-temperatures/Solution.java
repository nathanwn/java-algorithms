class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        ArrayDeque<Day> stack = new ArrayDeque<>();
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty()) {
                Day top = stack.getLast();
                if (top.temp < temperatures[i]) {
                    res[top.id] = i - top.id;
                    stack.removeLast();
                } else {
                    break;
                }
            }
            stack.addLast(new Day(i, temperatures[i]));
        }
        return res;
    }
}

class Day {
    int id;
    int temp;

    Day(int id, int temp) {
        this.id = id;
        this.temp = temp;
    }
}
