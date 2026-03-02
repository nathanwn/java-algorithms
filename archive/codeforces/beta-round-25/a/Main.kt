fun solve() {
    val n = readInt()
    val a = readInts()
    var countEven = 0
    var countOdd = 0
    for (i in 0 until n) {
        if (a[i] % 2 == 0) {
            countEven++
        } else {
            countOdd++
        }
    }
    var ans = 0
    if (countOdd == 1) {
        for (i in 0 until n) {
            if (a[i] % 2 != 0) {
                ans = i + 1
                break
            }
        }
    } else if (countEven == 1) {
        for (i in 0 until n) {
            if (a[i] % 2 == 0) {
                ans = i + 1
                break
            }
        }
    } else throw AssertionError()
    if (ans == 0) throw AssertionError()
    println(ans)
}

fun main() {
    // val t = readInt()
    val t = 1
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
