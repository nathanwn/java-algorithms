fun solve() {
    val rating = readInt()
    if (rating < 1400) {
        println("Division 4")
    } else if (rating < 1600) {
        println("Division 3")
    } else if (rating < 1900) {
        println("Division 2")
    } else {
        println("Division 1")
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