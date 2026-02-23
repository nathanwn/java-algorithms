fun solve() {
    val s = readln()
    var s1 = 0
    for (i in 0..2) {
        s1 += s[i] - '0'
    }
    var s2 = 0
    for (i in 3..5) {
        s2 += s[i] - '0'
    }
    println(if (s1 == s2) "YES" else "NO")
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