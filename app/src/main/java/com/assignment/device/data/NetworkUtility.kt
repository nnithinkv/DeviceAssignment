package com.assignment.device.data

import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NetworkUtility {

    companion object {

        suspend fun request(url: URL): String {
            return suspendCancellableCoroutine { continuation ->
                try {
                    val reader: BufferedReader

                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"
                        reader = BufferedReader(InputStreamReader(inputStream) as Reader?)

                        val response = StringBuffer()
                        var inputLine = reader.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = reader.readLine()
                        }
                        reader.close()

                        if (continuation.isActive) {
                            continuation.resume(response.toString())
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    if (continuation.isActive) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }
}