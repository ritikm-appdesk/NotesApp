package com.example.notesapp.utils

import java.util.*

class Constants {
    companion object{
        fun getDate(): String {
            val c = Calendar.getInstance()
            val date = c.get(Calendar.DATE)
            val month = c.get(Calendar.MONTH) + 1
            val year = c.get(Calendar.YEAR)

            return "$date/$month/$year"
        }
    }
}