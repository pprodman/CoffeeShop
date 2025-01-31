
# 🔄 Pruebas de Volumen y Estrés

## **Objetivo:**
- Evaluar el rendimiento de la aplicación cuando maneja grandes volúmenes de datos.
- Identificar cuellos de botella y posibles problemas de estabilidad en condiciones extremas.

## **Casos de Prueba:**
### **1. Prueba de Carga Masiva**
- **Escenario:** Cargar un gran número de cafeterías en la aplicación.
- **Pasos:**
  - Generar 500 elementos de prueba.
  - Cargar la lista en el RecyclerView.
  - Medir el tiempo de carga.
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
