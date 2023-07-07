package com.violet.heavy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    val controller: Controller = Controller(this)
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var muscleFragment: MusculesFragment
    private lateinit var viewPager: ViewPager

    var isRunning: Boolean = false
    private var elapsedTime: Long = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller.init()
        settingTabs()
    }

    private fun settingTabs() {
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MusculesFragment(), "Домой")
        muscleFragment = adapter.getItem(0) as MusculesFragment
        adapter.addFragment(AllFragment(), "Все")
        adapter.addFragment(LogsFragment(), "Отчет")
        adapter.addFragment(SettingsFragment(), "Настройки")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        //tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_tab_1)
        //tabLayout.setTabTextColors(Color.GRAY, Color.BLACK)
    }

    override fun onResume() {
        super.onResume()
        controller.init()
        adapter.notifyDataSetChanged()
        viewPager.adapter = adapter
    }

    fun startTimer() {
        if (!isRunning) {
            handler = Handler()
            runnable = object : Runnable {
                override fun run() {
                    elapsedTime += 1000
                    val hours = (elapsedTime / (1000 * 60 * 60)).toInt()
                    val minutes = ((elapsedTime / (1000 * 60)) % 60).toInt()
                    val seconds = ((elapsedTime / 1000) % 60).toInt()
                    muscleFragment.updateTimer(hours, minutes, seconds)
                    handler.postDelayed(this, 1000)
                }
            }
            handler.postDelayed(runnable, 1000)
            isRunning = true
        }
    }

    fun pauseTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false
        }
    }

    fun resetTimer() {
        handler.removeCallbacks(runnable)
        muscleFragment.updateTimer(0, 0, 0)
        elapsedTime = 0
        isRunning = false
    }
}
