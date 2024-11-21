package com.example.coffeeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater

class Valoraciones : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_title_transition)

        // Configurar la transición de salida para el slide
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_valoraciones, container, false)

        val coffeeShopName = arguments?.getString("title")
        val titleTextView = view.findViewById<TextView>(R.id.nameCard)
        titleTextView.text = coffeeShopName
        titleTextView.transitionName = "tranTitle"

        return view
    }
}

