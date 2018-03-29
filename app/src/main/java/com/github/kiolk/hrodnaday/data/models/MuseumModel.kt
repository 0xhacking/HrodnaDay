package com.github.kiolk.hrodnaday.data.models

import java.io.Serializable

data class Museum(var nameOfMuseum: String = "Museum",
                  var museumLabel: String = "",
                  var museumUrl: String = "http",
                  var dateOfFoundation: Long = 0,
                  var museumLatitude: Float = 53.701312F,
                  var museumShirota: Float = 23.812323F,
                  var museumAddress: String = "address",
                  var museumDescription: String = "description",
                  var phones: List<Phone> = listOf(Phone(), Phone("+375", "152", "720590")),
                  var chef: String = "chef",
                  var timeWork : WorkTime = WorkTime()
) : Serializable

data class Phone(var prefixs: String = "+375",
                 var code: String = "152",
                 var phoneNumber: String = "721485") : Serializable {
    fun getPhone(): String {
        return prefixs + code + phoneNumber
    }
}

data class WorkTime(var mondayStart: Float = 0.0F,
                    var mondayEnd: Float = 0.0F,
                    var mondayBreak: String = "No",
                    var thursdayStart: Float = 0.0F,
                    var thursdayEnd : Float = 0.0F,
                    var thursdayBreak : String = "No",
                    var wednesdayStart: Float = 0.0F,
                    var wednesdayEnd: Float = 0.0F,
                    var wednesdayBreak : String = "No",
                    var tuesdayStart: Float = 0.0F,
                    var tuesdayEnd : Float = 0.0F,
                    var tuesdayBreak: String = "No",
                    var fridayStart: Float = 0.0F,
                    var fridayEnd: Float = 0.0F,
                    var fridayBreak: String = "No",
                    var saturdayStart: Float = 0.0F,
                    var saturdayEnd : Float = 0.0F,
                    var saturdayBreak : String = "No",
                    var sundayStart : Float = 0.0F,
                    var sundayEnd : Float = 0.0F,
                    var sundayBreak : String = "No"
                    ) : Serializable {
    fun showWorkTime(array: Array<String>) : String{
        var stringBuilder = StringBuilder()
        var firstDay = true
        var lastDay = "Last"
                 fun addOneDay(day : String, breakWord : String, start : Float, end : Float, breakTime : String) : String{
                    if (start == 0.0F){
                        if(firstDay){
                             firstDay = false
                            return "$day-"
                        }else{
                            lastDay = day
                            return ""
                        }
                    }else if (start == 66.00F) {
                        if(firstDay){
                            return "$day: ${array.get(8)}\n"
                        }else{
                            firstDay = true
                            return "$lastDay\n$day: ${array.get(8)}\n"
                        }
                    }else{
                        firstDay = true
                        return "$day: ${start}0-${end}0 ${if (breakTime!="No"){"$breakWord: $breakTime"}else{""} }\n"
                    }
                }
        stringBuilder.append(addOneDay(array.get(0), array.get(7), mondayStart, mondayEnd, mondayBreak))
        stringBuilder.append(addOneDay(array.get(1), array.get(7), thursdayStart, thursdayEnd, thursdayBreak))
        stringBuilder.append(addOneDay(array.get(2), array.get(7), wednesdayStart, wednesdayEnd,wednesdayBreak))
        stringBuilder.append(addOneDay(array.get(3), array.get(7), tuesdayStart, tuesdayEnd, tuesdayBreak))
        stringBuilder.append(addOneDay(array.get(4), array.get(7), fridayStart, fridayEnd, fridayBreak))
        stringBuilder.append(addOneDay(array.get(5), array.get(7), saturdayStart, saturdayEnd, saturdayBreak))
        stringBuilder.append(addOneDay(array.get(6), array.get(7), sundayStart, sundayEnd, sundayBreak))

        return stringBuilder.toString()
    }

}

//fun addOneDay(var firstDay : Boolean, day : String, breakWord : String, start : Float, end : Float, breakTime : String) : String{
//    if (start == 0.0F){
//        if(firstDay){
//            firstDay = false
//            return "$day-"
//        }else{
//            return ""
//        }
//    }else{
//        firstDay = true
//        return "$day: $start-$end ${if (breakTime!="No"){"$breakWord: $breakTime"}else{""} }\n"
//    }
//}