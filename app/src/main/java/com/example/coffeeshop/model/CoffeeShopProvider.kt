package com.example.coffeeshop.model

import com.example.coffeeshop.R

/**
 * Proovedor de datos para la lista de Cafeterias
 */
class CoffeeShopProvider {
    companion object {
        val coffeeshopList = listOf<Coffeeshop>(
            Coffeeshop("Antico Caffè Greco", "St. Italy, Rome", R.drawable.images),
            Coffeeshop("Coffee Room", "St. Germany, Berlin", R.drawable.images1),
            Coffeeshop("Coffee Ibiza", "St. Colon, Madrid", R.drawable.images2),
            Coffeeshop("Pudding Coffee Shop", "St. Diagonal, Barcelona", R.drawable.images3),
            Coffeeshop("L'Express", "St. Picadilly Circus, London", R.drawable.images4),
            Coffeeshop("Coffee Corner", "St. Angel Guimerà, Valencia", R.drawable.images5),
            Coffeeshop("Sweet Cup", "St. Kinkerstraat, Amsterdam", R.drawable.images6),
        )
    }
}