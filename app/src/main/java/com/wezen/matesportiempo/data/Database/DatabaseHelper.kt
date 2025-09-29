package com.wezen.matesportiempo.data.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.wezen.matesportiempo.data.Database.DatabaseContract.DATABASE_NAME
import com.wezen.matesportiempo.data.Database.DatabaseContract.DATABASE_VERSION
import com.wezen.matesportiempo.data.User


class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    companion object {
        private const val TAG = "DatabaseHelper"

        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseHelper(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            // Crear las tablas
            db.execSQL(DatabaseContract.SQL_CREATE_USUARIOS)
            db.execSQL(DatabaseContract.SQL_CREATE_PRUEBAS)
            db.execSQL(DatabaseContract.SQL_CREATE_ERRORES)

            // Crear índices
            DatabaseContract.SQL_CREATE_INDEXES.forEach { index ->
                db.execSQL(index)
            }

            Log.d(TAG, "Base de datos creada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al crear la base de datos", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL(DatabaseContract.SQL_DELETE_ERRORES)
            db.execSQL(DatabaseContract.SQL_DELETE_PRUEBAS)
            db.execSQL(DatabaseContract.SQL_DELETE_USUARIOS)
            onCreate(db)
            Log.d(TAG, "Base de datos actualizada de versión $oldVersion a $newVersion")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar la base de datos", e)
        }
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    // === MÉTODOS PARA USUARIOS ===

    fun createUser(nombre: String, avatar: String? = null): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UsuariosTable.COLUMN_NOMBRE, nombre)
            put(DatabaseContract.UsuariosTable.COLUMN_AVATAR, avatar)
        }

        return try {
            val id = db.insert(DatabaseContract.UsuariosTable.TABLE_NAME, null, values)
            Log.d(TAG, "Usuario creado con ID: $id")
            id
        } catch (e: Exception) {
            Log.e(TAG, "Error al crear usuario", e)
            -1
        }
    }

    fun getAllUsers(): List<User> {
        val usuarios = mutableListOf<User>()
        val db = readableDatabase

        val cursor = db.query(
            DatabaseContract.UsuariosTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DatabaseContract.UsuariosTable.COLUMN_NOMBRE
        )

        cursor.use {
            while (it.moveToNext()) {
                usuarios.add(cursorToUsuario(it))
            }
        }

        return usuarios
    }

    fun getUserById(id: Long): User? {
        val db = readableDatabase
        val cursor = db.query(
            DatabaseContract.UsuariosTable.TABLE_NAME,
            null,
            "${DatabaseContract.UsuariosTable.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            return if (it.moveToFirst()) {
                cursorToUsuario(it)
            } else {
                null
            }
        }
    }

    fun updateUser(usuario: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UsuariosTable.COLUMN_NOMBRE, usuario.nombre)
            put(DatabaseContract.UsuariosTable.COLUMN_AVATAR, usuario.avatar)
            put(DatabaseContract.UsuariosTable.COLUMN_TIEMPO, usuario.tiempo)
            put(DatabaseContract.UsuariosTable.COLUMN_MEJOR, usuario.mejor)
            put(DatabaseContract.UsuariosTable.COLUMN_AREA_FAVORITA, usuario.areaFavorita)
            put(DatabaseContract.UsuariosTable.COLUMN_AREA_AMEJORAR, usuario.areaAMejorar)
        }

        return try {
            val rowsUpdated = db.update(
                DatabaseContract.UsuariosTable.TABLE_NAME,
                values,
                "${DatabaseContract.UsuariosTable.COLUMN_ID} = ?",
                arrayOf(usuario.id.toString())
            )
            rowsUpdated > 0
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar usuario", e)
            false
        }
    }

    fun updateUserStats(usuarioId: Long, operacion: String, esAcierto: Boolean = true): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        when (operacion.lowercase()) {
            "suma" -> {
                values.put(
                    DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA,
                    "${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA} + 1")
                if (!esAcierto) {
                    values.put(
                        DatabaseContract.UsuariosTable.COLUMN_FALLOS_SUMA,
                        "${DatabaseContract.UsuariosTable.COLUMN_FALLOS_SUMA} + 1")
                }
            }
            "resta" -> {
                values.put(
                    DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA,
                    "${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA} + 1")
                if (!esAcierto) {
                    values.put(
                        DatabaseContract.UsuariosTable.COLUMN_FALLOS_RESTA,
                        "${DatabaseContract.UsuariosTable.COLUMN_FALLOS_RESTA} + 1")
                }
            }
            "multiplicacion" -> {
                values.put(
                    DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION,
                    "${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} + 1")
                if (!esAcierto) {
                    values.put(
                        DatabaseContract.UsuariosTable.COLUMN_FALLOS_MULTIPLICACION,
                        "${DatabaseContract.UsuariosTable.COLUMN_FALLOS_MULTIPLICACION} + 1")
                }
            }
            "division" -> {
                values.put(
                    DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION,
                    "${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION} + 1")
                if (!esAcierto) {
                    values.put(
                        DatabaseContract.UsuariosTable.COLUMN_FALLOS_DIVISION,
                        "${DatabaseContract.UsuariosTable.COLUMN_FALLOS_DIVISION} + 1")
                }
            }
            else -> {
                Log.w(TAG, "Operación no reconocida: $operacion")
                return false
            }
        }

        return try {
            // Usando SQL raw para poder usar expresiones
            val sql = when (operacion.lowercase()) {
                "suma" -> if (esAcierto) {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                } else {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA} + 1, ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_SUMA} = ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_SUMA} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                }
                "resta" -> if (esAcierto) {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                } else {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA} + 1, ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_RESTA} = ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_RESTA} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                }
                "multiplicacion" -> if (esAcierto) {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                } else {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION} + 1, ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_MULTIPLICACION} = ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_MULTIPLICACION} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                }
                "division" -> if (esAcierto) {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                } else {
                    "UPDATE ${DatabaseContract.UsuariosTable.TABLE_NAME} SET ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION} = ${DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION} + 1, ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_DIVISION} = ${DatabaseContract.UsuariosTable.COLUMN_FALLOS_DIVISION} + 1 WHERE ${DatabaseContract.UsuariosTable.COLUMN_ID} = ?"
                }
                else -> return false
            }

            db.execSQL(sql, arrayOf(usuarioId.toString()))
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar estadísticas del usuario", e)
            false
        }
    }

    private fun cursorToUsuario(cursor: Cursor): User {

        return User(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_ID)),
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_NOMBRE)),
            avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_AVATAR)),
            tiempo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_TIEMPO)),
            ejerciciosSuma = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_SUMA)),
            fallosSuma = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_FALLOS_SUMA)),
            ejerciciosMultiplicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_MULTIPLICACION)),
            fallosMultiplicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_FALLOS_MULTIPLICACION)),
            ejerciciosResta = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_RESTA)),
            fallosResta = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_FALLOS_RESTA)),
            ejerciciosDivision = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_EJERCICIOS_DIVISION)),
            fallosDivision = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_FALLOS_DIVISION)),
            mejor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_MEJOR)),
            areaFavorita = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_AREA_FAVORITA)),
            areaAMejorar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_AREA_AMEJORAR)),
            fechaCreacion = null
            //fechaCreacion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuariosTable.COLUMN_FECHA_CREACION))
        )
    }
}