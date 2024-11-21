package com.example.coffeeshop.adapter

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.model.Coffeeshop

/**
 * ViewHolder para la lista de Cafeterias
 */
class CoffeeShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imagenCard: ImageView = view.findViewById<ImageView>(R.id.imageCard)
    private val nombreCard: TextView = view.findViewById<TextView>(R.id.nameCard)
    private val direccionCard: TextView = view.findViewById<TextView>(R.id.direccionCard)
    private val rb: RatingBar = view.findViewById<RatingBar>(R.id.ratingCard)
    private val puntuacion: TextView = view.findViewById<TextView>(R.id.puntuacionCard)

    // Vincula los datos con los elementos de la vista
    fun render(shop: Coffeeshop) {
        imagenCard.setImageResource(shop.imagen)
        nombreCard.text = shop.titulo
        direccionCard.text = shop.direccion

        // Configurar el RatingBar
        rb.numStars = 5 // Establece el número de estrellas
        rb.stepSize = 0.5f // Establece el tamaño del paso
        rb.rating = 0f // Establece la calificación inicial

        rb.setOnRatingBarChangeListener { _, rating, _ ->
            puntuacion.text = String.format("%.1f", rating) // Actualiza el TextView
        }
    }
}
