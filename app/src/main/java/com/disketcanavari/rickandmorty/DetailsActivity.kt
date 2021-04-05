package com.disketcanavari.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.disketcanavari.rickandmorty.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        intent.extras?.let { itExtras ->
            val character: Results = itExtras.getSerializable("transfer") as Results
            binding.speciesText.text = character.species
            binding.lastLocation.text = character.location.name
            binding.locationOrigin.text = character.origin.name

            Picasso.get().load(character.image.toString()).into(binding.imageView)

        }


    }
}