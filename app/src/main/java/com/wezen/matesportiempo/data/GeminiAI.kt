package com.wezen.matesportiempo.data

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException




object GeminiAI {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
    private const val API_KEY = "AIzaSyBp-dsDLcyikUfankaH7M5Lp2g99izcHnM"

    private val client = OkHttpClient()

    /**
     * Envía un prompt a la API de Gemini y devuelve la respuesta
     * @param prompt El texto que quieres enviar a Gemini
     * @param callback Función que recibe la respuesta (String) o null si hay error
     */

    val prompt = """
    Crea un problema de matemáticas para un niño de 9 años. 
    El problema debe ser una pequeña historia de no más de 100 palabras. 
    Al final, el niño debe identificar si necesita usar suma, resta, multiplicación o división para resolverlo. 
    No resuelvas el problema, solo plantea la situación y la pregunta de identificación de la operación.
    No quiero que aparezca la pregunta de que tipo de operacion es necesaria ni nada parecido
    
    Aquí tienes un ejemplo de cómo podría ser el resultado:
    "Sofía tiene una caja llena de sus cromos favoritos. Si tiene 3 estantes y quiere colocar la misma cantidad de cromos en cada uno, y tiene un total de 27 cromos, respuesta en json: {
    "problema": "Lucas tenía 24 galletas en una bandeja. Después de que él y su hermana comieron algunas, solo quedaron 18 galletas. Lucas quiere saber cuántas galletas se comieron entre los dos.",
    "respuesta_operacion": "Resta"
    }"
    """.trimIndent()

    fun sendPrompt(callback: (String?, String?) -> Unit) {

        // Construir el payload JSON
        val payload = JSONObject().apply {
            put("contents", org.json.JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", org.json.JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", prompt)
                        })
                    })
                })
            })
        }

        println("GEMINI: Pregunta enviada JSON: $payload")

        // Construir el request body
        val requestBody = RequestBody.create(
            "application/json".toMediaType(),
            payload.toString()
        )

        // Construir la request
        val request = Request.Builder()
            .url("$BASE_URL?key=$API_KEY")
            .post(requestBody)
            .build()



        // Ejecutar la llamada de forma asíncrona
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("GEMINI Error en la petición: ${e.message}")
                callback(null,null)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                if (body != null && response.isSuccessful) {
                    try {
                        // Parsear el JSON de respuesta
                        val jsonResponse = JSONObject(body)
                        val text = jsonResponse
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text")

                        // Limpiar el texto de markdown y Separamos las variables de la consulta
                        val cleanText = text
                            .replace("```json", "")
                            .replace("```", "")
                            .trim()

                        val json = JSONObject(cleanText)
                        val problema = json.getString("problema")
                        val respuestaOperacion = json.getString("respuesta_operacion")

                        callback(problema, respuestaOperacion)

                    } catch (e: Exception) {
                        println("GEMINI Error al parsear respuesta: ${e.message}")
                        println("GEMINI Body: $body")
                        callback(null,null)
                    }
                } else {
                    println("GEMINI Error en la respuesta: ${response.code}")
                    println("GEMINI Body: $body")
                    callback(null,null)
                }
            }
        })
    }

}


