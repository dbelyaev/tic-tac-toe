package tictactoe

class TicTacToe {
    private var field = Array(3) { Array(3) { '_' } }
    private var state = State.NOT_FINISHED
        set(value) {
            field = value
            when (value) {
                State.ASK_INPUT_PLAYER -> askInputPlayer(getPlayer())
                State.ANALYZE_STATE -> analyzeState()
                State.IMPOSSIBLE -> println("Impossible")
                State.NOT_FINISHED -> askInputPlayer(getPlayer())
                State.X_WINS -> println("X wins")
                State.O_WINS -> println("O wins")
                State.DRAW -> println("Draw")
            }
        }

    fun start() {
        println("Welcome to simple Tic-Tac-Toe!")
        println("Each player input coordinates until WIN or DRAW.")
        println()
        drawField()
        this.state = State.ASK_INPUT_PLAYER
    }

    private fun getPlayer(): Player {
        var countX = 0
        var countO = 0

        // count X and O to decide who's turn next
        for (i in 0..2) {
            for (j in 0..2) {
                when {
                    field[i][j] == 'X' -> countX++
                    field[i][j] == 'O' -> countO++
                }
            }
        }

        // decide next player
        return when {
            countX > countO -> Player.O
            else -> Player.X
        }
    }

    private fun askInputPlayer(player: Player) {
        println("Player ${player.char} enter coordinates (row then column, space separated:")
        while (true) {
            val input = readln().split(' ')
            when {
                !isValidInput(input) -> println("You should enter numbers!")
                input[0].toInt() !in 1..3 || input[1].toInt() !in 1..3 -> println("Coordinates should be from 1 to 3!")
                isOccupiedCell(input.first().toInt() - 1, input.last().toInt() - 1) -> println("This cell is occupied! Choose another one!")
                else -> {
                    field[input.first().toInt() - 1][input.last().toInt() - 1] = player.char
                    break
                }
            }
        }

        drawField()
        this.state = State.ANALYZE_STATE
    }

    private fun isValidInput(input: List<String>): Boolean {
        for (i in input) {
            if (i.toIntOrNull() == null) return false
        }

        return true
    }

    private fun isOccupiedCell(x: Int, y: Int): Boolean {
        val cell = field[x][y].toString()
        return cell.contains('X') || cell.contains('O')
    }

    private fun analyzeState() {
        val lines = mutableListOf<String>()
        lines.add(field[0][0].toString() + field[0][1].toString() + field[0][2].toString()) // horizontal
        lines.add(field[1][0].toString() + field[1][1].toString() + field[1][2].toString()) // horizontal
        lines.add(field[2][0].toString() + field[2][1].toString() + field[2][2].toString()) // horizontal
        lines.add(field[0][0].toString() + field[1][0].toString() + field[2][0].toString()) // vertical
        lines.add(field[0][1].toString() + field[1][1].toString() + field[2][1].toString()) // vertical
        lines.add(field[0][2].toString() + field[1][2].toString() + field[2][2].toString()) // vertical
        lines.add(field[0][0].toString() + field[1][1].toString() + field[2][2].toString()) // diagonal
        lines.add(field[0][2].toString() + field[1][1].toString() + field[2][0].toString()) // diagonal

        // check the current state
        state = when {
            lines.containsAll(listOf("XXX", "OOO")) -> State.IMPOSSIBLE
            lines.contains("XXX") -> State.X_WINS
            lines.contains("OOO") -> State.O_WINS
            hasEmptyCells() -> State.NOT_FINISHED
            else -> State.DRAW
        }
    }

    private fun hasEmptyCells(): Boolean {
        for (element in field) {
            if (element.contains('_')) return true
        }

        return false
    }

    private fun drawField() {
        println("  | 1 2 3  ") // print header
        println("-----------")
        for (i in 0..2) {
            print("${i + 1} | ")
            for (j in 0..2) {
                print("${field[i][j]} ")
            }
            println("|")
        }
        println("-----------")
    }
}