fun solve() {
    val n = readInt()
    val a = readInts()
    val m = mutableMapOf<Int, Int>()
    var ans = -1
    for (i in 0..<n) {
        m[a[i]] = m[a[i]]?.plus(1) ?: 1
        if (m[a[i]] == 3) {
            ans = a[i]
            break
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