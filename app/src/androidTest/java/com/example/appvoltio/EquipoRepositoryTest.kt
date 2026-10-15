package com.example.appvoltio

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.appvoltio.data.AppDatabase
import com.example.appvoltio.model.Equipo
import com.example.appvoltio.repository.EquipoRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EquipoRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var repository: EquipoRepository


    @Before
    fun crearBase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repository = EquipoRepository(db.equipoDao())
    }

    @After
    fun cerrarBase(){
        db.close()
    }

    @Test
    fun guardarYLeerEquipo() = runBlocking {
        val equipo = Equipo(codigo = "TR-001", tipo = "Transformador", ubicacion = "Subestación Norte")

        repository.guardar(equipo)
        val equipos = repository.obtenerTodos()

        assertEquals(1, equipos.size)
        assertEquals("TR-001", equipos[0].codigo)
        assertEquals(1, equipos[0].id)
    }
}