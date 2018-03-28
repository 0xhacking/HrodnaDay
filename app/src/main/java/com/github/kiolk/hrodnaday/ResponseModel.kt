package com.github.kiolk.hrodnaday

import com.github.kiolk.hrodnaday.data.models.DayNoteModel

class ResponseModel(val objects: Array<DayNoteModel>?, val exception : Exception?, val callback: ResultCallback<ResponseModel>)