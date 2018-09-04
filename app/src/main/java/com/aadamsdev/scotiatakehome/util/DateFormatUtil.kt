package com.aadamsdev.scotiatakehome.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {

    fun formatDate(date: Date): String {
        return SimpleDateFormat("MMM d, yyyy hh:mm:ss aa").format(date)
    }
}