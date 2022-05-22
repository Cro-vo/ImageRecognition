package com.example.imagerecognition.ui.choice

import android.app.Activity
import com.example.imagerecognition.databinding.ActivityChoiceBinding
import com.example.imagerecognition.logic.model.ChoiceObject
import com.example.imagerecognition.ui.mainPage.MainActivity



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.FunctionObject
import com.example.imagerecognition.ui.choice.ChoiceActivity


class ChoiceAdapter(private val context: Context, private val choiceList: List<ChoiceObject>) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val choiceImg: ImageView = view.findViewById(R.id.choiceImg)
        val choiceName: TextView = view.findViewById(R.id.choiceName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.choice_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val par = parent.context as ChoiceActivity

            when (choiceList[position].name) {
                "使用相机拍照" -> {

                    par.photoButton()

                }

                "从相册获取" -> {
                    par.albumButton()

                }
            }


        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val function = choiceList[position]
        holder.choiceImg.setImageResource(function.imageId)
        holder.choiceName.text = function.name
    }

    override fun getItemCount() = choiceList.size



}