package com.github.kiolk.hrodnaday.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.support.v7.widget.SearchView
import com.github.kiolk.hrodnaday.*
import com.github.kiolk.hrodnaday.R
import com.github.kiolk.hrodnaday.data.database.DBOperations
import com.github.kiolk.hrodnaday.data.models.DayNoteModel
import com.github.kiolk.hrodnaday.data.models.Museum
import com.github.kiolk.hrodnaday.data.recycler.EventArchiveAdapter
import com.github.kiolk.hrodnaday.data.recycler.ItemClickListener
import com.github.kiolk.hrodnaday.ui.MainActivity.sdd.LANGUAGE_PREFERNCES
import com.github.kiolk.hrodnaday.ui.MainActivity.sdd.LANGUAGE_PREFIX
import com.github.kiolk.hrodnaday.ui.activites.AddNoteActivity
import com.github.kiolk.hrodnaday.ui.fragments.ArchiveFragment
import com.github.kiolk.hrodnaday.ui.fragments.LeavingMessageFragment
import com.github.kiolk.hrodnaday.ui.fragments.OneEventFragment
import com.github.kiolk.hrodnaday.ui.fragments.SplashFragment
import com.google.firebase.database.*
import kiolk.com.github.pen.Pen
import kiolk.com.github.pen.utils.PenConstantsUtil.*
import kotlinx.android.synthetic.main.activity_main.*
import layout.SlideEventsFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    object sdd {
        val LANGUAGE_PREFIX = "Language"
        val LANGUAGE_PREFERNCES = "Language_preferences"
    }

    var mTransaction: FragmentTransaction? = null
    var mFragmentManager: FragmentManager? = null
    var archive: ArchiveFragment? = null
    var oneEvent: OneEventFragment? = null
    lateinit var mSplashFragment: SplashFragment
    var arrayDayEvents: Array<DayNoteModel>? = null
    var lastViewPagerPosition: Int? = null
    var mCurrentDay: Long = 0
    var redyStopSplash = false
    lateinit var mainMenu: Menu
    lateinit var searchView: SearchView

    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    var mEventListener: ChildEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyLogs", "Start onCreate")
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.MyTheme_Dark)
        } else {
            setTheme(R.style.MyTheme_Light)
        }
        loadData()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference.child("days")
        val ref = mFirebaseDatabase.reference.child("museums")
        val mus = Museum(nameOfMuseum = "DemoMuseum")
        ref.setValue(mus)
        Log.d("MyLogs", mus.toString())
        if (intent != null) {
            lastViewPagerPosition = intent.getIntExtra("position", 0)
        }
        setContentView(R.layout.activity_main)
        initToolBar()

        mFragmentManager = fragmentManager
        archive = ArchiveFragment()
        oneEvent = OneEventFragment()
        mSplashFragment = SplashFragment()
        mCurrentDay = getCurrentDay()
        if (!intent.getBooleanExtra("restart", false)) {
            mSplashFragment = SplashFragment()
            showFullFrame()
            showFragment(R.id.full_screen_frame_layout, mSplashFragment)
            SplashAsinckTask().execute(object : SplashListener {
                override fun endShowSplash() {
                    if (redyStopSplash) {
                        closeFullFrame()
                        closeFragment(mSplashFragment)
                    }else{
                        redyStopSplash = true
                    }
                }
            })
        }


        val listener = View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                about_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                archive_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                day_event_button_text_view.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                closeFragment(archive)
                events_view_pager.visibility = View.GONE
                mainMenu.findItem(R.id.search_menu_item).isVisible = false
                mainMenu.findItem(R.id.day_night_menu_item).isVisible = true
                mainMenu.findItem(R.id.language_menu_item).isVisible = true
                mainMenu.findItem(R.id.advance_menu_item).isVisible = false


                when (it) {
                    about_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
//                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.PRESSED_GENERAL_BUTTON))
                        mainMenu.findItem(R.id.day_night_menu_item).isVisible = false
                        mainMenu.findItem(R.id.language_menu_item).isVisible = false
                        mainMenu.findItem(R.id.advance_menu_item).isVisible = true
                    }
                    archive_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
