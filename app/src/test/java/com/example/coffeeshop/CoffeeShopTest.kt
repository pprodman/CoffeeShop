package com.example.coffeeshop

import com.example.coffeeshop.model.Coffeeshop
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeShopTest {

    @Test
    fun `should create a coffeeshop with correct values`() {
        val coffeeshop = Coffeeshop("Antico Caffè Greco", "St. Italy, Rome", R.drawable.images)
        assertEquals("Antico Caffè Greco", coffeeshop.titulo)
        assertEquals("St. Italy, Rome", coffeeshop.direccion)
        assertEquals(R.drawable.images, coffeeshop.imagen)
    }
}