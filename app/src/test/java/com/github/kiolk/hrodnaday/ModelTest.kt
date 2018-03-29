package com.github.kiolk.hrodnaday

import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.github.kiolk.hrodnaday.data.models.WorkTime
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ModelTest {

    @Before
    fun setUp(){
        val jsonExample = "{\"day\":1111111111,\"pictureUrl\":\"https://nn.by/img/w630d4/photos/z_2018_02/96958a9f889de0e4e7e2ebe6e7e2e2e1e2e0e0e2e3eae2e2e2e2e2e2-dsxmzo2650949003022018000001-pb1-2-p6r8d.jpg\",\"title\":\"Rocket\",\"description\":\"Small rocket fly\",\"author\":\"New Castle Museum\"}"
        val workTime = WorkTime(mondayStart = 8.00F, mondayEnd = 17.00F, sundayStart = 9.00F, sundayEnd = 16.00F)
    }

    val jsonExample = "{\"day\":1111111111,\"pictureUrl\":\"https://nn.by/img/w630d4/photos/z_2018_02/96958a9f889de0e4e7e2ebe6e7e2e2e1e2e0e0e2e3eae2e2e2e2e2e2-dsxmzo2650949003022018000001-pb1-2-p6r8d.jpg\",\"title\":\"Rocket\",\"description\":\"Small rocket fly\",\"author\":\"New Castle Museum\"}"
    val workTime = WorkTime(thursdayStart = 8.00F, thursdayEnd = 17.00F, thursdayBreak = "13.00-14.00", sundayStart = 9.00F, sundayEnd = 16.00F, sundayBreak = "12.00-12.30", wednesdayStart = 66.00F)

    @Test
    fun checkDayNoteModel() {
        val oneNote = DayNoteModel(1111111111L, "https://nn.by/img/w630d4/photos/z_2018_02/96958a9f889de0e4e7e2ebe6e7e2e2e1e2e0e0e2e3eae2e2e2e2e2e2-dsxmzo2650949003022018000001-pb1-2-p6r8d.jpg",
                "Rocket", "Small rocket fly", "New Castle Museum")
        val json = Gson().toJson(oneNote)
        Assert.assertNotNull(json)
        Assert.assertEquals(json, jsonExample)
        val readJson : DayNoteModel = Gson().fromJson(json, DayNoteModel::class.java)
        Assert.assertEquals(oneNote, readJson)
    }

    @Test
    fun checkWorkTime(){
        val result = workTime.showWorkTime(listOf("Mon", "Thu", "Wen", "Tus", "Fri", "Sat", "Sun", "break", "No work" ).toTypedArray())
        Assert.assertEquals(result, "1")
    }

}
