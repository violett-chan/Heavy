package com.violet.heavy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class LogsFragment : Fragment() {

    private val DAYS_VALUE = 7

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = requireActivity() as MainActivity
        val controller = mainActivity.controller
        val rootView = inflater.inflate(R.layout.fragment_logs, container, false)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.logUILinearLayout)
        val size = controller.timeList.size
        for (i in 0 until DAYS_VALUE) {
            var height = 0
            if (size - i > 0) {
                height =
                    (controller.timeList[size - i - 1].hours * 60) + controller.timeList[size - i - 1].minutes
            }
            val inflatedView: View = inflater.inflate(R.layout.ui_log, null)
            inflatedView.id = View.generateViewId()
            val logUIView: View = inflatedView.findViewById(R.id.logUIView)
            val layoutParamsChild = logUIView.layoutParams
            layoutParamsChild.height = height + 1
            logUIView.layoutParams = layoutParamsChild
            linearLayout.addView(inflatedView)
        }
        if (controller.timeList.isNotEmpty()) {
            val dailyLogTextView: TextView = rootView.findViewById(R.id.dailyLogTextView)
            val currentDay = controller.timeList.last().date
            var hours = 0
            var minutes = 0
            for (i in 0 until size) {
                if (controller.timeList[size - i - 1].date != currentDay) break
                minutes += controller.timeList[size - i - 1].minutes
                hours += controller.timeList[size - i - 1].hours
            }
            val plusHours: Int = minutes / 60
            val currentMinutes: Int = minutes % 60
            val currentHours: Int = plusHours + hours
            val text = "$currentHours час \n$currentMinutes минут"
            dailyLogTextView.text = text
        }
        return rootView
    }
}