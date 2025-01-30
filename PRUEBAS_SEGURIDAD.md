# Pruebas de Seguridad

## **Casos de Prueba:**
### **1 Prueba de Sanitización de Entrada**
- **Escenario:** Asegurar que los inputs no permiten inyecciones de código malicioso.
- **Resultado esperado:** La aplicación muestra solo texto limpio sin ejecutar scripts.

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
