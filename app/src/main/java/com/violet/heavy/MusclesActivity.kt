package com.violet.heavy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class MusclesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscles)
        val inflater = LayoutInflater.from(this)
        val linearLayout: LinearLayout = findViewById(R.id.allMusclesLinearLayout)
        for (i in 0 until Muscles.values().size) {
            val inflatedView: View = inflater.inflate(R.layout.ui_muscule, null)
            inflatedView.id = View.generateViewId()
            val nameTextView: TextView = inflatedView.findViewById(R.id.nameTextView)
            nameTextView.text = Muscles.values()[i].value
            val musculeLinearLayout: LinearLayout =
                inflatedView.findViewById(R.id.musculeLinearLayout)
            val imageView: ImageView = inflatedView.findViewById(R.id.arrowImageView)
            imageView.setOnClickListener {
                val layoutParams = musculeLinearLayout.layoutParams
                if (layoutParams.height == 0) {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    layoutParams.height = 0;
                }
                musculeLinearLayout.layoutParams = layoutParams
            }
            for (j in 0 until databaseList.size) {
                if (databaseList[j].muscle == Muscles.values()[i]) {
                    val inflatedView: View = inflater.inflate(R.layout.ui_execard, null)
                    inflatedView.id = View.generateViewId()
                    val exeCardView: CardView = inflatedView.findViewById(R.id.exeCardView)
                    exeCardView.setOnClickListener {
                        val intent = Intent(this, ExeCardActivity::class.java)
                        intent.putExtra("index", j)
                        startActivity(intent)
                    }
                    val nameTextView: TextView = inflatedView.findViewById(R.id.nameOfExeTextView)
                    nameTextView.text = databaseList[j].name
                    val descriptionTextView: TextView =
                        inflatedView.findViewById(R.id.descriptionOfExeTextView)
                    descriptionTextView.text = databaseList[j].description
                    val fireImageView: ImageView = inflatedView.findViewById(R.id.fireImageView)
                    if (!databaseList[j].fire) fireImageView.visibility = View.INVISIBLE
                    musculeLinearLayout.addView(inflatedView)
                }
            }
            linearLayout.addView(inflatedView)
        }
    }
}