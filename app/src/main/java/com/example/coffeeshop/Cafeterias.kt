package com.example.coffeeshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.adapter.CoffeeShopAdapter
import com.example.coffeeshop.model.CoffeeShopProvider

class Cafeterias : Fragment() {

    private lateinit var adapter: CoffeeShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cafeterias, container, false)

        // Configuración del RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CoffeeShopAdapter(
            coffeeShops = CoffeeShopProvider.coffeeshopList,
            navController = findNavController()
        )
        recyclerView.adapter = adapter

        return view
    }
}

