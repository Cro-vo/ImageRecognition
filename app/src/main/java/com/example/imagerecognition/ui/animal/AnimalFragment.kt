package com.example.imagerecognition.ui.animal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imagerecognition.R
import com.example.imagerecognition.databinding.FragmentAnimalBinding


class AnimalFragment : Fragment() {

//    lateinit var binding: FragmentAnimalBinding

    val viewModel by lazy { ViewModelProvider(this).get(AnimalViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentAnimalBinding.inflate(layoutInflater)
        return inflater.inflate(R.layout.fragment_animal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        binding.button2.setOnClickListener {
//            viewModel.getAnimalInfo("https://baidu-ai.bj.bcebos.com/image-classify/animal.jpeg")
//        }

        viewModel.animalLiveData.observe(viewLifecycleOwner, Observer {
            val results = it.getOrNull()
            println(results)
        })


    }

}