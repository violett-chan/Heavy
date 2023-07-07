package com.violet.heavy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import java.util.Locale

class MusculesFragment : Fragment() {
    lateinit var timerTextView: TextView
    private lateinit var startButton: ImageButton
    private lateinit var doneButton: ImageButton
    private lateinit var resetButton: ImageButton
    lateinit var mainActivity: MainActivity
    var hours = 0
    var minutes = 0
    var seconds = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = requireActivity() as MainActivity
        val controller = mainActivity.controller

        val rootView = inflater.inflate(R.layout.fragment_muscules, container, false)
        timerTextView = rootView.findViewById(R.id.timerTextView)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.favLinearLayout)
        val addImageButton: ImageButton = rootView.findViewById(R.id.addImageButton)
        if (controller.favList.size > 0) {
            val deleteFavsImageButton: ImageButton = rootView.findViewById(R.id.deleteFavsImageButton)
            deleteFavsImageButton.visibility = View.VISIBLE
            deleteFavsImageButton.setOnClickListener {
                controller.deleteFavs()
                //linearLayout.removeViews()
            }
        }
        addImageButton.setOnClickListener {
            val intent = Intent(requireContext(), MusclesActivity::class.java)
            startActivity(intent)
        }
        for (i in 0 until controller.favList.size) {
            val inflatedView: View = inflater.inflate(R.layout.ui_execard, null)
            inflatedView.id = View.generateViewId()
            val favsInflatedView: View = inflater.inflate(R.layout.ui_favs, null)
            favsInflatedView.id = View.generateViewId()
            val favsLinearLayout: LinearLayout =
                favsInflatedView.findViewById(R.id.favsLinearLayout)
            val exeCardView: CardView = inflatedView.findViewById(R.id.exeCardView)
            exeCardView.setOnClickListener {
                val layoutParams = favsLinearLayout.layoutParams
                if (layoutParams.height == 0) {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    layoutParams.height = 0;
                }
                favsLinearLayout.layoutParams = layoutParams
            }
            val nameTextView: TextView = inflatedView.findViewById(R.id.nameOfExeTextView)
            nameTextView.text = databaseList[controller.favList[i].id].name
            val descriptionTextView: TextView =
                inflatedView.findViewById(R.id.descriptionOfExeTextView)
            descriptionTextView.text = databaseList[controller.favList[i].id].description
            val fireImageView: ImageView = inflatedView.findViewById(R.id.fireImageView)
            var isDone = true
            for (k in controller.favList[i].done) {
                if (!k) {
                    isDone = false
                    break
                }
            }
            if (isDone) {
                val doneImageView: ImageView = inflatedView.findViewById(R.id.doneImageView)
                doneImageView.visibility = View.VISIBLE
            }
            if (!databaseList[controller.favList[i].id].fire) fireImageView.visibility =
                View.INVISIBLE
            linearLayout.addView(inflatedView)
            for (j in 1..controller.favList[i].entry) {
                val entryInflatedView: View = inflater.inflate(R.layout.ui_entry, null)
                val entryCardView: CardView = entryInflatedView.findViewById(R.id.entryCardView)
                val entryTextView: TextView = entryInflatedView.findViewById(R.id.entryTextView)
                entryTextView.text = j.toString()
                val weightTextView: TextView = entryInflatedView.findViewById(R.id.weightTextView)
                weightTextView.text = controller.favList[i].weight.toString()
                val retryTextView: TextView = entryInflatedView.findViewById(R.id.retryTextView)
                retryTextView.text = controller.favList[i].retry.toString()
                val entryDoneImageView: ImageView =
                    entryInflatedView.findViewById(R.id.entryDoneImageView)
                if (controller.favList[i].done[j - 1]) entryDoneImageView.setColorFilter(Color.GREEN)
                entryCardView.setOnClickListener {
                    controller.favList[i].done[j - 1] = true
                    controller.saveFav()
                    entryDoneImageView.setColorFilter(Color.GREEN)
                    isDone = true
                    for (k in controller.favList[i].done) {
                        if (!k) {
                            isDone = false
                            break
                        }
                    }
                    if (isDone) {
                        val doneImageView: ImageView = inflatedView.findViewById(R.id.doneImageView)
                        doneImageView.visibility = View.VISIBLE
                    }
                }
                favsLinearLayout.addView(entryInflatedView)
            }
            linearLayout.addView(favsInflatedView)
        }

        startButton = rootView.findViewById(R.id.startTimerImageButton)
        doneButton = rootView.findViewById(R.id.doneTimerImageButton)
        resetButton = rootView.findViewById(R.id.resetTimerImageButton)
        if (mainActivity.isRunning) startButton.setImageResource(R.drawable.baseline_pause_24)
        startButton.setOnClickListener {
            if (mainActivity.isRunning) {
                startButton.setImageResource(R.drawable.baseline_play_arrow_24)
                mainActivity.pauseTimer()
            } else {
                startButton.setImageResource(R.drawable.baseline_pause_24)
                mainActivity.startTimer()
            }
        }
        doneButton.setOnClickListener {
            controller.addToTime(hours, minutes, seconds)
            mainActivity.resetTimer()
            startButton.setImageResource(R.drawable.baseline_play_arrow_24)
            Toast.makeText(requireContext(), "Тренировка добавлена", Toast.LENGTH_SHORT).show()
        }
        resetButton.setOnClickListener {
            mainActivity.resetTimer()
            startButton.setImageResource(R.drawable.baseline_play_arrow_24)
        }

        return rootView
    }

    fun updateTimer(hours: Int, minutes: Int, seconds: Int) {
        if (::timerTextView.isInitialized) {
            this.hours = hours
            this.minutes = minutes
            this.seconds = seconds
            val timeText = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
            timerTextView.text = timeText
        }
    }
}