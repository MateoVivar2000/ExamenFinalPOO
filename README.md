Buscaminas en Consola (Minesweeper CLI)

Proyecto final para la asignatura de Programación Orientada a Objetos (POO). Implementación completa del clásico juego Buscaminas en una interfaz de consola. El desarrollo se enfocó en la aplicación estricta de principios de **POO**, **Patrón MVC** y técnicas de **Código Limpio** (DRY, KISS).

**Características y Principios Técnicos**

**Arquitectura MVC (Modelo-Vista-Controlador):** Implementación estricta para desacoplar la lógica de negocio (`modelo`) de la presentación (`vista`) y el flujo de la aplicación (`controlador`).
**Algoritmos de Juego:** Implementación del algoritmo de expansión recursiva de casillas vacías (Flood Fill) dentro de la clase `Tablero`.
**Manejo de Errores Avanzado:** Utilización de excepciones personalizadas para manejar errores de la lógica interna del juego de forma clara y semántica, separando esta responsabilidad del `Controlador`.
**Código Limpio (KISS/SRP):** Las clases cumplen el Principio de Responsabilidad Única (SRP);

**Tecnologías y Requisitos**

**Lenguaje:** **Java SE 21** (o superior).
**Entorno de Desarrollo:** Cualquier IDE moderno compatible con Java (Eclipse, IntelliJ IDEA, VS Code).
**Testing:** **JUnit 5** (para pruebas unitarias).

**Guía para Duplicar y Configurar el Proyecto**

Esta sección detalla los pasos para obtener el código fuente y configurarlo para su ejecución y desarrollo en un IDE.

 **1. Obtener el Código Fuente**

El método recomendado para duplicar el proyecto es clonar el repositorio de GitHub:

```bash
git clone [https://github.com/MateoVivar2000/UNIDAD](https://github.com/MateoVivar2000/UNIDAD) 4.git
cd ExamenPractico

**2. Configuración en el IDE (Eclipse, IntelliJ, etc.)**
El proyecto utiliza una estructura de paquetes estándar, que facilita su importación:

Abrir Proyecto: En tu IDE, selecciona "Importar Proyecto Existente" o "Abrir" y navega hasta el directorio raíz del proyecto.

Configurar Build Path/Dependencias: Asegúrate de que el proyecto utilice Java SE 21 como su JRE/SDK.

Configurar Paquetes Fuente: Verifica que los directorios src/controlador, src/modelo, src/vista, y src/main estén configurados como Carpetas de Código Fuente (Source Folders) de Java.

 Estructura de Paquetes y ClasesEl código está dividido en cuatro paquetes principales, siguiendo la convención de paquetes de Java y el patrón MVC:PaqueteClases ClaveResponsabilidad PrimariamainMain.javaInicializa el sistema, actúa como el bootstrapper del MVC.controladorControladorJuego.javaGestiona el flujo del juego, procesa la entrada de comandos y maneja excepciones del sistema.modeloJuegoBuscaminas.java, Tablero.java, Casilla.javaLógica de negocio, estado del tablero, reglas de juego, y serialización.vistaVistaConsola.javaEntrada/Salida de datos, impresión del tablero y formateo de mensajes.

 EjecuciónUna vez configurado, ejecuta la clase principal: main.Main.java.🎮 Modo de UsoUna vez que inicies el juego, se te pedirá ingresar comandos en el formato [ACCIÓN] [COORDENADA].AcciónFormato de EntradaDescripciónDescubrirD A5Revela el contenido de la casilla A5. Si es mina, el juego termina.Marcar/BanderaM B3Coloca o retira una bandera en la casilla B3.Coordenadas:Las filas van de A a J y las columnas van de 1 a 10.
