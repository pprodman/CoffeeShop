# Pruebas Detalladas - Coffee Shop Android App

## 1. Pruebas de Integración

### 1.1 Pruebas de Navegación
```kotlin
class NavigationIntegrationTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    
    @Test
    fun testCafeteriaToValoracionesNavigation() {
        launchFragmentInContainer<Cafeterias>()
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        onView(withId(R.id.nameCard))
            .check(matches(isDisplayed()))
            .check(matches(withText("Antico Caffè Greco")))
    }
    
    @Test
    fun testSharedElementTransition() {
        launchFragmentInContainer<Cafeterias>()
        
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        onView(withId(R.id.nameCard))
            .check(matches(withTransitionName("tranTitle")))
    }
    
    @Test
    fun testBackNavigation() {
        launchFragmentInContainer<Cafeterias>()
        
        // Navegar a Valoraciones
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        // Volver atrás
        pressBack()
        
        // Verificar regreso a Cafeterias
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
}
```

### 1.2 Pruebas del RecyclerView
```kotlin
class RecyclerViewIntegrationTests {
    @Test
    fun testRecyclerViewItemCount() {
        val testCoffeeShops = CoffeeShopProvider.coffeeshopList
        
        launchFragmentInContainer<Cafeterias>()
        
        onView(withId(R.id.recyclerView))
            .check(matches(hasChildCount(testCoffeeShops.size)))
    }
    
    @Test
    fun testRecyclerViewItemContent() {
        launchFragmentInContainer<Cafeterias>()
        
        // Verificar primer item
        onView(withId(R.id.recyclerView))
            .check(matches(hasDescendant(withText("Antico Caffè Greco"))))
            .check(matches(hasDescendant(withText("St. Italy, Rome"))))
        
        // Verificar imagen
        onView(allOf(withId(R.id.imageCard), isDescendantOfA(withId(R.id.recyclerView))))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun testRecyclerViewScrolling() {
        launchFragmentInContainer<Cafeterias>()
        
        // Scroll al último item
        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(
                CoffeeShopProvider.coffeeshopList.size - 1
            ))
        
        // Verificar último item
        onView(withText("Sweet Cup"))
            .check(matches(isDisplayed()))
    }
}
```

## 2. Pruebas de Regresión

### 2.1 Pruebas del Sistema de Rating
```kotlin
class RatingRegressionTests {
    @Test
    fun testRatingInitialState() {
        launchFragmentInContainer<Cafeterias>()
        
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        onView(withId(R.id.ratingCard))
            .check(matches(withRating(0.0f)))
    }
    
    @Test
    fun testRatingUpdate() {
        launchFragmentInContainer<Cafeterias>()
        
        // Navegar y dar rating
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        onView(withId(R.id.ratingCard))
            .perform(setRating(4.5f))
        
        onView(withId(R.id.puntuacionCard))
            .check(matches(withText("4.5")))
    }
    
    @Test
    fun testRatingPersistenceAfterRotation() {
        val scenario = launchFragmentInContainer<Valoraciones>()
        
        // Dar rating
        onView(withId(R.id.ratingCard))
            .perform(setRating(3.5f))
        
        // Rotar pantalla
        scenario.recreate()
        
        // Verificar persistencia
        onView(withId(R.id.ratingCard))
            .check(matches(withRating(3.5f)))
    }
}
```

## 3. Pruebas de Volumen y Estrés

### 3.1 Pruebas de Carga
```kotlin
class VolumeStressTests {
    @Test
    fun testLargeDatasetLoading() {
        // Generar dataset grande
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
        assert(endTime - startTime < 1000) // Menos de 1 segundo
    }
    
    @Test
    fun testRapidNavigationStress() {
        launchFragmentInContainer<Cafeterias>()
        
        repeat(100) {
            // Navegar a Valoraciones
            onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(
                    it % CoffeeShopProvider.coffeeshopList.size, click()
                ))
            
            // Volver
            pressBack()
        }
    }
    
    @Test
    fun testMemoryConsumption() {
        val runtime = Runtime.getRuntime()
        val initialMemory = runtime.totalMemory() - runtime.freeMemory()
        
        // Realizar operaciones intensivas
        repeat(50) {
            launchFragmentInContainer<Cafeterias>()
            onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(
                    CoffeeShopProvider.coffeeshopList.size - 1
                ))
        }
        
        val finalMemory = runtime.totalMemory() - runtime.freeMemory()
        val memoryDiff = finalMemory - initialMemory
        
        // No debe haber fuga de memoria significativa
        assert(memoryDiff < 10_000_000) // Menos de 10MB de diferencia
    }
}
```

