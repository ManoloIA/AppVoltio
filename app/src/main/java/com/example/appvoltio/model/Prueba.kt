package com.example.appvoltio.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "prueba",
    foreignKeys = [
        ForeignKey(
            entity = Equipo::class,
            parentColumns = ["id"],
            childColumns = ["equipoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("equipoId")]
)

data class Prueba(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val equipoId: Int,
    val fecha: String,
    val hora: String,
    val valorMedicion: Double,
    val unidad: String,
    val observaciones: String = ""
)