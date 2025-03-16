## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


## Project structure

```bash
MobilityX/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/movilidadsostenible/
│   │   │   │   ├── Main.java                   # Punto de entrada de la aplicación
│   │   │   │   ├── models/
│   │   │   │   │   ├── Usuario.java            # Clase que representa a un usuario
│   │   │   │   │   ├── Vehiculo.java           # Clase base para los vehículos
│   │   │   │   │   ├── Bicicleta.java          # Clase específica para bicicletas
│   │   │   │   │   ├── Patineta.java           # Clase específica para patinetas
│   │   │   │   │   ├── Estacion.java           # Clase que representa una estación de vehículos
│   │   │   │   ├── services/
│   │   │   │   │   ├── UsuarioService.java     # Lógica para la gestión de usuarios
│   │   │   │   │   ├── VehiculoService.java    # Lógica para gestionar vehículos
│   │   │   │   │   ├── EstacionService.java    # Lógica para gestionar estaciones
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Logger.java             # Clase para manejo de logs
│   │   │   │   │   ├── Config.java             # Configuración de la aplicación
│   │   ├── resources/
│   │   │   ├── application.properties          # Configuraciones de la aplicación
│   ├── test/
│   │   ├── java/
│   │   │   ├── com/movilidadsostenible/
│   │   │   │   ├── UsuarioTest.java            # Pruebas para Usuario
│   │   │   │   ├── VehiculoTest.java           # Pruebas para Vehiculo
│   │   │   │   ├── EstacionTest.java           # Pruebas para Estacion
│── pom.xml                                      # Configuración de Maven (si usas Maven)
│── build.gradle                                 # Configuración de Gradle (si usas Gradle)
│── README.md                                    # Documentación del proyecto
```