//                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.BUTTON_COLOR))
                        showArchiveFragment()
                        mainMenu.findItem(R.id.search_menu_item).isVisible = true
                        mainMenu.findItem(R.id.day_night_menu_item).isVisible = false
                        mainMenu.findItem(R.id.language_menu_item).isVisible = false
                    }
                    day_event_button_text_view -> {
                        it.background = resources.getDrawable(R.drawable.button_under_background)
//                        main_frame_layout.setBackgroundColor(resources.getColor(R.color.UPPER_BUTTON_LINE))
                        events_view_pager.visibility = View.VISIBLE
//                        if(arrayDayEvents != null) startDayEventViewPager()
                    }
                }
            }
        }

        about_button_text_view.setOnClickListener(listener)
        archive_button_text_view.setOnClickListener(listener)
        day_event_button_text_view.setOnClickListener(listener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            day_event_button_text_view.background = resources.getDrawable(R.drawable.button_under_background)
        }

        attachEventListener()
    }

    private fun setUpData() {
        if (checkConnection(this)) {
            //            SendRequestAsyncTask().execute(RequestModel("http://www.json-generator.com/api/json/get/bVTePKeVmG?indent=2",
            SendRequestAsyncTask().execute(RequestModelFromDB(
                    object : ResultCallback<ResponseModel> {
                        override fun onSuccess(param: ResponseModel) {
                            val arrayEvents = param.objects
                            arrayEvents?.sortBy { it.day }
                            val locale: String = baseContext.resources.configuration.locale.language
                            Log.d("MyLogs", locale)
                            arrayDayEvents = arrayEvents?.filter { it.day < getCurrentDay() }?.toTypedArray()
                            arrayDayEvents = arrayDayEvents?.filter { it.language.equals(locale) }?.sortedBy { it.day }?.toTypedArray()

                            if (!intent.getBooleanExtra("restart", false)) {
                                if (redyStopSplash) {
                                    closeFullFrame()
                                    closeFragment(mSplashFragment)
                                } else {
                                    redyStopSplash = true
                                }
                            }

                            startDayEventViewPager()
                        }

                        override fun onError(exception: Exception) {
                        }
                    }
            ))
        }
    }


    private fun attachEventListener() {
        mEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                var dayNote = p0?.getValue<DayNoteModel>(DayNoteModel::class.java)
                dayNote?.let { DBOperations().insert(it) }
                Log.d("MyLogs", "day = ${dayNote?.day}")
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                Log.d("MyLogs", p0.toString())
                var dayNote = p0?.getValue<DayNoteModel>(DayNoteModel::class.java)

                dayNote?.let {
                    DBOperations().insert(it)
                }
                Log.d("MyLogs", "day = ${dayNote?.day}")
                if (dayNote?.day != null && (dayNote.day > mCurrentDay || dayNote.day == 0L)) {
                    Log.d("MyLogs", "Get last day. And start show main fragment")
                    setUpData()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }
        }
        mDatabaseReference.addChildEventListener(mEventListener)
