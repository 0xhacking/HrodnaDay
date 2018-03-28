package com.github.kiolk.hrodnaday.data.models

import java.io.Serializable

data class Museum(var nameOfMuseum: String = "Museum",
                  var museumUrl: String = "http",
                  var dateOfFoundation: Long = 0,
                  var museumLatitude: Float = 0.0F,
                  var museumShirota: Float = 0.0F,
                  var museumAddress: String = "address",
                  var museumDescription: String = "description",
                  var phones: List<Phone> = emptyList(),
                  var chef: String = "chef",
                  var timeWork : WorkTime
) : Serializable

data class Phone(var prefixs: String = "+375",
                 var code: String = "152",
                 var phoneNumber: String = "000000")

data class WorkTime(var mondayStart: Float = 0.0F,
                    var mondayEnd: Float = 0.0F,
                    var mondayBreak: String = "No"){
    fun showWorkTime(array: Array<String>) : String{
        return mondayStart.toString() + mondayBreak + mondayEnd
    }
}