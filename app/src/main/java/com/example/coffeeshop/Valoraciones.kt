package com.example.coffeeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import androidx.constraintlayout.motion.widget.MotionLayout

class Valoraciones : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mantener transiciones compartidas
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_title_transition)
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_valoraciones, container, false)

        // Obtener el nombre de la cafetería desde los argumentos
        val coffeeShopName = arguments?.getString("title")
        val titleTextView = view.findViewById<TextView>(R.id.nameCardCoffe)
        titleTextView.text = coffeeShopName
        titleTextView.transitionName = "tranTitle"

        // Configurar clics en las tarjetas
        val motionLayout = view.findViewById<MotionLayout>(R.id.motionLayout)

        val cardIds = listOf(R.id.card1, R.id.card2, R.id.card3, R.id.card4)

        for (cardId in cardIds) {
            val card = view.findViewById<CardView>(cardId)
            card.setOnClickListener {
                if (motionLayout.progress == 0f) {
                    motionLayout.transitionToEnd()
                } else {
                    motionLayout.transitionToStart()
                }
            }
        }

        return view
    }
}
