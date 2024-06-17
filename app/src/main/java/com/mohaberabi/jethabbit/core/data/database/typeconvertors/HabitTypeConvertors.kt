package com.mohaberabi.jethabbit.core.data.database.typeconvertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.DayOfWeek

@ProvidedTypeConverter
class HabitTypeConvertors {


    @TypeConverter
    fun fromCompletedDays(days: List<Long>): String = days.joinToString(",")

    @TypeConverter
    fun toCompletedDays(days: String): List<Long> = days.splitToLongList()


}

private fun String.splitToLongList(separator: String = ","): List<Long> {
    return split(separator).mapNotNull {
        try {
            it.toLong()
        } catch (e: NumberFormatException) {
            null
        }
    }
}

private fun String.splitToIntList(separator: String = ","): List<Int> {
    return split(separator).mapNotNull {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }
}

