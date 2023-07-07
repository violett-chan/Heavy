package com.violet.heavy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView


class AllFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val exerciseList = databaseList.sortedBy { it.name[0] }
        val rootView = inflater.inflate(R.layout.fragment_all, container, false)
        val linearLayout: LinearLayout = rootView.findViewById(R.id.allExeLinearLayout)
        for (i in 0 until databaseList.size) {
            val inflatedView: View = inflater.inflate(R.layout.ui_execard, null)
            inflatedView.id = View.generateViewId()
            val exeCardView: CardView = inflatedView.findViewById(R.id.exeCardView)
            exeCardView.setOnClickListener {
                val intent = Intent(requireContext(), ExeCardActivity::class.java)
                intent.putExtra("index", i)
                startActivity(intent)
            }
            val nameTextView: TextView = inflatedView.findViewById(R.id.nameOfExeTextView)
            nameTextView.text = databaseList[i].name
            val descriptionTextView: TextView =
                inflatedView.findViewById(R.id.descriptionOfExeTextView)
            descriptionTextView.text = databaseList[i].description
            val fireImageView: ImageView = inflatedView.findViewById(R.id.fireImageView)
            if (!databaseList[i].fire) fireImageView.visibility = View.INVISIBLE
            linearLayout.addView(inflatedView)
        }
        return rootView
    }

    companion object {

    }
}