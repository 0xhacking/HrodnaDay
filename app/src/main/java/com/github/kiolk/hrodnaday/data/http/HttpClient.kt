package com.github.kiolk.hrodnaday.data.http

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.text.Charsets.UTF_8

class HttpClient {

    fun get(request: String): String {

        var gettingResponse = ""
        var reader: BufferedReader? = null

        try {
            val url = URL(request)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            val responseCod = connection.responseCode
            val responseString = StringBuilder()
            reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String? = reader.readLine()

            while (line != null) {
                responseString.append(line)
                line = reader.readLine()
            }

            if (responseCod == HttpURLConnection.HTTP_OK) {
                gettingResponse = responseString.toString()
            } else {
            }
        } catch (pE: Exception) {
            pE.stackTrace
        } finally {
            reader?.close()
        }

        return gettingResponse
    }

    fun post(pRequest: String): String {
        var gettingResponse = ""
        var outputStream: OutputStream? = null
        var bufferedWriter: BufferedWriter? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(pRequest)

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            outputStream = httpURLConnection.outputStream
            bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, UTF_8))
            if (pRequest != null) {
//                bufferedWriter.write(pRequest)
            } else {
                throw IllegalArgumentException("Request without param")
            }
            bufferedWriter.flush()
            httpURLConnection.connect()

            // Read response
            val respondCod = httpURLConnection.responseCode
            val responseString = StringBuilder()
            reader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
            var line: String? = reader.readLine()

            while (line != null) {
                responseString.append(line)
                line = reader.readLine()
            }

            if (respondCod == HttpURLConnection.HTTP_OK) {
                gettingResponse = responseString.toString()
            } else {
            }
        } catch (pE: Exception) {
            pE.stackTrace
        } finally {
            outputStream?.close()

        }

        return gettingResponse
    }
}