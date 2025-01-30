# Pruebas de Uso
## **Objetivo:**
- Verificar que la aplicación sea intuitiva y fácil de usar para los usuarios finales.
- Asegurar que los elementos interactivos sean accesibles y funcionales en distintos escenarios.

## **Casos de Prueba:**

### **1. Simulación del Flujo Completo de Usuario**
- **Escenario:** Evaluar la experiencia del usuario en un recorrido completo por la aplicación.
- **Resultado esperado:** El flujo es intuitivo y sin errores.

```kotlin
@Test
fun testCompleteUserJourney() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .check(matches(isDisplayed()))
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.ratingCard))
        .perform(setRating(4.0f))
    
    onView(withId(R.id.puntuacionCard))
        .check(matches(withText("4.0")))
}
```
