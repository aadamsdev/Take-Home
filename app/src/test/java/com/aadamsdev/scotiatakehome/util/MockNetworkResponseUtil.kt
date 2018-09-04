package com.aadamsdev.scotiatakehome.util

import java.io.IOException
import java.io.InputStream
import java.util.*

object MockNetworkResponseUtil {

    @Throws(IOException::class)
    fun convertStreamToString(filename: String, classLoader: ClassLoader): String {
        val scanner = Scanner(openFile(filename, classLoader)).useDelimiter("\\A")
        return if (scanner.hasNext()) scanner.next() else ""
    }

    @Throws(IOException::class)
    private fun openFile(filename: String, classLoader: ClassLoader): InputStream {
        return classLoader.getResourceAsStream(filename)
    }
}