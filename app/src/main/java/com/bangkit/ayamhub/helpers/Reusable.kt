package com.bangkit.ayamhub.helpers

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.MessageResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object Reusable {
    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun getCity(address: String): String {
        val cityName = address.split(", ")
        return cityName[1]
    }

    fun getChickenAge(date: String): String {
        val actualDate = date.split("T")
        val chickenDate = LocalDate.parse(actualDate[0])
        val currentDate = LocalDate.now()
        return ChronoUnit.DAYS.between(chickenDate, currentDate).toString()
    }

    fun urlToBitmap(context: Context, url: String, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(bitmap)
                }
            })
    }
}