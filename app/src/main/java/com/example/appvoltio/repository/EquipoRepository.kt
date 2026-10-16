package com.example.appvoltio.repository

import com.example.appvoltio.data.EquipoDao
import com.example.appvoltio.model.Equipo

class EquipoRepository(private val equipoDao: EquipoDao) {

    suspend fun guardar(equipo: Equipo) {
        equipoDao.insertar(equipo)
    }

    suspend fun obtenerTodos(): List<Equipo> {
        return equipoDao.obtenerTodos()
    }
}