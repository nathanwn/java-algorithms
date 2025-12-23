class MinStack {
    ArrayDeque<Integer> vals;
    ArrayDeque<Integer> mins;

    public MinStack() {
        vals = new ArrayDeque<>();
        mins = new ArrayDeque<>();
    }

    public void push(int val) {
        vals.addLast(val);
        if (mins.isEmpty()) {
            mins.addLast(val);
        } else {
            mins.addLast(Math.min(mins.getLast(), val));
        }
    }

    public void pop() {
        vals.removeLast();
        mins.removeLast();
    }

    public int top() {
        return vals.getLast();
    }

    public int getMin() {
        return mins.getLast();
    }
}
