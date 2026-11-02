package com.example.appvoltio.repository

import com.example.appvoltio.data.UsuarioDao
import com.example.appvoltio.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun crearAdministradorInicial() {
        if (usuarioDao.contar() == 0) {
            usuarioDao.insertar(Usuario(usuario = "admin", clave = "admin123", esAdmin = true))
        }
    }

    suspend fun existe(nombre: String): Boolean {
        return usuarioDao.buscarPorNombre(nombre) != null
    }

    suspend fun guardar(usuario: Usuario) {
        usuarioDao.insertar(usuario)
    }

    suspend fun autenticar(nombre: String, clave: String): Usuario? {
        val encontrado = usuarioDao.buscarPorNombre(nombre)
        return if (encontrado != null && encontrado.clave == clave) encontrado else null
    }
}