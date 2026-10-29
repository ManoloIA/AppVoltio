package com.example.appvoltio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appvoltio.model.Equipo
import com.example.appvoltio.model.Prueba

@Database(entities = [Equipo::class, Prueba::class], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao
    abstract fun pruebaDao(): PruebaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtener(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appvoltio.db"
                ).build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}