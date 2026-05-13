package com.example.todoapp.data.local

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Конвертеры Room для [java.time.LocalDateTime]: SQLite хранит числа, доменная модель — типы времени.
 * Время нормализуется в **UTC**, чтобы значения не зависели от часового пояса устройства при чтении из БД.
 */
class Converters {

    /** Сохраняет [LocalDateTime] как epoch-millis в UTC; `null` остаётся `null`. */
    @TypeConverter
    fun localDateTimeToMillis(value: LocalDateTime?): Long? =
        value?.toInstant(ZoneOffset.UTC)?.toEpochMilli()

    /** Восстанавливает [LocalDateTime] из epoch-millis в UTC; `null` остаётся `null`. */
    @TypeConverter
    fun millisToLocalDateTime(value: Long?): LocalDateTime? =
        value?.let { millis ->
            LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC)
        }
}
