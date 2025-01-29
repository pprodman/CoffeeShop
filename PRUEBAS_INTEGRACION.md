
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
