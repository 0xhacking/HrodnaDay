package com.github.kiolk.hrodnaday.ui

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.data.database.DBConnector
import com.github.kiolk.hrodnaday.ui.fragments.ArchiveFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mTransaction: FragmentTransaction? = null
    var mFragmentManager: FragmentManager? = null
    var archive : ArchiveFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DBConnector.initInstance(this)

        mFragmentManager = fragmentManager
        archive = ArchiveFragment()


        val listener = View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                about_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                archive_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                day_event_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                closeFragment(archive)

                when (it) {
                    about_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.PRESSED_GENERAL_BUTTON))
                    }
                    archive_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                        showArchiveFragment()
                    }
                    day_event_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.UPPER_BUTTON_LINE))
                    }
                }
            }
        }

        about_button_text_view.setOnClickListener(listener)
        archive_button_text_view.setOnClickListener(listener)
        day_event_button_text_view.setOnClickListener(listener)

        SendRequestAsyncTask().execute(RequestModel("http://www.json-generator.com/api/json/get/bUEgRdnXJu?indent=2",
                object : ResultCallback<ResponseModel> {
                    override fun onSuccess(param: ResponseModel) {
                        val arrayEvents = param.objects
                        arrayEvents?.sortBy { it.day }
                        val note = arrayEvents?.maxBy { it.day }
                        title_text_view.text = note?.title
                    }

                    override fun onError(exception: Exception) {
                        title_text_view.text = exception.message
                    }
                }
        ))
    }

    fun showArchiveFragment(){
        showFragment(R.id.main_frame_layout, archive)
        archive?.presentData()
    }

    fun showFragment(pContainer: Int, pFragment: Fragment?) {
        mTransaction = mFragmentManager?.beginTransaction()
        mTransaction?.add(pContainer, pFragment)
        mTransaction?.commit()
        mFragmentManager?.executePendingTransactions()
    }

    fun closeFragment(pFragment : Fragment?){
        mTransaction = mFragmentManager?.beginTransaction()
        mTransaction?.remove(pFragment)
        mTransaction?.commit()
    }
}