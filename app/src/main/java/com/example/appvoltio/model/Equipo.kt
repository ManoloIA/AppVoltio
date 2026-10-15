package com.example.appvoltio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipo")
data class Equipo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String,
    val tipo: String,
    val ubicacion: String,
    val fotoUri: String? = null
)