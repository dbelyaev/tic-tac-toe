package tictactoe

enum class State {
    ASK_INPUT_PLAYER,
    ANALYZE_STATE,
    IMPOSSIBLE,
    X_WINS,
    O_WINS,
    NOT_FINISHED,
    DRAW;
}