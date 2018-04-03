package com.github.kiolk.hrodnaday.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kiolk.hrodnaday.R


class SplashFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_splash, null) ?:   super.onCreateView(inflater, container, savedInstanceState)
    }
}