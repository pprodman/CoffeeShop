
# Pruebas de Volumen y Estrés

## **Casos de Prueba:**
### **1 Prueba de Carga Masiva**
- **Escenario:** Cargar un gran número de cafeterías en la aplicación.
- **Pasos:**
  1. Generar 500 elementos de prueba.
  2. Cargar la lista en el RecyclerView.
  3. Medir el tiempo de carga.
- **Resultado esperado:** Carga en menos de 1 segundo.

```kotlin
@Test
fun testLargeDatasetLoading() {
    val largeCoffeeShopList = (1..500).map { 
        Coffeeshop(
            "Coffee Shop $it",
            "Address $it",
            R.drawable.images
        )
    }
    
    val startTime = System.currentTimeMillis()
    
    activityRule.scenario.onActivity { activity ->
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = CoffeeShopAdapter(largeCoffeeShopList, mockNavController)
    }
    
    val endTime = System.currentTimeMillis()
    assert(endTime - startTime < 1000) 
}
```
