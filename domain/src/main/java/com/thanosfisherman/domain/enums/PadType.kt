package com.thanosfisherman.domain.enums

enum class PadType(val type: String) {

    DECIMAL("."),
    EQUALS("="),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    CLEAR("DEL"),
    CLEAR_ALL("AC"),
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("÷"),
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    MOD("mod"),
    POWER("^"),
    SQRT("√"),
    PI("Π"),
    E("e"),
    LOG("log"),
    LN("ln"),

    UNKNOWN("NaN");


    companion object {

        fun getPadType(type: String): PadType {
            val lookup = values().associateBy(PadType::type)
            return lookup[type] ?: UNKNOWN
        }
    }
}