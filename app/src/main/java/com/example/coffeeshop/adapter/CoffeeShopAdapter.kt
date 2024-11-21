package com.example.coffeeshop.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.model.Coffeeshop

class CoffeeShopAdapter(
    private val coffeeShops: List<Coffeeshop>,
    private val navController: NavController
) : RecyclerView.Adapter<CoffeeShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_coffee, parent, false)
        return CoffeeShopViewHolder(view)
    }

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
}

