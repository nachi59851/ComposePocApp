package com.example.composepocapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val sdf = SimpleDateFormat("MMMMM d, yyyy")

    fun longToDate(long: Long): Date {
        return Date(long)
    }

    fun dateToLong(date: Date): Long {
        return date.time / 1000 // return seconds
    }

    //October 19, 2021
    fun dateToString(date: Date): String{
        return sdf.format(date)
    }

    fun stringToDate(dateString: String): Date{
        return sdf.parse(dateString) ?: throw NullPointerException("Could not convert date string to actual date")
    }

    fun createTimestamp(): Date{
        return Date()
    }
}