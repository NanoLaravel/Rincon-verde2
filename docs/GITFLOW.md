# GitFlow - Rincon Verde 2

## Descripción General

Este documento define el flujo de trabajo con Git y GitHub para el proyecto Android "Rincon Verde 2", incluyendo prácticas de desarrollo, pruebas, seguridad y deployment.

---

## Estructura de Ramas

```
master (PRODUCTION)
  │
  ├── develop (INTEGRACIÓN)
  │     │
  │     ├── feature/* (NUEVAS FUNCIONALIDADES)
  │     │
  │     ├── bugfix/* (CORRECCIONES)
  │     │
  │     └── release/* (PRE-PRODUCTION)
  │
  └── hotfix/* (CORRECCIONES URGENTES)
```

### Ramas Principales

| Rama | Propósito | Protected |
|------|-----------|-----------|
| `master` | Producción estable | ✅ Sí |
| `develop` | Integración de features | ✅ Sí |
| `feature/*` | Desarrollo de nuevas funciones | ❌ No |
| `bugfix/*` | Corrección de bugs | ❌ No |
| `release/*` | Preparación para producción | ❌ No |
| `hotfix/*` | Correcciones urgentes en producción | ❌ No |

---

## Flujo de Trabajo

### 1. Iniciar una Nueva Feature

```bash
# Actualizar develop desde remoto
git checkout develop
git pull origin develop

# Crear rama feature desde develop
git checkout -b feature/nombre-descriptivo

# Desarrollar...
git add .
git commit -m "feat: descripción clara del cambio"

# Mantener sincronizado con develop
git fetch origin
git rebase origin/develop
```

### 2. Completar una Feature

```bash
# Ejecutar pruebas locales
./gradlew test
./gradlew connectedAndroidTest

# Verificar build de debug
./gradlew assembleDebug

# Hacer rebase con develop
git checkout develop
git pull origin develop
git checkout feature/nombre-descriptivo
git rebase develop

# Push de la rama feature
git push origin feature/nombre-descriptivo

# Crear Pull Request hacia develop
# En GitHub: Pull Request title: "feat: descripción"
# Descripción debe incluir: qué, por qué, cómo
```

### 3. Code Review (Pull Request)

**Antes de crear PR, asegurar:**
- [ ] Código compila sin errores
- [ ] Pruebas unitarias pasan (`./gradlew test`)
- [ ] Pruebas instrumentadas pasan (`./gradlew connectedAndroidTest`)
- [ ] No hay warnings de lint (`./gradlew lint`)
- [ ] Análisis de código completado
- [ ] Documentación actualizada

**Criterios de aceptación del PR:**
- Al menos 1 approve de reviewer
- Todos los CI checks pasan
- No hay conflictos con develop

### 4. Release a Producción

```bash
# Crear rama de release desde develop
git checkout develop
git pull origin develop
git checkout -b release/v1.0.0

# Actualizar versión en build.gradle.kts
# versionCode += 1
# versionName = "1.0.0"

# Commits de preparación (version bump, etc)
git commit -m "chore: bump version to 1.0.0"

# Merge a master y develop
git checkout master
git merge release/v1.0.0 --no-ff
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin master --tags

git checkout develop
git merge release/v1.0.0 --no-ff
git push origin develop

# Eliminar rama release
git branch -d release/v1.0.0
```

### 5. Hotfix en Producción

```bash
# Crear hotfix desde master
git checkout master
git pull origin master
git checkout -b hotfix/descripcion-del-fix

# Corregir y testing...
git commit -m "fix: descripción de la corrección"

# Merge a master y develop
git checkout master
git merge hotfix/descripcion-del-fix --no-ff
git tag -a v1.0.1 -m "Hotfix v1.0.1"
git push origin master --tags

git checkout develop
git merge hotfix/descripcion-del-fix --no-ff
git push origin develop

# Eliminar rama hotfix
git branch -d hotfix/descripcion-del-fix
```

---

## Conventional Commits

Usar conventional commits para mensajes claros y auto-generación de changelogs.

```
<tipo>(<alcance>): <descripción>

[可选 body]

[可选 footer]
```

### Tipos de Commits

| Tipo | Descripción |
|------|-------------|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `docs` | Documentación |
| `style` | Formato (no lógica) |
| `refactor` | Refactorización |
| `perf` | Mejora de rendimiento |
| `test` | Pruebas |
| `chore` | Tareas de mantenimiento |
| `build` | Cambios en build |
| `ci` | Cambios en CI/CD |

### Ejemplos

```bash
git commit -m "feat(auth): add biometric login support"
git commit -m "fix(api): handle timeout errors gracefully"
git commit -m "docs(readme): update installation instructions"
git commit -m "refactor(repository): extract common data logic"
git commit -m "test(favorites): add unit tests for toggle favorite"
```

