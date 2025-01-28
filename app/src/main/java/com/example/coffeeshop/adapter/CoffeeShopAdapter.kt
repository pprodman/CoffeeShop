package com.example.coffeeshop.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.model.Coffeeshop

/**
 * Adapter para el RecyclerView de cafés.
 * @property coffeeShops Lista de cafés a mostrar.
 * @property navController Controlador de navegación.
 */
class CoffeeShopAdapter(
    private val coffeeShops: List<Coffeeshop>,
    private val navController: NavController
) : RecyclerView.Adapter<CoffeeShopAdapter.CoffeeShopViewHolder>() {

    /**
     * Crea una nueva vista para cada elemento del RecyclerView.
     * @param parent Grupo al que pertenece la vista.
     * @param viewType Tipo de vista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_coffee, parent, false)
        return CoffeeShopViewHolder(view)
    }

    /**
     * Vincula los datos del café con la vista correspondiente.
     * @param holder Vista del ViewHolder.
     * @param position Posición del elemento en la lista.
     */
    override fun onBindViewHolder(holder: CoffeeShopViewHolder, position: Int) {
        val coffeeShop = coffeeShops[position]
        holder.render(coffeeShop)

        val nameCard = holder.itemView.findViewById<TextView>(R.id.nameCard)
        nameCard.transitionName = "tranTitle${coffeeShop.titulo}"

        holder.itemView.setOnClickListener {
            val extras = FragmentNavigatorExtras(nameCard to "tranTitle")
            val bundle = Bundle().apply {
                putString("title", coffeeShop.titulo)
            }
            navController.navigate(R.id.action_cafeterias_to_valoraciones, bundle, null, extras)
        }
    }

    override fun getItemCount(): Int = coffeeShops.size

    /**
     * ViewHolder para cada elemento del RecyclerView.
     * @property imagenCard Imagen de la cafeteria.
     * @property nombreCard Nombre de la cafeteria.
     * @property direccionCard Dirección de la cafeteria.
     * @property rb RatingBar de la cafeteria.
     * @property puntuacion TextView para mostrar la puntuación.
     */
    class CoffeeShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imagenCard: ImageView = view.findViewById(R.id.imageCard)
        private val nombreCard: TextView = view.findViewById(R.id.nameCard)
        private val direccionCard: TextView = view.findViewById(R.id.direccionCard)
        private val rb: RatingBar = view.findViewById(R.id.ratingCard)
        private val puntuacion: TextView = view.findViewById(R.id.puntuacionCard)

        /**
         * Vincula los datos con los elementos de la vista
         */
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
}



