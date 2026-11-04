package com.example.appvoltio.repository

import com.example.appvoltio.data.EquipoDao
import com.example.appvoltio.model.Equipo
import kotlinx.coroutines.flow.Flow

class EquipoRepository(private val equipoDao: EquipoDao) {

    suspend fun guardar(equipo: Equipo) {
        equipoDao.insertar(equipo)
    }

    suspend fun obtenerTodos(): List<Equipo> {
        return equipoDao.obtenerTodos()
    }

    suspend fun eliminar(equipo: Equipo) {
        equipoDao.eliminar(equipo)
    }

    fun equipoPorId(id: Int): Flow<Equipo?> = equipoDao.observarPorId(id)
}