## 4. Pruebas de Seguridad

### 4.1 Pruebas de Validación
```kotlin
class SecurityTests {
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
    
    @Test
    fun testNullHandling() {
        val bundle = Bundle().apply {
            putString("title", null)
        }
        
        launchFragmentInContainer<Valoraciones>(bundle)
        
        // No debe crashear con input null
        onView(withId(R.id.nameCard))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun testLongInputHandling() {
        val longInput = "a".repeat(1000)
        
        val bundle = Bundle().apply {
            putString("title", longInput)
        }
        
        launchFragmentInContainer<Valoraciones>(bundle)
        
        // Verificar que el texto se trunca apropiadamente
        onView(withId(R.id.nameCard))
            .check(matches(withEllipsizedText()))
    }
}
```

## 5. Pruebas de Uso

### 5.1 Pruebas de Accesibilidad
```kotlin
class UsabilityTests {
    @Test
    fun testAccessibilityLabels() {
        launchFragmentInContainer<Cafeterias>()
        
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<CoffeeShopAdapter.CoffeeShopViewHolder>(0, click()))
        
        // Verificar descripciones de contenido
        onView(withId(R.id.imageCard))
            .check(matches(withContentDescription()))
        
        onView(withId(R.id.ratingCard))
            .check(matches(withContentDescription()))
    }
    
    @Test
    fun testTouchTargetSizes() {
        launchFragmentInContainer<Cafeterias>()
        
        // Verificar tamaños mínimos para accesibilidad
        onView(withId(R.id.ratingCard))
            .check(matches(hasMinimumTouchTargetSize()))
    }
    
    @Test
    fun testColorContrast() {
        launchFragmentInContainer<Cafeterias>()
        
        // Verificar contraste de texto
        onView(withId(R.id.nameCard))
            .check(matches(hasMinimumContrastRatio()))
    }
}
```

### 5.2 Pruebas de Flujo de Usuario
```kotlin
class UserFlowTests {
    @Test
    fun testCompleteUserJourney() {
        launchFragmentInContainer<Cafeterias>()
        
        // 1. Ver lista de cafeterías
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
        
        // 5. Volver y verificar persistencia
        pressBack()
        
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }
    
    @Test
    fun testErrorHandling() {
        launchFragmentInContainer<Cafeterias>()
        
        // Simular error de carga
        activityRule.scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.adapter = null
        }
        
        // Verificar manejo de error
        onView(withId(R.id.errorMessage))
            .check(matches(isDisplayed()))
    }
}
```

## Configuración Necesaria

```kotlin
class TestSetup {
    @Before
    fun setup() {
        // Configuración general
        Espresso.setFailureHandler(CustomFailureHandler())
        
        // Deshabilitar animaciones
        val context = ApplicationProvider.getApplicationContext<Context>()
        Settings.Global.putFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            0f
        )
        
        // Configurar mock navigation
        every { mockNavController.navigate(any<Int>(), any(), any(), any()) } just Runs
    }
    
    // Custom matchers
    private fun withRating(expectedRating: Float) = object : BoundedMatcher<View, RatingBar>(RatingBar::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with rating: $expectedRating")
        }
        
        override fun matchesSafely(item: RatingBar): Boolean {
            return item.rating == expectedRating
        }
    }
    
    private fun withEllipsizedText() = object : BoundedMatcher<View, TextView>(TextView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with ellipsized text")
        }
        
        override fun matchesSafely(item: TextView): Boolean {
            return item.ellipsize == TextUtils.TruncateAt.END
        }
    }
}
```
