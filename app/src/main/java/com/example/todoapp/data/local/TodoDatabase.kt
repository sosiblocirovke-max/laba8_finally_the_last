package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Граф Room для TodoApp: одна сущность [Task], DAO и зарегистрированные [Converters].
 *
 * **Миграции (плейсхолдер):** при увеличении `version` добавьте цепочку в `Room.databaseBuilder` в [com.example.todoapp.di.DatabaseModule]:
 * `.addMigrations(MIGRATION_1_2, MIGRATION_2_3, …)` либо временно `.fallbackToDestructiveMigration()` только на этапе разработки.
 * Пример для 1 → 2:
 * ```
 * val MIGRATION_1_2 = object : Migration(1, 2) {
 *     override fun migrate(db: SupportSQLiteDatabase) {
 *         db.execSQL("ALTER TABLE tasks ADD COLUMN …")
 *     }
 * }
 * ```
 * JSON-схемы при `exportSchema = true` пишутся в каталог `schemas/` (см. `room.schemaLocation` в `app/build.gradle.kts`).
 */
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    /** Доступ к запросам таблицы `tasks`. */
    abstract fun taskDao(): TaskDao

    companion object {
        /** Имя файла SQLite на диске (передаётся в [androidx.room.Room.databaseBuilder]). */
        const val DATABASE_NAME: String = "todo_database"
    }
}
