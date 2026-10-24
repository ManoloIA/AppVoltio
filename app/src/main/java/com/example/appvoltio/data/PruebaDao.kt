package com.example.appvoltio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appvoltio.model.Prueba
import kotlinx.coroutines.flow.Flow

@Dao
interface PruebaDao {

    @Insert
    suspend fun insertar(prueba: Prueba)

    @Query("SELECT * FROM prueba WHERE equipoId = :equipoId ORDER BY fecha DESC, hora DESC")
    fun observarPorEquipo(equipoId: Int): Flow<List<Prueba>>
}