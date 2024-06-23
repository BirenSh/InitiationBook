package com.example.initiations.util

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_1_2 : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE initiationPerson ADD COLUMN initiationDate TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE initiationPerson ADD COLUMN meritFee TEXT NOT NULL DEFAULT ''")
        db.execSQL("ALTER TABLE initiationPerson ADD COLUMN is2DaysDharmaClassAttend INTEGER NOT NULL DEFAULT 0")
    }

}