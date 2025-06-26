# Planificador Horarios

Aplicación de consola en **Java clásico** (MVC + Ant) para gestionar **cursos** y **sesiones** con roles de usuario y persistencia en disco.

---

## Descripción

**PlanificadorHorarios** permite a un **Administrador**, **Profesor** o **Estudiante**:

- **Administrador**  
  - Crear y clonar plantillas de curso (con sufijo de sección).  
  - Listar todos los cursos.  
  - Agendar sesiones (verifica conflictos de horario).  
  - Listar todas las sesiones.  
  - Gestionar usuarios (crear nuevos usuarios con contraseña y rol).  
- **Profesor**  
  - Listar cursos disponibles.  
  - Agendar sesiones.  
  - Listar sus propias sesiones.  
- **Estudiante**  
  - Listar cursos.  
  - Listar sesiones.

La persistencia se realiza en archivos de texto (`usuarios.txt`, `cursos.txt`, `sesiones.txt`) para que ningún dato se pierda al cerrar la aplicación.

---

## Patrones de diseño aplicados

| Categoría         | Patrón         | Uso                                                           |
|-------------------|----------------|---------------------------------------------------------------|
| **Creacional**    | Prototype      | Clonación de plantillas de `Curso` con sufijo de sección.     |
| **Estructural**   | Bridge         | Interfaz `Storage` + implementación `JdbcStorage` (TXT).      |
| **Comportamiento**| Iterator       | `ColeccionSesiones` + `SesionIterador` para recorrer listas.  |
| **Libre elección**| Observer       | `Observador` + `NotificadorConsola` para notificar conflictos.|


---
## Requisitos

- Java 17 (o superior) instalado.  
- (Opcional) Apache Ant instalado para simplificar compilación.  
- Un IDE o editor a su elección:
  - **NetBeans** (Ant integrado)  
  - **Visual Studio Code** con Java Extension Pack  
  - **IntelliJ IDEA**, **Eclipse**, o cualquier entorno que soporte proyectos Java.

## Compilación y ejecución

### Con Ant (si está instalado)

```bash
cd PlanificadorHorarios
ant compile
ant run
````

Para generar JAR:

```bash
ant jar
java -jar dist/PlanificadorHorarios.jar
```

### Sin Ant, solo con JDK

```bash
cd PlanificadorHorarios
mkdir -p build
# Linux/Mac:
javac -d build $(find src -name "*.java")
java -cp build view.Aplicacion

# Windows PowerShell:
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { javac -d build $_.FullName }
java -cp build view.Aplicacion
```

### Importar en un IDE

1. **NetBeans**

   * File → Open Project… → elige la carpeta raíz.
   * Run Project.

2. **VS Code**

   * File → Open Folder… → selecciona `PlanificadorHorarios`.
   * Usa las tareas (`Tasks: Run Task…`) para compilar o configura `launch.json` para depurar.

3. **IntelliJ / Eclipse**

   * Import Project → Existing Sources → apunta a la carpeta y configura `src` como Source Folder.

## Credenciales por defecto

* **Usuario**: `admin`
* **Password**: `admin`
* **Rol**: `ADMINISTRADOR`

> El Administrador puede crear nuevos usuarios (y asignarles rol y contraseña) desde la opción **6) Gestionar usuarios**.

## Flujo de uso

1. Se limpia la pantalla y pide **login** (usuario + password).
2. Según tu rol, verás un menú con las opciones permitidas.
3. Tras cada acción, pulsa **ENTER** para volver al menú.
4. La opción **0) Cerrar sesión** regresa al login (no cierra la aplicación).

---

© 2025 — Proyecto académico de Patrones de Diseño.

