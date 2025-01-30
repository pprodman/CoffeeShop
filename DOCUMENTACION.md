# Documentación del Proyecto CoffeeShop

## 📌 Descripción
Este documento detalla la estructura del código del proyecto **CoffeeShop**, incluyendo las clases principales, su ubicación en el paquete y sus métodos esenciales.

---

## 📂 Paquete: `com.example.coffeeshop`

## Clases Principales

### 🏷️ `MainActivity`
- **Ubicación:** `com.example.coffeeshop`
- **Descripción:** Actividad principal que gestiona la lista de cafeterías y la navegación entre pantallas.
- **Métodos principales:**
  - `onCreate(savedInstanceState: Bundle?)`: Inicializa la interfaz de usuario y el RecyclerView.
  - `setupRecyclerView()`: Configura el RecyclerView con su adaptador.
  - `navigateToDetails(coffeeShop: CoffeeShop)`: Maneja la navegación a la pantalla de detalles.

---

### 🏷️ `Cafeterias`
- **Ubicación:** `com.example.coffeeshop.fragments`
- **Descripción:** Fragmento que muestra la lista de cafeterías disponibles en la aplicación.
- **Métodos principales:**
  - `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)`: Infla la vista del fragmento.
  - `setupRecyclerView()`: Configura y carga el RecyclerView con la lista de cafeterías.
  - `onCoffeeShopSelected(coffeeShop: CoffeeShop)`: Maneja la navegación a la vista de detalles de una cafetería.

---

### 🏷️ `Valoraciones`
- **Ubicación:** `com.example.coffeeshop.fragments`
- **Descripción:** Fragmento que permite a los usuarios calificar y ver las valoraciones de una cafetería.
- **Métodos principales:**
  - `onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)`: Infla la vista del fragmento.
  - `loadRatings()`: Carga las valoraciones previas desde la base de datos.
  - `submitRating(rating: Float)`: Guarda la nueva calificación dada por el usuario.

---

### 🏷️ `CoffeeShopAdapter`
- **Ubicación:** `com.example.coffeeshop.adapters`
- **Descripción:** Adaptador para el RecyclerView encargado de gestionar la lista de cafeterías.
- **Métodos principales:**
  - `onCreateViewHolder(parent: ViewGroup, viewType: Int)`: Crea una nueva vista para un elemento de la lista.
  - `onBindViewHolder(holder: ViewHolder, position: Int)`: Asigna datos a la vista del elemento en la posición dada.
  - `getItemCount()`: Retorna el número total de elementos.

---

### 🏷️ `ViewHolder`
- **Ubicación:** Dentro de `CoffeeShopAdapter`
- **Descripción:** Contiene referencias a la vista de cada elemento del RecyclerView.
- **Métodos principales:**
  - `bind(coffeeShop: CoffeeShop, onClick: (CoffeeShop) -> Unit)`: Asigna datos a la vista y gestiona eventos de clic.

---

### 🏷️ `CoffeeShop`
- **Ubicación:** `com.example.coffeeshop.models`
- **Descripción:** Representa una cafetería en la aplicación.
- **Atributos:**
  - `name: String`: Nombre de la cafetería.
  - `address: String`: Dirección de la cafetería.
  - `imageRes: Int`: ID del recurso de la imagen de la cafetería.

