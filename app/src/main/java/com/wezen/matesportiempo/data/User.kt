package com.wezen.matesportiempo.data

import android.provider.BaseColumns
import java.util.Date

data class User(
    val id: Long? = null,
    val nombre: String,
    val avatar: String?,
    val tiempo: Int,
    val ejerciciosSuma: Int,
    val fallosSuma: Int,
    val ejerciciosMultiplicacion: Int,
    val fallosMultiplicacion: Int,
    val ejerciciosResta: Int,
    val fallosResta: Int,
    val ejerciciosDivision: Int,
    val fallosDivision: Int,
    val mejor: String?,
    val areaFavorita: String?,
    val areaAMejorar: String?,
    val fechaCreacion: Date?
) {
    // Constructor secundario
    constructor(nombre: String) : this(
        id = null,
        nombre = nombre,
        avatar = null,
        tiempo = 0,
        ejerciciosSuma = 0,
        fallosSuma = 0,
        ejerciciosMultiplicacion = 0,
        fallosMultiplicacion = 0,
        ejerciciosResta = 0,
        fallosResta = 0,
        ejerciciosDivision = 0,
        fallosDivision = 0,
        mejor = null,
        areaFavorita = null,
        areaAMejorar = null,
        fechaCreacion = Date()
    )
}


