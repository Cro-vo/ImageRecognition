package com.example.imagerecognition.ui.mainPage

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


class FunctionAdapter(private val context: Context, private val functionList: List<FunctionObject>) : RecyclerView.Adapter<FunctionAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val funcImg: ImageView = view.findViewById(R.id.funcImg)
        val funcName: TextView = view.findViewById(R.id.funcName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.function_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition

            when (functionList[position].name) {
                "动物识别" -> {
                    val intent = Intent(context, ChoiceActivity::class.java)
                    intent.putExtra("function", MainActivity.ANIMAL)
                    context.startActivity(intent)

                }

                "植物识别" -> {
                    val intent = Intent(context, ChoiceActivity::class.java)
                    intent.putExtra("function", MainActivity.PLANT)
                    context.startActivity(intent)

                }
            }


        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val function = functionList[position]
        holder.funcImg.setImageResource(function.imageId)
        holder.funcName.text = function.name
    }

    override fun getItemCount() = functionList.size



}