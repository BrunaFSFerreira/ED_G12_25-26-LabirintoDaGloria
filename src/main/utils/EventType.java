package main.utils;

/**
 * Enumeration representing different types of events in the game.
 */
public enum EventType {
    /** No event */
    NONE,
    /** Player wins an extra move */
    EXTRA_MOVE,
    /** Swap positions with another player */
    POSITION_SWAP,
    /** Move back a certain number of spaces */
    MOVE_BACK,
    /** Block the next player's turn */
    TURN_BLOCK,
    /** General swap event */
    GENERAL_SWAP
}

