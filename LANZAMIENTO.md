
# Lanzamiento de la APP 🚀

## Índice

1. [Configura la app para lanzamiento](##%EF%B8%8F-1-configura-la-app-para-lanzamiento)
2. [Crear el a archivo Keystore](#2-crear-el-archivo-keystore)
3. [Configurar la firma en Gradle](#3-configurar-la-firma-en-gradle)
4. [Generar el APK firmado](#4-generar-el-apk-firmado)
5. [Verificar el APK firmado](#5-verificar-el-apk-firmado)
6. [Testea la versión de lanzamiento](#6-testea-la-versión-de-lanzamiento)
7. [Actualiza los recursos de la aplicación](#7-actualiza-los-recursos-de-la-aplicación)
8. [Prepara los servidores y servicios remotos](#8-prepara-los-servidores-y-servicios-remotos)
9. [Lanzamiento de la Aplicación](#9-lanzamiento-de-la-aplicación)

---

## **🏷️ 1. Configura la app para lanzamiento**

### **a. Desactivar el registro (logs) y modo depurable**
Para asegurarte de que tu aplicación no exponga información sensible en producción, desactiva los registros y el modo depurable.

#### **En `build.gradle.kts` (Kotlin DSL):**
```kotlin
android {
    buildTypes {
        getByName("release") {
            isDebuggable = false // Desactiva el modo depurable
            isMinifyEnabled = true // Habilita la ofuscación de código
            isShrinkResources = true // Elimina recursos no utilizados
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### **b. Configurar la versión de la app**
Define la versión de tu aplicación en el archivo `build.gradle`:

#### **En `build.gradle.kts` (Kotlin DSL):**
```kotlin
android {
    defaultConfig {
        versionCode = 1 // Número de versión interna (incrementa con cada actualización)
        versionName = "1.0" // Versión visible para los usuarios
    }
}
```

---

## **🏷️ 2. Crear el archivo Keystore**

El archivo **Keystore** es necesario para firmar tu APK o AAB. Contiene las claves privadas que identifican tu aplicación como única en Google Play.

### **Opción 1: Usar Android Studio**
1. Abre tu proyecto en **Android Studio**.
2. Ve al menú superior y selecciona:
   ```
   Build > Generate Signed Bundle / APK
   ```
3. En la ventana emergente, selecciona **APK** (o **Android App Bundle**, si prefieres usar AAB) y haz clic en **Next**.
4. Haz clic en **Create new...** para crear un nuevo archivo Keystore.
5. Completa los siguientes campos:
   - **Key store path**: Selecciona una ubicación segura para guardar el archivo `keystore.jks`.
   - **Password**: Ingresa una contraseña segura para el Keystore.
   - **Confirm**: Confirma la contraseña.
   - **Alias**: Ingresa un nombre único para la clave (por ejemplo, `my-key-alias`).
   - **Password**: Ingresa una contraseña para la clave (puede ser la misma que la del Keystore).
   - **Validity (years)**: Ingresa cuántos años será válida la clave (recomendado: 25 años).
   - **Certificate information**: Completa los datos requeridos (nombre, organización, ciudad, país, etc.).
6. Haz clic en **OK** para guardar el archivo Keystore.

### **Opción 2: Usar la línea de comandos**
Si prefieres usar la herramienta `keytool` desde la línea de comandos, sigue estos pasos:

1. Abre una terminal o consola.
2. Ejecuta el siguiente comando:
   ```bash
   keytool -genkeypair -v \
     -keystore keystore.jks \
     -alias my-key-alias \
     -keyalg RSA \
     -keysize 2048 \
     -validity 10000
   ```
3. Te pedirá que ingreses:
   - Una contraseña para el Keystore.
   - Información del certificado (nombre, organización, ciudad, etc.).
4. El archivo `keystore.jks` se generará en la ubicación especificada.

---

## **🏷️ 3. Configurar la firma en Gradle**

Una vez que tengas el archivo Keystore, configúralo en tu archivo `build.gradle` para automatizar el proceso de firma.

### **En Kotlin DSL (`build.gradle.kts`):**
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("ruta/al/keystore.jks") // Ruta al archivo Keystore
            storePassword = "tu_contraseña_keystore"
            keyAlias = "my-key-alias"
            keyPassword = "tu_contraseña_clave"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

---

## **🏷️ 4. Generar el APK firmado**

### **Opción 1: Usar Android Studio**
1. Ve al menú superior y selecciona:
   ```
   Build > Generate Signed Bundle / APK
   ```
2. Selecciona **APK** y haz clic en **Next**.
3. Configura lo siguiente:
   - **Module**: Selecciona el módulo principal de tu aplicación.
   - **Key store path**: Selecciona el archivo Keystore que creaste.
   - **Passwords**: Ingresa las contraseñas del Keystore y la clave.
4. Selecciona la variante de compilación (`release`) y haz clic en **Finish**.
5. Android Studio generará el APK firmado en la carpeta:
   ```
   app/build/outputs/apk/release/
   ```

### **Opción 2: Usar la línea de comandos**
1. Abre una terminal o consola.
2. Navega al directorio raíz de tu proyecto.
3. Ejecuta el siguiente comando para compilar el APK:
   ```bash
   ./gradlew assembleRelease
   ```
4. El APK firmado estará en:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

---

## **🏷️ 5. Verificar el APK firmado**

Para asegurarte de que el APK esté correctamente firmado, usa la herramienta `apksigner`:

1. Abre una terminal o consola.
2. Ejecuta el siguiente comando:
   ```bash
   apksigner verify --verbose app/build/outputs/apk/release/app-release.apk
   ```
3. Si el APK está correctamente firmado, verás un mensaje como este:
   ```
   Verified using v1 scheme (JAR signing): true
   Verified using v2 scheme (APK Signature Scheme v2): true
   ```

---

---

## **🏷️ 6. Testea la versión de lanzamiento**

### **a. Pruebas manuales**
Instala la versión de lanzamiento en un dispositivo físico o emulador:
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

### **b. Firebase Test Lab**
Usa Firebase Test Lab para ejecutar pruebas automatizadas en múltiples dispositivos:
1. Sube tu APK/AAB a Firebase Console.
2. Configura las pruebas (por ejemplo, pruebas de interfaz o pruebas de rendimiento).
3. Ejecuta las pruebas y revisa los resultados.

---

## **🏷️ 7. Actualiza los recursos de la aplicación**

### **a. Archivos multimedia y gráficos**
- Asegúrate de que todos los recursos (imágenes, videos, etc.) estén optimizados para producción.
- Usa herramientas como [TinyPNG](https://tinypng.com/) para comprimir imágenes sin perder calidad.

### **b. Almacenamiento provisional**
- Si tus recursos dependen de servidores externos, asegúrate de que estén almacenados en los servidores de producción adecuados.

---

## **🏷️ 8. Prepara los servidores y servicios remotos**

### **a. Verifica la seguridad**
- Asegúrate de que los servidores usen conexiones seguras (HTTPS).
- Implementa autenticación y autorización robustas.

### **b. Escalabilidad**
- Asegúrate de que los servidores puedan manejar el tráfico esperado.
- Configura monitoreo y alertas para detectar problemas en tiempo real.

---

## **🏷️ 9. Lanzamiento de la Aplicación**

### **a. Lanzamiento en Google Play**
Google Play es el mercado de aplicaciones más grande y popular para Android, lo que lo convierte en la opción ideal si deseas alcanzar una audiencia global. Aquí tienes los pasos para publicar tu aplicación en Google Play:

#### **Paso 1: Crear una cuenta de desarrollador en Google Play**
1. Ve al [Google Play Console](https://play.google.com/console/).
2. Inicia sesión con tu cuenta de Google.
3. Regístrate como desarrollador pagando una tarifa única de **$25 USD** (esto puede variar según la región).
4. Completa el formulario con la información requerida (nombre de la empresa, dirección, etc.).

#### **Paso 2: Preparar tu aplicación para Google Play**
1. **Genera un APK o AAB firmado**:
   - Sigue los pasos descritos anteriormente para crear un archivo `.apk` o `.aab` firmado.
   - Recuerda que Google Play recomienda usar **Android App Bundle (AAB)** para optimizar el tamaño de la aplicación.
2. **Configura la versión de tu aplicación**:
   - Define `versionCode` y `versionName` en tu archivo `build.gradle`.
3. **Optimiza los recursos**:
   - Comprime imágenes, elimina archivos innecesarios y asegúrate de que la aplicación esté lista para producción.

#### **Paso 3: Crear una lista de la aplicación en Google Play**
1. En el **Google Play Console**, selecciona **Crear aplicación**.
2. Ingresa el nombre de tu aplicación y selecciona el idioma predeterminado.
3. Completa los siguientes apartados:
   - **Descripción**: Escribe una descripción clara y atractiva de tu aplicación.
   - **Capturas de pantalla**: Sube capturas de pantalla de alta calidad en diferentes tamaños.
   - **Icono de la aplicación**: Sube un ícono que cumpla con las especificaciones de diseño de Google Play.
   - **Categoría**: Selecciona la categoría adecuada para tu aplicación (por ejemplo, "Herramientas", "Entretenimiento").
   - **Política de privacidad**: Proporciona un enlace a tu política de privacidad (obligatorio si usas datos personales).

#### **Paso 4: Configurar precios y distribución**
1. Elige si tu aplicación será gratuita o de pago.
2. Configura la disponibilidad geográfica de tu aplicación.
3. Habilita funciones adicionales si es necesario:
   - Facturación integrada en la aplicación.
   - Licencias de aplicaciones.

#### **Paso 5: Publicar la aplicación**
1. Sube tu archivo `.aab` o `.apk` en la sección **Producción** del Google Play Console.
2. Revisa todos los detalles antes de enviar la aplicación para su revisión.
3. Una vez aprobada (generalmente toma unos días), tu aplicación estará disponible en Google Play.

---

### **b. Distribución a través de un sitio web propio**

Si prefieres no usar Google Play, puedes distribuir tu aplicación directamente desde tu sitio web. Esto es útil para aplicaciones empresariales o públicos específicos.

#### **Paso 1: Preparar tu aplicación para el lanzamiento**
1. Genera un **APK firmado** siguiendo los pasos mencionados anteriormente.
2. Asegúrate de que el APK esté optimizado y listo para producción.

#### **Paso 2: Alojar el APK en tu sitio web**
1. Sube el archivo APK a tu servidor web.
   - Por ejemplo, puedes colocarlo en una carpeta como `/downloads/app-release.apk`.
2. Crea una página de descarga en tu sitio web con información sobre la aplicación:
   - Descripción.
   - Capturas de pantalla.
   - Instrucciones de instalación.

#### **Paso 3: Proporcionar un enlace de descarga**
1. Crea un enlace directo al archivo APK en tu sitio web:
   ```html
   <a href="https://tusitio.com/downloads/app-release.apk">Descargar aplicación</a>
   ```
2. Asegúrate de que el enlace sea accesible desde dispositivos Android.

#### **Paso 4: Instrucciones para los usuarios**
Proporciona instrucciones claras para que los usuarios puedan instalar la aplicación:
1. Descarguen el archivo APK desde tu sitio web.
2. Habiliten la opción **Orígenes desconocidos** en su dispositivo:
   - Ve a **Configuración > Seguridad > Orígenes desconocidos**.
3. Instalen el archivo APK descargado.

#### **Paso 5: Promocionar tu aplicación**
- Usa redes sociales, correos electrónicos o campañas de marketing para promocionar tu aplicación.
- Asegúrate de que tu sitio web sea fácil de navegar y proporcione suficiente información sobre la aplicación.

---

### **Ventajas y desventajas de cada método**

| **Método**               | **Ventajas**                                                                 | **Desventajas**                                                                 |
|--------------------------|-----------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| **Google Play**           | - Acceso a una audiencia global.                                           | - Tarifa inicial de $25 USD.                                                   |
|                          | - Herramientas avanzadas de análisis y monitoreo.                          | - Proceso de revisión puede demorar varios días.                               |
|                          | - Mayor confianza por parte de los usuarios.                               | - Comisión del 15-30% en ventas de aplicaciones o compras dentro de la app.    |
| **Sitio web propio**      | - Sin tarifas iniciales ni comisiones.                                     | - Menor visibilidad y alcance.                                                |
|                          | - Control total sobre la distribución y actualizaciones.                   | - Los usuarios deben habilitar "Orígenes desconocidos" manualmente.            |
|                          | - Ideal para aplicaciones empresariales o públicos específicos.            | - Mayor responsabilidad en la promoción y soporte técnico.                     |

---

### **Consejos adicionales**

1. **Actualizaciones regulares**:
   - Mantén tu aplicación actualizada para corregir errores y agregar nuevas funciones.
   - Si usas Google Play, puedes configurar actualizaciones automáticas.

2. **Seguridad**:
   - Usa HTTPS en tu sitio web para proteger las descargas.
   - Asegúrate de que el Keystore esté almacenado de forma segura.

3. **Pruebas previas al lanzamiento**:
   - Usa herramientas como **Firebase Test Lab** o pruebas beta para validar tu aplicación antes de lanzarla.

4. **Marketing**:
   - Crea una estrategia de marketing para promocionar tu aplicación, ya sea en Google Play o en tu sitio web.

---
