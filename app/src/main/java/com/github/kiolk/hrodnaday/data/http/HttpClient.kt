package com.github.kiolk.hrodnaday.data.http

import android.util.Log
import org.json.JSONObject
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
//            val url = URL(pRequest)
            val url = URL("https://fcm.googleapis.com/fcm/send")

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            httpURLConnection.setRequestProperty("Authorization", "key=AAAAMRUKszw:APA91bHgeC92GSzXH-3j01yMANJqpy_ve3Rx-y08w_8dgNX6jOc_IbB1JuhT_ENKD3gh0R2LamHG8IuhMoGcRJVZhuuE1yJQDvDHTbOM-DbsWk6UhyU9KABjSNy7bUC8jvVMUZdyfLXo")
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            outputStream = httpURLConnection.outputStream
            bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, UTF_8))
            if (pRequest != null) {
//                bufferedWriter.write
                val jsonObject : JSONObject = JSONObject()
                val jsonParam = JSONObject()
                jsonParam.put("title", "First notification")
                jsonParam.put("body", "Message text")
                jsonObject.put("notification", jsonParam)
                bufferedWriter.write(jsonObject.toString())
            } else {
                throw IllegalArgumentException("Request without param")
            }
            bufferedWriter.flush()
            httpURLConnection.connect()

            // Read response
            val respondCod = httpURLConnection.responseCode
            Log.d("MyLogs", "Response $respondCod")
            val responseString = StringBuilder()
            reader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
            var line: String? = reader.readLine()

            while (line != null) {
                responseString.append(line)
                line = reader.readLine()
            }

            if (respondCod == HttpURLConnection.HTTP_OK) {
                gettingResponse = responseString.toString()
                Log.d("MyLogs", "Response $gettingResponse")
            } else {
                Log.d("MyLogs", "Response $respondCod")
            }
        } catch (pE: Exception) {
            pE.stackTrace
            Log.d("MyLogs", "Response $pE")
        } finally {
            outputStream?.close()

        }

        return gettingResponse
    }
}