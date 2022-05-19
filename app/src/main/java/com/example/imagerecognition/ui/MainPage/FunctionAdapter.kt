package com.example.imagerecognition.ui.MainPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.FunctionObject

class FunctionAdapter(private val context: Context, private val functionList: List<FunctionObject>) : RecyclerView.Adapter<FunctionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val funcImg: ImageView = view.findViewById(R.id.funcImg)
        val funcName: TextView = view.findViewById(R.id.funcName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.function_item, parent, false)
        val holder = ViewHolder(view)
//        holder.itemView.setOnClickListener {
//
//        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val function = functionList[position]
        holder.funcImg.setImageResource(function.imageId)
        holder.funcName.text = function.name
    }

    override fun getItemCount() = functionList.size

}