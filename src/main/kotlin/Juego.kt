class Juego {
    val tablero: Array<IntArray> = Array(3) { IntArray(3) }
    var jugadorActual: Int = 1

    init {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                tablero[i][j] = 0
            }
        }
    }

    fun imprimirTablero() {
        clearConsole(25)
        println("-------------")
        for (i in 0 until 3) {
            println("| " + tablero[i].joinToString(" | ") { if (it == 0) " " else if (it == 1) "X" else "O" } + " |")
            println("-------------")
        }
    }

    fun clearConsole(num: Int) {
        repeat(num) { println() }
    }

    fun comprobarGanador(): Int {
        for (i in 0 until 3) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != 0) {
                return tablero[i][0]
            }
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] != 0) {
                return tablero[0][i]
            }
        }
        if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] != 0) {
            return tablero[0][0]
        }
        if (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] != 0) {
            return tablero[0][2]
        }
        return 0
    }

    fun tableroCompleto(): Boolean {
        return tablero.all { row -> row.all { it != 0 } }
    }

    fun colocarFicha(row: Int, col: Int) {
        if (row in 0 until 3 && col in 0 until 3 && tablero[row][col] == 0) {
            tablero[row][col] = jugadorActual
        } else {
            println("**Error** Movimiento inválido. Inténtalo de nuevo.")
        }
    }

    fun jugar() {
        var winner = 0

        while (winner == 0) {
            imprimirTablero()
            println("Turno del Jugador $jugadorActual")
            val (row, col) = obtenerMovJugador()

            colocarFicha(row, col)

            winner = comprobarGanador()
            if (winner != 0) {
                imprimirTablero()
                println("¡El jugador $winner ha ganado!")
            } else if (tableroCompleto()) {
                imprimirTablero()
                println("El juego ha terminado en empate.")
            }

            jugadorActual = if (jugadorActual == 1) 2 else 1
        }
    }

    fun obtenerMovJugador(): Pair<Int, Int> {
        println("Elige la fila (1, 2, 3) o pulsa ENTER para salir: ")
        val rowInput = readLine()

        if (rowInput.isNullOrBlank()) {
            println("Juego finalizado por el jugador $jugadorActual.")
            System.exit(0)
        }

        try {
            val row = rowInput!!.toInt() - 1

            print("Elige la columna (1, 2, 3): ")
            val col = readLine()!!.toInt() - 1

            return Pair(row, col)
        } catch (e: NumberFormatException) {
            println("**Error** Número inválido. Inténtalo de nuevo.")
            return obtenerMovJugador()
        }
    }
}