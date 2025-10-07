package com.jorgeromo.androidClassMp1.secondpartial.home

import com.jorgeromo.androidClassMp1.secondpartial.home.model.Event
import com.jorgeromo.androidClassMp1.secondpartial.home.network.HomeApi
import java.io.IOException

class HomeRepository(private val api: HomeApi) {

    suspend fun getEvents(): Result<List<Event>> {
        return try {
            val resp = api.getEvents()
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                val msg = resp.errorBody()?.string().orEmpty()
                val errorMsg = "HTTP ${resp.code()} - ${resp.message()}\n$msg"
                Result.failure(Exception(errorMsg.ifBlank { "Error al obtener eventos" }))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Sin conexión. Verifica tu red. (${e.localizedMessage})"))
        } catch (e: Exception) {
            Result.failure(Exception("Error inesperado: ${e.localizedMessage}"))
        }
    }
}
