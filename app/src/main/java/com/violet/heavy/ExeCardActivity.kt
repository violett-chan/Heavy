package com.violet.heavy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ExeCardActivity : AppCompatActivity() {

    val controller: Controller = Controller(this)
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exe_card)
        index = intent.getIntExtra("index", 0)
        val imageView: ImageView = findViewById(R.id.exeActivityImageView)
        imageView.setImageResource(databaseList[index].image)
        val nameTextView: TextView = findViewById(R.id.exeActivityNameTextView)
        nameTextView.text = databaseList[index].name
        val descTextView: TextView = findViewById(R.id.exeActivityDescTextView)
        descTextView.text = databaseList[index].description
        val fireImageView: ImageView = findViewById(R.id.exeActivityFireImageView)
        if (!databaseList[index].fire) fireImageView.visibility = View.INVISIBLE
    }

    fun start(view : View) {
        val dialogFragment = CustomDialogFragment()
        dialogFragment.show(supportFragmentManager, "CustomDialog")
    }
}