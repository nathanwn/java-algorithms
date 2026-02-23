fun solve() {
    var n = readInt()
    val a = mutableListOf<Int>()
    var p = 1
    while (n > 0) {
        val d = n % 10
        n /= 10
        if (d > 0) {
            a.add(d * p)
        }
        p *= 10
    }
    println(a.size)
    println(a.joinToString(separator = " "))
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