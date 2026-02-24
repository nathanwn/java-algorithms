import kotlin.math.abs
import kotlin.math.min

fun solve() {
    val (n, m) = readInts()
    val s = mutableListOf<String>()
    for (i in 1..n) {
        s.add(readln())
    }
    var minDiff = Integer.MAX_VALUE
    for (i in 0..<n) {
        for (j in i + 1..<n) {
            val diff = getDiff(s[i], s[j])
            minDiff = min(minDiff, diff)
        }
    }
    println(minDiff)
}

fun getDiff(s1: String, s2: String): Int {
    var res = 0
    for (i in 0..<s1.length) {
        res += abs(s1[i] - s2[i])
    }
    return res
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
