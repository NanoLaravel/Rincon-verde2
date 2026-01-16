# Copilot Instructions for Rinconverde2

This is a modern Android tourism/travel guide app built with **Jetpack Compose**, **Hilt DI**, **Room database**, and **Clean Architecture** patterns.

## Architecture Overview

The project follows **Clean Architecture** with three main layers:

- **Presentation Layer** (`ui/`): Compose screens, ViewModels, UI state
- **Domain Layer** (`domain/`): Business logic, use cases, models
- **Data Layer** (`data/`): Repositories, local (Room) & remote (Retrofit) data sources

### Key Project Structure

```
app/src/main/java/com/example/rincon_verde2/
├── ui/
│   ├── feature/          # Screen implementations (home, auth, splash, etc.)
│   ├── components/       # Reusable Compose components
│   ├── navigation/       # NavGraph.kt, Screen.kt (route definitions)
│   └── theme/            # Material3 theming (Color, Theme, Type)
├── domain/
│   ├── model/            # Domain models (Place, Event, User)
│   └── usecase/          # Business logic organized by feature
├── data/
│   ├── model/            # Entity classes for Room DB
│   ├── local/            # Room database, DAOs
│   ├── remote/           # Retrofit API clients
│   └── repository/       # Repository interfaces and implementations
├── di/                   # Hilt dependency injection modules
└── Application.kt        # @HiltAndroidApp entry point
```

## Key Technologies & Patterns

### Dependencies & Versions
- **Kotlin** 2.1.0 with Compose compiler 1.5.3
- **Jetpack Compose** BOM 2025.12.01 (Material3, Navigation)
- **Hilt** 2.57.2 for dependency injection
- **Room** 2.8.4 for local persistence
- **Retrofit** 3.0.0 + OkHttp 5.3.2 for networking
- **Coil** 2.7.0 for image loading
- **DataStore** 1.2.0 for preferences
- **JVM Target**: 11 (set in `build.gradle.kts` under `kotlinOptions`)

### Build Configuration
- **compileSdk**: 36 (Android 15)
- **minSdk**: 24, **targetSdk**: 36
- **Non-transitive R class**: enabled (`android.nonTransitiveRClass=true`)
- **AndroidX namespacing**: enabled
- **Minification**: disabled in release builds (ProGuard available but unused)

## Development Patterns

### DI Setup (Hilt)
- Entry point: `@HiltAndroidApp` on `RinconVerde2Application` class
- Activities annotated with `@AndroidEntryPoint`
- Dependency modules in `di/` folder (AppModule, NetworkModule, DatabaseModule)
- Example: `MainActivity` uses Hilt to inject dependencies

### UI State Management
- **ViewModel Pattern**: Feature screens have dedicated ViewModels
- **State Classes**: Each feature has `*UiState` data class (e.g., `HomeUiState`)
- **Material3 Theming**: Dynamic color support on Android 12+, with dark/light modes

### Data Models Convention
- **Entity Classes** (Room): `*Entity.kt` in `data/model/` (e.g., `PlaceEntity`, `EventEntity`)
- **Domain Models**: Plain data classes in `domain/model/` (e.g., `Place`, `Event`, `User`)
- **Mapping**: Entities map to domain models via repository layer

### Repository Pattern
- Abstractions in `data/repository/` as interfaces
- Implementations in `data/repository/impl/`
- Repositories coordinate between local (Room) and remote (Retrofit) sources

### Navigation
- Type-safe routes defined in `ui/navigation/Screen.kt`
- NavGraph setup in `ui/navigation/NavGraph.kt` using `@Composable`

## Build & Test Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Lint check
./gradlew lint

# Build release (ProGuard disabled by default, enable if needed)
./gradlew assembleRelease

# Clean and rebuild
./gradlew clean build
```

## Important Conventions

1. **Package Structure by Feature**: UI screens organized by feature (auth/, favorites/, home/, etc.)
2. **Compose Best Practices**: Use `@Composable` preview functions for component testing
3. **Hilt Scopes**: Use appropriate scopes (@ApplicationScope, @ActivityScope) for singleton vs. instance management
4. **Room Queries**: Use Kotlin Flow for reactive queries
5. **Error Handling**: Not yet established—recommend Result sealed class or try/catch with logging

## Integration Points

- **Local Storage**: Room DAOs in `data/local/dao/` (PlaceDao, EventDao, UserDao)
- **Remote API**: Retrofit services (implementation details in `data/remote/`)
- **Image Loading**: Coil used in Compose components (e.g., `AsyncImage` from coil-compose)
- **Networking Logging**: OkHttp logging interceptor configured in NetworkModule

## Current App Features

Main screens include:
- **Splash Screen**: Initial loading/branding
- **Home Screen**: Place discovery with categories (EAT, STAY, ACTIVITY), search, filters
- **Place Detail**: Individual place information
- **Favorites**: Bookmarked places
- **Events**: Tourism events
- **Search**: Unified search across places/events
- **Profile**: User account information
- **Auth**: Login/registration (if implemented)

## When Writing Code

- Reference [MainActivity.kt](app/src/main/java/com/example/rincon_verde2/MainActivity.kt) for sample Compose setup
- Follow the [HomeScreen.kt](app/src/main/java/com/example/rincon_verde2/ui/feature/home/HomeScreen.kt) structure for new screens
- Add new features under `ui/feature/{feature_name}/` with ViewModel + UiState pattern
- Use `gradle/libs.versions.toml` for dependency versioning (never hardcode versions in build.gradle.kts)
