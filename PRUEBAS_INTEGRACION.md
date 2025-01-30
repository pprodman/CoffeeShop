## 1. Pruebas de Integración

### **Objetivo:**
Verificar la comunicación entre los distintos componentes de la aplicación, asegurando que trabajan correctamente en conjunto.

### **Casos de Prueba:**
#### **1.1 Integración del RecyclerView con el Adaptador**
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

#### **1.2 Integración de la navegación entre fragmentos**
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

#### **1.3 Pruebas de Navegación**
**Test de Navegación Básica**
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

**Test de Transición Compartida**
```kotlin
@Test
fun testSharedElementTransition() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.nameCard))
        .check(matches(withTransitionName("tranTitle")))
}
```

**Test de Navegación Hacia Atrás**
```kotlin
@Test
fun testBackNavigation() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    pressBack()
    
    onView(withId(R.id.recyclerView))
        .check(matches(isDisplayed()))
}
```
