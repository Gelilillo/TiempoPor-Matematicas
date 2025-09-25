package com.wezen.matesportiempo.data.Database

import android.provider.BaseColumns

object DatabaseContract {

    // Configuración general de la base de datos
    const val DATABASE_NAME = "MatesPorTiempo.db"
    const val DATABASE_VERSION = 1

    // Tabla Usuarios
    object UsuariosTable : BaseColumns {
        const val TABLE_NAME = "Usuarios"
        const val COLUMN_ID = "Id"
        const val COLUMN_NOMBRE = "Nombre"
        const val COLUMN_AVATAR = "Avatar"
        const val COLUMN_TIEMPO = "Tiempo"
        const val COLUMN_EJERCICIOS_SUMA = "ejerciciosSuma"
        const val COLUMN_FALLOS_SUMA = "FallosSuma"
        const val COLUMN_EJERCICIOS_MULTIPLICACION = "ejerciciosMultiplicacion"
        const val COLUMN_FALLOS_MULTIPLICACION = "FallosMultiplicacion"
        const val COLUMN_EJERCICIOS_RESTA = "ejerciciosResta"
        const val COLUMN_FALLOS_RESTA = "FallosResta"
        const val COLUMN_EJERCICIOS_DIVISION = "ejerciciosDivision"
        const val COLUMN_FALLOS_DIVISION = "FallosDivision"
        const val COLUMN_MEJOR = "Mejor"
        const val COLUMN_AREA_FAVORITA = "AreaFavorita"
        const val COLUMN_AREA_AMEJORAR = "AreaAmejorar"
        const val COLUMN_FECHA_CREACION = "fechaCreacion"
    }

    // Tabla Pruebas
    object PruebasTable : BaseColumns {
        const val TABLE_NAME = "Pruebas"
        const val COLUMN_ID = "Id"
        const val COLUMN_USUARIO = "usuario"
        const val COLUMN_FECHA = "Fecha"
        const val COLUMN_ACIERTOS = "Aciertos"
        const val COLUMN_FALLOS = "Fallos"
        const val COLUMN_MODULO = "Modulo"
        const val COLUMN_TIEMPO_TOTAL = "tiempoTotal"
    }

    // Tabla Errores
    object ErroresTable : BaseColumns {
        const val TABLE_NAME = "Errores"
        const val COLUMN_ID = "Id"
        const val COLUMN_USUARIO = "Usuario"
        const val COLUMN_OPERACION = "Operacion"
        const val COLUMN_NUM1 = "Num1"
        const val COLUMN_NUM2 = "Num2"
        const val COLUMN_FECHA = "fecha"
        const val COLUMN_RESPUESTA_CORRECTA = "respuestaCorrecta"
        const val COLUMN_RESPUESTA_USUARIO = "respuestaUsuario"
    }

    // SQL para crear las tablas
    const val SQL_CREATE_USUARIOS = """
        CREATE TABLE ${UsuariosTable.TABLE_NAME} (
            ${UsuariosTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${UsuariosTable.COLUMN_NOMBRE} TEXT NOT NULL,
            ${UsuariosTable.COLUMN_AVATAR} TEXT,
            ${UsuariosTable.COLUMN_TIEMPO} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_EJERCICIOS_SUMA} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_FALLOS_SUMA} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_FALLOS_MULTIPLICACION} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_EJERCICIOS_RESTA} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_FALLOS_RESTA} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_EJERCICIOS_DIVISION} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_FALLOS_DIVISION} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_MEJOR} INTEGER DEFAULT 0,
            ${UsuariosTable.COLUMN_AREA_FAVORITA} TEXT,
            ${UsuariosTable.COLUMN_AREA_AMEJORAR} TEXT,
            ${UsuariosTable.COLUMN_FECHA_CREACION} DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    """

    const val SQL_CREATE_PRUEBAS = """
        CREATE TABLE ${PruebasTable.TABLE_NAME} (
            ${PruebasTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PruebasTable.COLUMN_USUARIO} INTEGER NOT NULL,
            ${PruebasTable.COLUMN_FECHA} DATETIME DEFAULT CURRENT_TIMESTAMP,
            ${PruebasTable.COLUMN_ACIERTOS} INTEGER DEFAULT 0,
            ${PruebasTable.COLUMN_FALLOS} INTEGER DEFAULT 0,
            ${PruebasTable.COLUMN_MODULO} TEXT NOT NULL,
            ${PruebasTable.COLUMN_TIEMPO_TOTAL} INTEGER DEFAULT 0,
            FOREIGN KEY (${PruebasTable.COLUMN_USUARIO}) REFERENCES ${UsuariosTable.TABLE_NAME}(${UsuariosTable.COLUMN_ID}) ON DELETE CASCADE
        )
    """

    const val SQL_CREATE_ERRORES = """
        CREATE TABLE ${ErroresTable.TABLE_NAME} (
            ${ErroresTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ErroresTable.COLUMN_USUARIO} INTEGER NOT NULL,
            ${ErroresTable.COLUMN_OPERACION} TEXT NOT NULL,
            ${ErroresTable.COLUMN_NUM1} INTEGER NOT NULL,
            ${ErroresTable.COLUMN_NUM2} INTEGER NOT NULL,
            ${ErroresTable.COLUMN_FECHA} DATETIME DEFAULT CURRENT_TIMESTAMP,
            ${ErroresTable.COLUMN_RESPUESTA_CORRECTA} INTEGER,
            ${ErroresTable.COLUMN_RESPUESTA_USUARIO} INTEGER,
            FOREIGN KEY (${ErroresTable.COLUMN_USUARIO}) REFERENCES ${UsuariosTable.TABLE_NAME}(${UsuariosTable.COLUMN_ID}) ON DELETE CASCADE
        )
    """

    // SQL para eliminar las tablas
    const val SQL_DELETE_USUARIOS = "DROP TABLE IF EXISTS ${UsuariosTable.TABLE_NAME}"
    const val SQL_DELETE_PRUEBAS = "DROP TABLE IF EXISTS ${PruebasTable.TABLE_NAME}"
    const val SQL_DELETE_ERRORES = "DROP TABLE IF EXISTS ${ErroresTable.TABLE_NAME}"

    // Índices para mejorar el rendimiento
    val SQL_CREATE_INDEXES = arrayOf(
        "CREATE INDEX IF NOT EXISTS idx_usuarios_nombre ON ${UsuariosTable.TABLE_NAME}(${UsuariosTable.COLUMN_NOMBRE})",
        "CREATE INDEX IF NOT EXISTS idx_pruebas_usuario ON ${PruebasTable.TABLE_NAME}(${PruebasTable.COLUMN_USUARIO})",
        "CREATE INDEX IF NOT EXISTS idx_pruebas_fecha ON ${PruebasTable.TABLE_NAME}(${PruebasTable.COLUMN_FECHA})",
        "CREATE INDEX IF NOT EXISTS idx_errores_usuario ON ${ErroresTable.TABLE_NAME}(${ErroresTable.COLUMN_USUARIO})",
        "CREATE INDEX IF NOT EXISTS idx_errores_fecha ON ${ErroresTable.TABLE_NAME}(${ErroresTable.COLUMN_FECHA})",
        "CREATE INDEX IF NOT EXISTS idx_errores_operacion ON ${ErroresTable.TABLE_NAME}(${ErroresTable.COLUMN_OPERACION})"
    )
}