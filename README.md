# Rincon Verde 🌿

> Aplicación móvil Android para descubrir y explorar lugares turísticos en Colombia.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-000000?style=for-the-badge&logo=jetpackcompose&logoColor=white)

## 📱 Acerca del Proyecto

**Rincon Verde** es una aplicación Android nativa que permite a los usuarios descubrir lugares turísticos, eventos y experiencias en Colombia. La app incluye búsqueda avanzada, filtros por categoría, listados de lugares mejor valorados, gestión de favoritos y perfil de usuario.

### Características Principales

- 🏠 **Pantalla de Inicio** - Lugares mejor valorados, eventos próximos y categorías
- 🔍 **Búsqueda Avanzada** - Filtros por categoría, rating, distancia y más
- 📋 **Listado por Categoría** - Hoteles, restaurantes, atractivos turísticos, etc.
- ⭐ **Favoritos** - Guarda tus lugares favoritos para rápida referencia
- 👤 **Perfil de Usuario** - Historial, preferencias y gestión de cuenta
- 🔐 **Autenticación** - Sistema de login seguro

## 🛠️ Tecnologías

### Framework & Lenguaje

| Tecnología | Versión |
|------------|---------|
| Kotlin | 2.1.0 |
| Android Gradle Plugin | 8.13.2 |
| Target SDK | 36 |
| Min SDK | 24 |

### Librerías Principales

| Categoría | Librería |
|-----------|----------|
| **UI** | Jetpack Compose (BOM 2025.12.01), Material 3 |
| **Navegación** | Navigation Compose 2.9.6 |
| **Inyección de Dependencias** | Hilt 2.57.2 |
| **Networking** | Retrofit 3.0.0, OkHttp 5.3.2 |
| **Base de Datos Local** | Room 2.8.4 |
| **Gestión de Imágenes** | Coil 2.7.0 |
| **Persistencia de Preferencias** | DataStore 1.2.0 |
| **Serialización** | Kotlinx Serialization 1.6.2 |

### Arquitectura

```
├── domain/           # Capa de dominio (modelos, casos de uso)
├── data/             # Capa de datos (repositorios, API, local DB)
├── di/               # Módulos de inyección de dependencias
└── ui/               # Capa de presentación (screens, viewmodels, componentes)
```

El proyecto sigue **Clean Architecture** con separación clara de responsabilidades:

- **Domain Layer**: Modelos de negocio y casos de uso
- **Data Layer**: Repositorios, fuentes de datos (remote/local)
- **UI Layer**: Composables, ViewModels y estado de UI

### Patrones de Diseño

- **MVVM** (Model-View-ViewModel) para la capa de presentación
- **Repository Pattern** para acceso a datos
- **Dependency Injection** con Hilt
- **State Management** con Kotlin Flow y StateFlow

## 🚀 Primeros Pasos

### Requisitos Previos

- JDK 17 o superior
- Android Studio Koala o superior
- Gradle 8.x

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/NanoLaravel/Rincon-verde2.git
   cd Rincon-verde2
   ```

2. **Abrir en Android Studio**
   - File → Open → Seleccionar carpeta del proyecto
   - Esperar a que Gradle sincronice las dependencias

3. **Configurar el entorno**
   - Crear archivo `local.properties` con la ruta del SDK:
     ```properties
     sdk.dir=/ruta/a/android/sdk
     ```

4. **Ejecutar**
   - Conectar un dispositivo o iniciar un emulador
   - Run → Run 'app'

### Construcción

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requiere firma)
./gradlew assembleRelease
```

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/rincon_verde2/
├── data/
│   ├── local/           # Room DB, DAOs, Entity
│   ├── remote/          # API Service, DTOs
│   └── repository/      # Implementaciones de repositorios
├── di/                  # Módulos Hilt
├── domain/
│   ├── model/           # Modelos de dominio
│   └── usecase/         # Casos de uso
└── ui/
    ├── components/      # Componentes reutilizables
    ├── feature/          # Features por pantalla
    │   ├── auth/
    │   ├── home/
    │   ├── search/
    │   ├── favorites/
    │   ├── placelist/
    │   ├── placedetail/
    │   └── profile/
    ├── navigation/      # NavGraph y Screen definitions
    └── theme/           # Colores, tipografía, temas
```

## 🔐 Seguridad

- ✅ Autenticación con tokens JWT (implementación del lado del servidor)
- ✅ Almacenamiento seguro de tokens con EncryptedSharedPreferences
- ✅ Comunicación exclusivamente HTTPS
- ✅ ProGuard/R8 habilitado para builds de release
- ⚠️ Las credenciales de API nunca se almacenan en el código

## 🤝 Contribución

1. Crear una rama desde `develop`: `git checkout -b feature/nueva-caracteristica`
2. Realizar cambios y agregar tests
3. Commitear con mensajes claros (ver [Conventional Commits](docs/GITFLOW.md))
4. Crear Pull Request hacia `develop`
5. Esperar revisión y approval

## 📄 Licencia

Este proyecto es de uso privado. Todos los derechos reservados.

## 📞 Contacto

- **Desarrollador**: NanoLaravel
- **Repositorio**: https://github.com/NanoLaravel/Rincon-verde2

---

*Documento creado con ❤️ para el proyecto Rincon Verde*