---

## Pruebas Requeridas Antes de Producción

### Nivel 1: Pruebas Locales (Pre-commit)

```bash
# Pruebas unitarias
./gradlew test

# Análisis estático
./gradlew lint

# Verificar build debug
./gradlew assembleDebug
```

### Nivel 2: Pruebas de Integración (CI)

```bash
# Todas las pruebas
./gradlew testDebugUnitTest
./gradlew testDebugUnitTest --tests "com.example.rincon_verde2.*"

# Build de debug completo
./gradlew assembleDebug
```

### Nivel 3: QA (Pre-producción)

- Pruebas en dispositivo real
- Pruebas de regresión
- Pruebas de rendimiento
- UX testing

---

## Seguridad Pre-producción

### Checklist de Seguridad

- [ ] **No exponer API keys en código**
  - Usar `BuildConfig` para URLs base
  - Usar secrets manager o variables de entorno para keys sensibles
  
- [ ] **Obfuscation habilitada**
  - `isMinifyEnabled = true` (ya configurado)
  - `isShrinkResources = true` (ya configurado)
  
- [ ] **ProGuard rules actualizadas**
  - Mantener modelos de datos
  - Mantener reflexión de librerías
  
- [ ] **Certificados de firma**
  - Configurar signing config para release
  - No incluir certificados en repositorio
  
- [ ] **Network Security Config**
  - Solo HTTPS en producción
  - Certificate pinning si es necesario

### Configuración de Firma (release)

```kotlin
// app/build.gradle.kts
android {
    signingConfigs {
        create("release") {
            storeFile = file("keystore/my-app.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            // ... rest of config
        }
    }
}
```

---

## CI/CD Recomendado (GitHub Actions)

```yaml
# .github/workflows/android.yml
name: Android CI

on:
  push:
    branches: [ develop, master ]
  pull_request:
    branches: [ develop ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      
      - name: Cache Gradle
        uses: actions/cache@v3
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}
      
      - name: Run tests
        run: ./gradlew testDebugUnitTest
      
      - name: Run lint
        run: ./gradlew lint
      
      - name: Build debug APK
        run: ./gradlew assembleDebug
      
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

---

## Configuración de Ramas Protegidas (GitHub)

1. Ir a **Settings > Branches > Branch protection rules**
2. Crear regla para `develop` y `master`:

**Configuración recomendada:**
- ✅ Require pull request reviews (1 approve)
- ✅ Require status checks to pass before merging
- ✅ Require conversation resolution before merge
- ✅ Require signed commits (opcional)
- ✅ Include administrators
- ❌ Allow force pushes
- ❌ Allow deletions

---

## Versionado (SemVer)

```
MAJOR.MINOR.PATCH
  │     │     │
  │     │     └── Bug fixes
  │     └──────── Nuevas features (backwards compatible)
  └────────────── Breaking changes
```

**Ejemplos:**
- `1.0.0` → `1.0.1` (bug fix)
- `1.0.0` → `1.1.0` (nueva feature)
- `1.0.0` → `2.0.0` (breaking change)

---

## Checklist Pre-Producción

### Código
- [ ] Todas las features completas
- [ ] Sin bugs críticos o bloqueantes
- [ ] Pruebas unitarias > 70% coverage
- [ ] Pruebas de integración pasan
- [ ] Análisis estático sin errores críticos
- [ ] Performance aceptable

### Seguridad
- [ ] API keys fuera del código
- [ ] ProGuard habilitado
- [ ] Certificados configurados
- [ ] HTTPS obligatorio

### Build
- [ ] Build de release exitoso
- [ ] APK generado correctamente
- [ ] Tamaño de APK razonable (<50MB)

### Documentación
- [ ] CHANGELOG actualizado
- [ ] README actualizado
- [ ] Documentación de API

### Deployment
- [ ] Play Store准备好
- [ ] Screenshots actualizados
- [ ] Descripción de app actualizada

---

## Comandos Útiles

```bash
# Ver estado actual
git status

# Ver ramas locales
git branch

# Ver ramas remotas
git branch -r

# Eliminar rama local
git branch -d nombre-rama

# Eliminar rama remota
git push origin --delete nombre-rama

# Ver commits pendientes
git log origin/develop..HEAD

# Deshacer último commit (sin push)
git reset --soft HEAD~1

# Guardar cambios temporalmente
git stash
git stash pop

# Cherry-pick commit
git cherry-pick <commit-hash>
```

---

## Contacto y Recursos

- **Repositorio:** https://github.com/NanoLaravel/Rincon-verde2
- **Issues:** https://github.com/NanoLaravel/Rincon-verde2/issues
- **Documentación Android:** https://developer.android.com

---

*Documento creado: Febrero 2026*
*Última actualización: 2026-02-26*
