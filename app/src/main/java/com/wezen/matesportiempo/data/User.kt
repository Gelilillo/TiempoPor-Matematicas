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
)


