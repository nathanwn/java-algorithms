import kotlin.math.max

fun solve() {
    val n = readInt()
    val (s, t) = readStrings()
    if (s.toList().sorted() == t.toList().sorted()) {
        println("YES")
    } else {
        println("NO")
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
