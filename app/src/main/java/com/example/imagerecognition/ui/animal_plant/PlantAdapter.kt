package com.example.imagerecognition.ui.animal_plant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagerecognition.R
import com.example.imagerecognition.logic.model.AnimalResponse
import com.example.imagerecognition.logic.model.PlantResponse
import com.example.imagerecognition.ui.detail.DetailActivity
import com.example.imagerecognition.ui.mainPage.MainActivity

class PlantAdapter(private val context: Context, private val resultList: List<PlantResponse.Result>) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

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

            // 判断是否含有信息
            if (result.baikeInfo.baikeURL != null) {
                val intent = Intent(this.context, DetailActivity::class.java)
                intent.putExtra("result", result)
                intent.putExtra("function", MainActivity.PLANT)
                this.context.startActivity(intent)
            } else {
                Toast.makeText(this.context, "暂无详细信息", Toast.LENGTH_SHORT).show()
            }

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