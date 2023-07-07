package com.violet.heavy

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class CustomDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val exeCardActivity = requireActivity() as ExeCardActivity
            val controller = exeCardActivity.controller
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.ui_dialog, null)

            val dialogEntryEditText = dialogView.findViewById<EditText>(R.id.dialogEntryEditText)
            val dialogWeightEditText = dialogView.findViewById<EditText>(R.id.dialogWeightEditText)
            val dialogRetryEditText = dialogView.findViewById<EditText>(R.id.dialogRetryEditText)

            builder.setView(dialogView)
            builder.setTitle("Введите параметры")
            builder.setPositiveButton("Добавить") { dialog, which ->

                val entry = dialogEntryEditText.text.toString().toInt()
                val weight = dialogWeightEditText.text.toString().toInt()
                val retry = dialogRetryEditText.text.toString().toInt()

                controller.addToFav(exeCardActivity.index, entry, weight, retry)
                Toast.makeText(exeCardActivity.applicationContext, "Добавлено", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("Отмена") { dialog, _ ->

            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
