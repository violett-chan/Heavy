package com.violet.heavy

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar

class Controller(private val context: Context) {
    private val pref = "heavy"
    private val favPref = "fav"
    private val timePref = "time"

    var favList: ArrayList<Fav> = ArrayList()
    var timeList: ArrayList<Time> = ArrayList()

    fun init() {
        favList = getArrayFromSharedPreferences(favPref)
        timeList = getTimeFromSharedPreferences(timePref)
    }

    fun deleteFavs() {
        favList = ArrayList()
        saveFav()
    }

    fun addToFav(id: Int, entry: Int, weight: Int, retry: Int) {
        favList = getArrayFromSharedPreferences(favPref)
        val booleanList = ArrayList<Boolean>()
        for (i in 0 until entry) {
            booleanList.add(false)
        }
        val i = Fav(id, entry, weight, retry, booleanList)
        favList.add(i)
        saveArrayToSharedPreferences(favPref, favList)
    }

    fun addToTime(hours: Int, minutes: Int, seconds: Int) {
        timeList = getTimeFromSharedPreferences(timePref)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd")
        val currentDate = dateFormat.format(calendar.time)
        val i = Time(currentDate.toInt(), hours, minutes, seconds)
        timeList.add(i)
        saveTimeToSharedPreferences(timePref, timeList)
    }

    fun saveFav() {
        saveArrayToSharedPreferences(favPref, favList)
    }

    private fun saveArrayToSharedPreferences(key: String, array: ArrayList<Fav>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(array)
        editor.putString(key, json)
        editor.apply()
    }
    private fun getArrayFromSharedPreferences(key: String): ArrayList<Fav> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<ArrayList<Fav>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }
    private fun saveTimeToSharedPreferences(key: String, array: ArrayList<Time>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(array)
        editor.putString(key, json)
        editor.apply()
    }
    private fun getTimeFromSharedPreferences(key: String): ArrayList<Time> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(pref, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<ArrayList<Time>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }
}