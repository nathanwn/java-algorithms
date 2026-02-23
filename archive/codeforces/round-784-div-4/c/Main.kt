fun solve() {
    val n = readInt()
    val a = readInts()
    val r0 = a[0] and 1
    val r1 = a[1] and 1
    for (i in 2..<n step 2) {
        if (a[i] and 1 != r0) {
            println("No")
            return
        }
    }
    for (i in 3..<n step 2) {
        if (a[i] and 1 != r1) {
            println("No")
            return
        }
    }
    println("Yes")
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