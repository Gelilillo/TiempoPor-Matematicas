package com.wezen.matesportiempo.data

data class Module(
    val title: String,
    val description: String,
    val icon: String,
    val route: String

)

fun getModules(): List<Module> {
    return listOf(
        Module(
            title = "Sumas",
            description = "Aprende a sumar números",
            icon = "➕",
            route = "suma"
        ),
        Module(
            title = "Restas",
            description = "Practica las restas",
            icon = "➖",
            route = "resta"
        ),
        Module(
            title = "Multiplicaciones",
            description = "Multiplica como un experto",
            icon = "✖️",
            route = "multiplicacion"
        ),
        Module(
            title = "Divisiones",
            description = "Divide y aprende",
            icon = "➗",
            route = "division"
        ),
        Module(
            title = "Tablas de Multiplicar",
            description = "Memoriza las tablas",
            icon = "📋",
            route = "tablas"
        ),
        Module(
            title = "Comprensión",
            description = "Resuelve problemas",
            icon = "🧠",
            route = "comprension"
        ),
        Module(
            title = "Examen",
            description = "Pon a prueba tus conocimientos",
            icon = "📝",
            route = "examen"
        )
    )
}