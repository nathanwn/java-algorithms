fun solve() {
    val (n, q) = readInts()
    val a: List<Int> = readInts().sortedBy { it }.reversed()
    val p = Array(n) { 0 }
    p[0] = a[0]
    for (i in 1 until n) {
        p[i] = p[i - 1] + a[i]
    }
    for (j in 1..q) {
        val x = readInt()
        var low = 0
        var high = n - 1
        var ans = -1
        while (low <= high) {
            val mid = low + (high - low) / 2
            if (p[mid] >= x) {
                ans = mid + 1
                high = mid - 1
            } else {
                low = mid + 1
            }
        }
        println(ans)
    }
}

fun main() {
    val t = readInt()
    for (i in 1..t) {
        solve()
    }
}

private fun readInt() = readln().toInt()
private fun readLong() = readln().toLong()
private fun readDouble() = readln().toDouble()
private fun readStrings(): List<String> = readln().split(" ")
private fun readInts(): List<Int> = readStrings().map { it.toInt() }
private fun readLongs(): List<Long> = readStrings().map { it.toLong() }
private fun readDoubles(): List<Double> = readStrings().map { it.toDouble() }
