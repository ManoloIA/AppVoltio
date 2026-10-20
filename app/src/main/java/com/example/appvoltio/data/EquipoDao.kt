package com.example.appvoltio.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.appvoltio.model.Equipo

@Dao
interface EquipoDao {

    @Insert
    suspend fun insertar(equipo: Equipo)

    @Query("SELECT * FROM equipo")
    suspend fun obtenerTodos(): List<Equipo>
}