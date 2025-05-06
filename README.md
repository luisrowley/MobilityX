# MobilityX - Plataforma de Movilidad Urbana Sostenible

## Descripción del Proyecto

MobilityX es una plataforma de gestión de movilidad urbana sostenible que permite administrar diferentes tipos de vehículos (bicicletas, patinetes, motos) distribuidos en bases por toda la ciudad. El sistema facilita el alquiler de vehículos, la gestión de usuarios, el mantenimiento de la flota y la obtención de estadísticas de uso.

## Requisitos Previos

- Java JDK 11 o superior
- Maven 3.6 o superior
- Git (opcional, para control de versiones)

## Instalación y Configuración

### Clonar el Repositorio

```bash
git clone https://github.com/luisrowley/MobilityX.git
cd MobilityX
```

### Compilar el Proyecto

```bash
mvn clean compile
```

## Ejecución del Proyecto

### Ejecutar la Aplicación

```bash
mvn exec:java -Dexec.mainClass="com.mobilityX.CLI"
```

### Generar JAR Ejecutable

```bash
mvn clean package
```

Y luego ejecutar:

```bash
java -jar target/mobility-x-1.0-SNAPSHOT.jar
```

## Ejecución de Pruebas

```bash
mvn test
```

## Estructura del Proyecto

```
MobilityX/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/mobilityX/
│   │   │   │   ├── CLI.java                    # Interfaz de línea de comandos
│   │   │   │   ├── models/
│   │   │   │   │   ├── user/
│   │   │   │   │   │   ├── Usuario.java        # Clase base para usuarios
│   │   │   │   │   │   ├── UsuarioPremium.java # Clase para usuarios premium
│   │   │   │   │   ├── vehicle/
│   │   │   │   │   │   ├── Base.java           # Clase para bases de vehículos
│   │   │   │   │   │   ├── Vehiculo.java       # Clase base para vehículos
│   │   │   │   │   │   ├── Bicicleta.java      # Clase para bicicletas
│   │   │   │   │   │   ├── Patinete.java       # Clase para patinetes
│   │   │   │   │   │   ├── Moto.java           # Clase para motos
│   │   │   │   │   ├── worker/
│   │   │   │   │   │   ├── Trabajador.java     # Clase base para trabajadores
│   │   │   │   │   │   ├── Administrador.java  # Clase para administradores
│   │   │   │   ├── services/
│   │   │   │   │   ├── AlquilerService.java    # Servicio de alquiler de vehículos
│   │   │   │   │   ├── AdminService.java       # Servicio de administración
│   │   │   │   │   ├── MantenimientoService.java # Servicio de mantenimiento
│   │   │   │   │   ├── StatsService.java       # Servicio de estadísticas
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Logger.java             # Utilidad para registro de logs
│   │   │   │   │   ├── Config.java             # Configuración de la aplicación
│   ├── test/
│   │   ├── java/
│   │   │   ├── com/mobilityX/
│   │   │   │   ├── services/                   # Tests de servicios
│   │   │   │   ├── models/                     # Tests de modelos
├── pom.xml                                     # Configuración de Maven
├── README.md                                   # Documentación del proyecto
```

## Funcionalidades Principales

1. **Gestión de Vehículos**
   - Registro y seguimiento de diferentes tipos de vehículos
   - Asignación de vehículos a bases
   - Monitoreo del estado y batería de los vehículos

2. **Gestión de Usuarios**
   - Registro de usuarios
   - Usuarios premium con beneficios especiales
   - Historial de uso y gastos

3. **Sistema de Alquiler**
   - Búsqueda de vehículos cercanos
   - Alquiler y devolución de vehículos
   - Cálculo de tarifas

4. **Mantenimiento**
   - Registro de incidencias
   - Asignación de trabajadores a tareas de mantenimiento
   - Seguimiento de intervenciones

5. **Estadísticas**
   - Bases más demandadas
   - Trabajadores más activos
   - Vehículos más utilizados
   - Usuarios con mayor gasto

## Uso de la Interfaz de Línea de Comandos

Al ejecutar la aplicación, se mostrará un menú principal con las siguientes opciones:

1. **Consultas cercanas**: Permite buscar bases y vehículos cercanos a una ubicación
2. **Listados / estadísticas**: Muestra diferentes estadísticas del sistema
   - Bases por demanda
   - Trabajadores por intervenciones
   - Vehículos por uso (por tipo)
   - Usuarios por gasto total

## Contribución

Para contribuir al proyecto:

1. Haz un fork del repositorio
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Haz commit de tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo LICENSE para más detalles.

## Contacto

Luis Rowley - luis@mobilityX.com

Enlace del proyecto: [https://github.com/luisrowley/MobilityX](https://github.com/luisrowley/MobilityX)