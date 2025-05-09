package com.example.initiations.util.db_migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


class Migration_3_4 : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Create a new table with the correct schema (personAge as TEXT)
        db.execSQL("""
            CREATE TABLE initiationPerson_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                personName TEXT NOT NULL,
                personAge TEXT NOT NULL,
                gender TEXT NOT NULL,
                education TEXT NOT NULL,
                fullAddress TEXT NOT NULL,
                masterName TEXT NOT NULL,
                introducerName TEXT NOT NULL,
                guarantorName TEXT NOT NULL,
                templeName TEXT NOT NULL,
                meritFee TEXT NOT NULL,
                initiationDate TEXT NOT NULL,
                dharmaMeetingDate TEXT NOT NULL,
                is2DaysDharmaClassAttend INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())

        // 2. Copy data from old table into the new table
        db.execSQL("""
            INSERT INTO initiationPerson_new (
                id, personName, personAge, gender, education, fullAddress,
                masterName, introducerName, guarantorName, templeName,
                meritFee, initiationDate, dharmaMeetingDate, is2DaysDharmaClassAttend
            )
            SELECT 
                id, personName, personAge, gender, education, fullAddress,
                masterName, introducerName, guarantorName, templeName,
                meritFee, initiationDate, dharmaMeetingDate, is2DaysDharmaClassAttend
            FROM initiationPerson
        """.trimIndent())

        // 3. Drop the old table
        db.execSQL("DROP TABLE initiationPerson")

        // 4. Rename the new table to the original table name
        db.execSQL("ALTER TABLE initiationPerson_new RENAME TO initiationPerson")
    }
}

