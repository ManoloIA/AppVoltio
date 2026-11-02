package com.example.appvoltio.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appvoltio.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE usuario = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): Usuario?

    @Query("SELECT COUNT(*) FROM usuario")
    suspend fun contar(): Int
}