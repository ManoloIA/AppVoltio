package com.example.appvoltio.repository

import com.example.appvoltio.data.PruebaDao
import com.example.appvoltio.model.Prueba
import kotlinx.coroutines.flow.Flow

class PruebaRepository(private val pruebaDao: PruebaDao) {

    suspend fun guardar(prueba: Prueba) {
        pruebaDao.insertar(prueba)
    }

    fun pruebasDeEquipo(equipoId: Int): Flow<List<Prueba>> =
        pruebaDao.observarPorEquipo(equipoId)
  }
