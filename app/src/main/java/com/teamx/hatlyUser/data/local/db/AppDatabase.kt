package com.teamx.hatlyUser.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teamx.hatlyUser.constants.AppConstants
import com.teamx.hatlyUser.data.local.TypeConverterMV
import com.teamx.hatlyUser.data.local.dbmodel.*


@Database(
    entities = [ProductTable::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(TypeConverterMV::class,)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        )
                            .also { instance = it }
                }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                AppConstants.DbConfiguration.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }

}