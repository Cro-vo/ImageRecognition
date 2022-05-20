package com.example.imagerecognition.ui.animal

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.FunctionObject
import com.example.imagerecognition.ui.detail.DetailActivity

class AnimalAdapter(private val context: Context, private val resultList: List<AnimalResponse.Result>) : RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val animalImg: ImageView = view.findViewById(R.id.animalImg)
        val animalName: TextView = view.findViewById(R.id.animalName)
        val animalScore: TextView = view.findViewById(R.id.animalScore)
        val animalDescription: TextView = view.findViewById(R.id.animalDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.animal_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            // 点击相应的选项可以跳转到详情页面
            val position = holder.adapterPosition
            val result = resultList[position]
//            Log.d("result", "msg:${result}")

            // 需要判断是否正确识别图片
            val intent = Intent(this.context, DetailActivity::class.java)
            intent.putExtra("result", result.baikeInfo)
            this.context.startActivity(intent)

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = resultList[position]
        Glide.with(context).load(result.baikeInfo.imageURL).into(holder.animalImg)
        holder.animalName.text = result.name
        holder.animalScore.text = result.score
        holder.animalDescription.text = result.baikeInfo.description
    }

    override fun getItemCount() = resultList.size

}