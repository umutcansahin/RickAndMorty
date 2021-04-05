package com.disketcanavari.rickandmorty

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.disketcanavari.rickandmorty.databinding.RowItemBinding
import com.google.android.material.color.MaterialColors.getColor
import com.squareup.picasso.Picasso

class RecylerViewAdapter(val setOnClickinRecyler: SetOnClickinRecyler) : RecyclerView.Adapter<RecylerViewAdapter.RowHolder>() {

    lateinit var binding: RowItemBinding
     var characterList = emptyList<Results>()
    class RowHolder(val binding: RowItemBinding, context: Context): RecyclerView.ViewHolder(binding.root){

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ResourceAsColor")
        fun bindData(character: Results,setOnClickinRecyler: SetOnClickinRecyler){
            binding.charNameView.text = character.name
            Picasso.get().load("${character.image}").into(binding.imageView2)
            binding.lifeStatusView.text = "${character.status} - ${character.species}"

            binding.layoutClick.setOnClickListener{
                setOnClickinRecyler.onItemClicked(character)
            }


            when(character.status){
                "Alive" -> {}
                "Dead" -> {}
                else->{

                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        binding = RowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding,parent.context)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
       holder.bindData(characterList[position],setOnClickinRecyler)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun setData(testlist:List<Results>){
        characterList = testlist
       notifyDataSetChanged()
    }



}