//        val string : String = WorkTime(mondayStart = 4.5F).showWorkTime(resources.getStringArray(R.array.DAYS))
    }


    override fun onPause() {
        super.onPause()
        if (mEventListener != null) {
            mDatabaseReference.removeEventListener(mEventListener)
            mEventListener == null
        }
    }

    override fun onResume() {
        super.onResume()
        if (mEventListener == null) {
            attachEventListener()
        }
        if (mCurrentDay != getCurrentDay()) {
            restartActivity(events_view_pager.currentItem)
            mCurrentDay = getCurrentDay()
        }

    }

    private fun initToolBar() {
        val actionBar = supportActionBar
        main_tool_bar.navigationIcon = resources.getDrawable(R.drawable.ic_coat_of_arms_of_hrodna)
//        actionBar?.setLogo(resources.getDrawable(R.drawable.ic_coat_of_arms_of_hrodna))
        setSupportActionBar(main_tool_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mainMenu = menu!!
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val adapter = archive?.getAdapter() as EventArchiveAdapter
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.toCharArray()?.size == 0) {
                    val adapter = archive?.getAdapter() as EventArchiveAdapter
                    adapter.filter.filter(newText)
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val menuItem = menu?.findItem(R.id.search_menu_item)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.day_night_menu_item -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                val position = events_view_pager.currentItem
                restartActivity(position)
//                closeFragment()
//                events_view_pager.destroyDrawingCache()
//                startDayEventViewPager()
            }
            R.id.search_menu_item -> {
                closeFragment(archive)
                showArchiveFragment()
            }
            R.id.english_menu_item -> {
                changeLocale("en")
                restartActivity(null)
            }
            R.id.russian_menu_item -> {
                changeLocale("ru")
                restartActivity(null)
            }
            R.id.belarus_menu_item -> {
                changeLocale("be")
                restartActivity(null)
            }
            R.id.advance_menu_item -> {
                val intent: Intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        loadData()
    }

    fun saveData(lang: String) {
        val prefernces = getSharedPreferences(LANGUAGE_PREFERNCES, Activity.MODE_PRIVATE)
        val editor = prefernces.edit()
        editor.putString(LANGUAGE_PREFIX, lang)
        editor.commit()
    }

    fun loadData() {
        val preferences = getSharedPreferences(LANGUAGE_PREFERNCES, Activity.MODE_PRIVATE)
        val lang = preferences.getString(LANGUAGE_PREFIX, "en")
        if (lang != resources.configuration.locale.language) {
            changeLocale(lang)
        }
    }

    private fun changeLocale(lang: String?) {
        val locale = Locale(lang)
        lang?.let { saveData(it) }
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
    }

    private fun restartActivity(position: Int?) {
        val intent = Intent(this, MainActivity::class.java)

        if (position != null) {
            intent.putExtra("position", position)
        }
        intent.putExtra("restart", true)
        startActivity(intent)
        finish()
    }

    private fun startDayEventViewPager() {
        events_view_pager.visibility = View.VISIBLE
        val pageAdapter = ScreenSlideAdapter(supportFragmentManager, arrayDayEvents)
        events_view_pager.adapter = pageAdapter

        if (lastViewPagerPosition != null && lastViewPagerPosition != 0) {
            events_view_pager.currentItem = lastViewPagerPosition as Int
        } else {
            events_view_pager.currentItem = arrayDayEvents?.size?.minus(1) ?: 0
        }

        events_view_pager.clipToPadding = false
        events_view_pager.setPadding(48, 0, 48, 0)
        events_view_pager.pageMargin = 30
    }

    private fun initImageLoader() {
        Pen.getInstance().setLoaderSettings().setSavingStrategy(SAVE_FULL_IMAGE_STRATEGY).setContext(this).setTypeOfCache(INNER_FILE_CACHE).setSizeInnerFileCache(10).setUp()
    }

    override fun onBackPressed() {
        if (full_screen_frame_layout.visibility == View.VISIBLE) {
            closeFragment(oneEvent)
            closeFullFrame()
        } else if (!searchView.isIconified) {
            searchView.isIconified = true
        } else {
            LeavingMessageFragment().show(supportFragmentManager, null)
//            super.onBackPressed()
        }
    }

    private fun closeFullFrame() {
        full_screen_frame_layout.visibility = View.GONE
        main_frame_layout.visibility = View.VISIBLE
        button_linear_layout.visibility = View.VISIBLE
    }

    fun showArchiveFragment() {
        showFragment(R.id.main_frame_layout, archive)
        archive?.presentData(object : ItemClickListener {
            override fun onItemClick(date: String) {
                if (full_screen_frame_layout.visibility != View.VISIBLE) arrayDayEvents?.find { it.title == date }?.day?.let { showOneEventFragment(it) }
            }
        })

    }

    fun showOneEventFragment(date: Long) {
        showFullFrame()
        showFragment(R.id.full_screen_frame_layout, oneEvent)
        oneEvent?.showChosenDay(date)
    }

    private fun showFullFrame() {
        full_screen_frame_layout.visibility = View.VISIBLE
        main_frame_layout.visibility = View.GONE
        button_linear_layout.visibility = View.INVISIBLE
        main_tool_bar.visibility = View.INVISIBLE
        separate_line_linear_layout.visibility = View.INVISIBLE
    }

    fun showFragment(pContainer: Int, pFragment: Fragment?) {
        mTransaction = mFragmentManager?.beginTransaction()
        mTransaction?.add(pContainer, pFragment)
        mTransaction?.commit()
        mFragmentManager?.executePendingTransactions()
    }

    fun closeFragment(pFragment: Fragment?) {
        main_tool_bar.visibility = View.VISIBLE
        separate_line_linear_layout.visibility = View.VISIBLE
        mTransaction = mFragmentManager?.beginTransaction()
        mTransaction?.remove(pFragment)
        mTransaction?.commit()
    }

    class ScreenSlideAdapter(fm: android.support.v4.app.FragmentManager, array: Array<DayNoteModel>?) : FragmentStatePagerAdapter(fm) {

        var arrayNotes: Array<DayNoteModel>? = null
        var listener: View.OnClickListener? = null

        init {
            arrayNotes = array
        }

        override fun getItem(position: Int): android.support.v4.app.Fragment {
            if (position == arrayNotes?.size) {
                return SlideEventsFragment().formInstance(DayNoteModel(day = 2333333), 0)
            }
            if (position == arrayNotes?.size?.minus(1)) {
                return SlideEventsFragment().formInstance(arrayNotes?.get(position), 1)
            }
            return SlideEventsFragment().formInstance(arrayNotes?.get(position), 2)
        }

        override fun getCount(): Int {
            return arrayNotes?.size?.plus(1) ?: 2
        }

        override fun getItemPosition(`object`: Any?): Int {
            return super.getItemPosition(`object`)
        }
    }
}
