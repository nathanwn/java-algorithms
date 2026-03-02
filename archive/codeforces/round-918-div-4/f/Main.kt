import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.system.exitProcess

fun solve() {
    val n = nextInt()
    val vals = IntArray(size = 2 * n) { nextInt() }
    val c = CoordinateCompressor(vals)
    val ps = mutableListOf<Person>()
    for (i in 0 until n) {
        val start = c.find(vals[2 * i])
        val end = c.find(vals[2 * i + 1])
        ps.add(Person(start, end))
    }
    ps.sortBy { it.start }
    val t = FenwickTree(2 * n)
    var ans: Long = 0
    for (i in 0 until n) {
        val p = ps[i]
        ans += t.sum(p.end + 1, 2 * n - 1)
        t.add(p.end, 1)
    }
    println(ans)
}

data class Person(val start: Int, val end: Int)

class FenwickTree(val n: Int) {
    val t = LongArray(size = n) { 0 }

    constructor(a: List<Int>) : this(a.size) {
        for (i in 0 until n) {
            t[i] += a[i];
            val r = i or (i + 1);
            if (r < n) {
                t[r] += t[i];
            }
        }
    }

    fun preSum(id: Int): Long {
        var res: Long = 0
        var i = id
        while (i >= 0) {
            res += t[i]
            i = (i and (i + 1)) - 1
        }
        return res
    }

    fun sum(l: Int, r: Int): Long {
        return preSum(r) - preSum(l - 1)
    }

    fun add(id: Int, delta: Long) {
        var i = id
        while (i < n) {
            t[i] += delta
            i = i or (i + 1)
        }
    }
}

class CoordinateCompressor(a: IntArray) {
    val c: IntArray = a.toList().sortedBy { it }.distinct().toIntArray()

    fun find(x: Int): Int {
        return c.binarySearch(x)
    }
}


fun main() {
    val t = nextInt()
    // val t = 1
    for (i in 1..t) {
        solve()
    }
}

class InputReader(inStream: InputStream) {
    val reader = BufferedReader(InputStreamReader(inStream), 32768)
    var tokenizer = StringTokenizer("")

    fun hasNext(): Boolean {
        while (!tokenizer.hasMoreTokens()) {
            val line = try {
                reader.readLine()
            } catch (e: IOException) {
                e.printStackTrace()
                exitProcess(1)
            }
            if (line == null) {
                return false
            }
            tokenizer = StringTokenizer(line)
        }
        return true
    }

    fun next(): String {
        if (!hasNext()) {
            throw RuntimeException()
        }
        return tokenizer.nextToken()
    }
}

val reader = InputReader(System.`in`)
fun nextInt(): Int = reader.next().toInt()
fun nextLong(): Long = reader.next().toLong()
fun nextDouble(): Double = reader.next().toDouble()
