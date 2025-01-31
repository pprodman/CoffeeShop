# 🔄 Pruebas de Regresión

## **Objetivo:**
- Detectar si los cambios recientes en el código han afectado funcionalidades previamente implementadas.
- Evitar la reintroducción de errores en partes ya probadas de la aplicación.

## **Casos de Prueba:**
### **1. Verificación de transiciones tras modificaciones**
- **Escenario:** Después de actualizar las transiciones entre fragmentos.
- **Pasos:**
  - Navegar entre `Cafeterias` y `Valoraciones` varias veces.
  - Observar si la animación se ejecuta correctamente.
- **Herramientas:** UI Automator, Espresso.
- **Resultado esperado:** La transición es fluida y sin errores.

**Ejemplo de código en JUnit:**
```kotlin
@Test
fun testFragmentTransition() {
    onView(withId(R.id.recyclerView))
        .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    onView(withId(R.id.nameCard))
        .check(matches(isDisplayed()))
}
```
### **2. Validación del Sistema de Rating**
- **Escenario:** Confirmar que el valor de calificación se almacena y actualiza correctamente.
- **Pasos:**
  - Seleccionar una cafetería.
  - Cambiar la puntuación en el RatingBar.
  - Verificar que el nuevo valor se guarda y muestra correctamente.
- **Resultado esperado:** La puntuación se almacena sin errores.

```kotlin
@Test
fun testRatingUpdate() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.ratingCard))
        .perform(setRating(4.5f))
    
    onView(withId(R.id.puntuacionCard))
        .check(matches(withText("4.5")))
}
```
