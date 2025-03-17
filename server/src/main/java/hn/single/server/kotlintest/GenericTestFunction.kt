package hn.single.server.kotlintest

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek

fun <T> singletonList(item: T): List<T> {
    return listOf(item)
}

fun <T> T.toStringData(): String {
    return this.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    println("singletonList = ${singletonList(1)}")
    val demo = "123456"
    println(demo.substring(2))
    println(demo.takeLast(demo.length - 6))
    //println("All day of weeks: ${DayOfWeek.entries.joinToString()}")
    val dayOfWeeks = mutableListOf<DayOfWeek>()
    val days = "1112345678"
    days.forEachIndexed { index, value ->
        if (index != 0 && index in 1..7) {
            dayOfWeeks.add(index.toDayOfWeek())
        }
    }
    println("Day of weeks selected: $dayOfWeeks")
    DayOfWeek.entries.forEach {
        println("Day of week: $it has value ${it.value}")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Int.toDayOfWeek(): DayOfWeek {
    return when(this) {
        1 -> DayOfWeek.MONDAY
        2 -> DayOfWeek.TUESDAY
        3 -> DayOfWeek.WEDNESDAY
        4 -> DayOfWeek.THURSDAY
        5 -> DayOfWeek.FRIDAY
        6 -> DayOfWeek.SATURDAY
        7 -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Invalid day of week")
    }
}