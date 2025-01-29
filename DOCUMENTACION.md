# Pruebas Detalladas - Coffee Shop Android App

## 1. Pruebas de Integración

### 1.1 Pruebas de Navegación

#### Test de Navegación Básica
Esta prueba verifica que al seleccionar una cafetería de la lista, se navega correctamente al fragmento de Valoraciones y se muestra la información correcta.

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

#### Test de Transición Compartida
Verifica que la transición animada entre fragmentos funciona correctamente, comprobando que el nombre de la transición se mantiene durante la navegación.

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

#### Test de Navegación Hacia Atrás
Comprueba que el usuario puede regresar correctamente al fragmento de Cafeterías después de ver los detalles de una cafetería.

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

### 1.2 Pruebas del RecyclerView

#### Test de Cantidad de Elementos
Verifica que el RecyclerView muestra correctamente todos los elementos de la lista de cafeterías proporcionada.

```kotlin
@Test
fun testRecyclerViewItemCount() {
    val testCoffeeShops = CoffeeShopProvider.coffeeshopList
    
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .check(matches(hasChildCount(testCoffeeShops.size)))
}
```

#### Test de Contenido de Items
Comprueba que cada item del RecyclerView muestra correctamente la información de la cafetería (nombre, dirección e imagen).

```kotlin
@Test
fun testRecyclerViewItemContent() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .check(matches(hasDescendant(withText("Antico Caffè Greco"))))
        .check(matches(hasDescendant(withText("St. Italy, Rome"))))
    
    onView(allOf(withId(R.id.imageCard), isDescendantOfA(withId(R.id.recyclerView))))
        .check(matches(isDisplayed()))
}
```

## 2. Pruebas de Regresión

### 2.1 Pruebas del Sistema de Rating

#### Test de Estado Inicial del Rating
Verifica que el RatingBar comienza en un estado inicial correcto (0 estrellas) cuando se abre una nueva cafetería.

```kotlin
@Test
fun testRatingInitialState() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.ratingCard))
        .check(matches(withRating(0.0f)))
}
```

#### Test de Actualización de Rating
Comprueba que el sistema actualiza correctamente la puntuación cuando el usuario cambia el rating y que se muestra el valor correcto.

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

## 3. Pruebas de Volumen y Estrés

### 3.1 Prueba de Carga Masiva
Esta prueba verifica el rendimiento de la aplicación al cargar una gran cantidad de cafeterías, asegurando que la carga se realice en un tiempo razonable.

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
    assert(endTime - startTime < 1000) // Debe cargar en menos de 1 segundo
}
```

### 3.2 Prueba de Navegación Intensiva
Simula un uso intensivo de la navegación para detectar posibles problemas de memoria o rendimiento.

```kotlin
@Test
fun testRapidNavigationStress() {
    launchFragmentInContainer<Cafeterias>()
    
    repeat(100) {
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(
                it % CoffeeShopProvider.coffeeshopList.size, click()
            ))
        
        pressBack()
    }
}
```

## 4. Pruebas de Seguridad

### 4.1 Prueba de Sanitización de Entrada
Verifica que la aplicación maneja correctamente entradas potencialmente maliciosas y las sanitiza adecuadamente.

```kotlin
@Test
fun testInputSanitization() {
    val maliciousInput = "<script>alert('test')</script>"
    
    val bundle = Bundle().apply {
        putString("title", maliciousInput)
    }
    
    launchFragmentInContainer<Valoraciones>(bundle)
    
    onView(withId(R.id.nameCard))
        .check(matches(not(withText(containsString("<script>")))))
}
```

### 4.2 Prueba de Manejo de Nulos
Asegura que la aplicación maneja correctamente los casos donde los datos pueden ser nulos sin crashear.

```kotlin
@Test
fun testNullHandling() {
    val bundle = Bundle().apply {
        putString("title", null)
    }
    
    launchFragmentInContainer<Valoraciones>(bundle)
    
    onView(withId(R.id.nameCard))
        .check(matches(isDisplayed()))
}
```

## 5. Pruebas de Uso

### 5.1 Prueba de Accesibilidad
Verifica que la aplicación es accesible para usuarios que utilizan servicios de accesibilidad como TalkBack.

```kotlin
@Test
fun testAccessibilityLabels() {
    launchFragmentInContainer<Cafeterias>()
    
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    onView(withId(R.id.imageCard))
        .check(matches(withContentDescription()))
    
    onView(withId(R.id.ratingCard))
        .check(matches(withContentDescription()))
}
```

### 5.2 Prueba de Flujo Completo de Usuario
Simula un flujo completo de usuario desde la selección de una cafetería hasta la valoración y regreso.

```kotlin
@Test
fun testCompleteUserJourney() {
    launchFragmentInContainer<Cafeterias>()
    
    // 1. Ver lista
    onView(withId(R.id.recyclerView))
        .check(matches(isDisplayed()))
    
    // 2. Seleccionar cafetería
    onView(withId(R.id.recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
    
    // 3. Dar valoración
    onView(withId(R.id.ratingCard))
        .perform(setRating(4.0f))
    
    // 4. Verificar actualización
    onView(withId(R.id.puntuacionCard))
        .check(matches(withText("4.0")))
}
```
