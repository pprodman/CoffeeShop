# 🧪 Pruebas de Integración 

## **Objetivo:**
- Validar la correcta interacción entre los distintos componentes de la aplicación (UI, navegación, bases de datos, servicios, etc.).
- Asegurar que los datos fluyen correctamente entre las pantallas sin errores.

## **Casos de Prueba:**
### **1. Integración del RecyclerView con el Adaptador**
- **Escenario:** La lista de cafeterías debe mostrarse correctamente en el RecyclerView.
- **Pasos:**
  1. Iniciar la aplicación.
  2. Verificar que se cargan todas las cafeterías.
  3. Hacer scroll para confirmar que los elementos aparecen correctamente.
- **Herramientas:** JUnit, Espresso.
- **Resultado esperado:** Los datos se muestran correctamente sin errores.

**Ejemplo de código en Espresso:**
```kotlin
@Test
fun testRecyclerViewDisplaysItems() {
    onView(withId(R.id.recyclerView))
        .check(matches(isDisplayed()))
    onView(withId(R.id.recyclerView))
        .perform(scrollToPosition<RecyclerView.ViewHolder>(5))
}
```

### **2. Integración de la navegación entre fragmentos**
- **Escenario:** Navegación desde `Cafeterias` a `Valoraciones`.
- **Pasos:**
  1. Hacer clic en una cafetería.
  2. Confirmar que la transición ocurre correctamente.
  3. Verificar que el nombre de la cafetería se muestra correctamente en `Valoraciones`.
- **Herramientas:** Navigation Component Test, Espresso.
- **Resultado esperado:** La navegación funciona sin errores.

**Ejemplo de código en Espresso:**
```kotlin
@Test
fun testNavigationToValoraciones() {
    onView(withId(R.id.recyclerView))
        .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    onView(withId(R.id.nameCard))
        .check(matches(isDisplayed()))
}
```

### **3. Pruebas de Navegación**
- **Escenario:** Validar navegación y transiciones visuales entre pantallas.
- **Pasos:**
  1. Seleccionar una cafetería de la lista.
  2. Verificar la transición animada.
  3. Regresar al listado y comprobar el estado de la vista.
- **Resultado esperado:** Navegación sin errores y transiciones fluidas.

```kotlin
@Test
fun testCafeteriaToValoracionesNavigation() {
    launchFragmentInContainer<Cafeterias>()
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.nameCard))
        .check(matches(isDisplayed()))
        .check(matches(withText("Antico Caffè Greco")))
}
```
