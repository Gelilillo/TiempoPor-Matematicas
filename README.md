📱 MatesPorTiempo

MatesPorTiempo es una aplicación educativa para Android diseñada para reforzar los conocimientos de matemáticas en niños de primaria (8-9 años).
Combina aprendizaje, estadísticas y gamificación para motivar a los estudiantes mientras practican operaciones aritméticas y miden su progreso a lo largo del tiempo.

🎯 Objetivo General

Desarrollar una aplicación móvil para Android que fomente el aprendizaje y la mejora de las habilidades matemáticas en los niños de primaria, integrando herramientas de seguimiento (estadísticas) y gamificación (acumulación de tiempo) para aumentar la motivación y el compromiso.

🧩 Objetivos Específicos

1. Implementar los módulos de práctica para las cinco operaciones aritméticas fundamentales (Suma, Resta, Multiplicación, División y tablas de multiplicar).
2. Desarrollar el módulo de "Exámenes" que permita la realización de pruebas variadas con ejercicios aleatorios y que genere resultados inmediatos y consultables.
3. Diseñar e implementar el Sistema de Registro de Errores que capture los fallos específicos del usuario (por operación y tipo), con el objetivo de generar un plan de refuerzo focalizado y estadísticas de desempeño.
4. Integrar la API de Gemini (de terceros) en el módulo de "Comprensión" para la generación dinámica de problemas matemáticos breves que ayuden a diferenciar las operaciones. Justificación Uso de Terceros: En este caso se utilizara esta API de terceros para la generación dinámica de problemas o contenido contextual para el modulo de Compresión disponiendo de esta forma de un infinito numero de ejemplos para detectar la operación necesaria. Solicitando a través de un promt previamente trabajado una respuesta en formato Json.
5. Implementar el modelo de datos (utilizando tres tablas relacionadas: Usuarios, Errores y Exámenes) en SQLite para la persistencia local de la configuración, las estadísticas y los resultados.
6. Desarrollar la funcionalidad de Acumulación de Tiempo, donde el tiempo dedicado a ejercicios o exámenes se sume como una métrica de recompensa canjeable por los padres.
7. Crear una interfaz de usuario (UI) intuitiva y atractiva, optimizada para la usabilidad por parte del público infantil (8-9 años) y una sección separada para la visualización de estadísticas por parte de los padres.

🧠 Tecnologías Utilizadas
Componente	Descripción
Lenguaje	Kotlin
Framework UI	Jetpack Compose
Base de datos local	SQLite
Inteligencia Artificial	API de Gemini (Google)
Arquitectura	MVVM (Model-View-ViewModel)
Entorno de desarrollo	Android Studio
🚀 Instalación y Ejecución

📊 Estructura de la Base de Datos (SQLite)
Tabla	Campos principales	Descripción
Usuarios	id, nombre, tiempo_total	Almacena la información básica del usuario.
Errores	id, usuario_id, operacion, tipo_error, fecha	Guarda los errores detectados para generar estadísticas de refuerzo.
Exámenes	id, usuario_id, aciertos, errores, fecha	Contiene los resultados de los exámenes realizados.
🧮 Funcionalidades Principales

🔢 Práctica guiada de operaciones matemáticas básicas.
🧠 Generación dinámica de ejercicios mediante IA (Gemini).
📈 Registro automático de errores y progreso.
🕒 Acumulación de tiempo de estudio como elemento de gamificación.
👨‍👩‍👧 Panel de estadísticas para los padres.
🔐 Modo de configuración protegido por contraseña para evitar cambios no autorizados.
🖼️ Capturas de Pantalla

Pendiente////

👩‍🏫 Autor

MatesPorTiempo ha sido desarrollado como parte del Trabajo de Fin de Grado de Desarrollo de Aplicaciones Multiplataforma (DAM), con el objetivo de fomentar el aprendizaje matemático mediante el uso de tecnología, inteligencia artificial y diseño centrado en el usuario.
