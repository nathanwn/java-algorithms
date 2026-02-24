import kotlin.math.max

fun solve() {
    val (n, m) = readInts()
    val g = mutableListOf<List<Int>>()
    for (i in 1..n) {
        g.add(readInts())
    }
    val leftDiags = mutableMapOf<Int, Int>()
    val rightDiags = mutableMapOf<Int, Int>()
    for (i in 0..<n) {
        for (j in 0..<m) {
            val l = i - j
            val r = i + j
            leftDiags[l] = leftDiags.getOrDefault(l, 0) + g[i][j]
            rightDiags[r] = rightDiags.getOrDefault(r, 0) + g[i][j]
        }
    }
    var ans = 0
    for (i in 0..<n) {
        for (j in 0..<m) {
            val l = i - j
            val r = i + j
            val res = leftDiags.getOrDefault(l, 0) + rightDiags.getOrDefault(r, 0) - g[i][j]
            ans = max(ans, res)
        }
    }
    println(ans)
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
