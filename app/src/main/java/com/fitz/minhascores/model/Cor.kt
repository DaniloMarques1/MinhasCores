package com.fitz.minhascores.model

import java.io.Serializable

data class Cor(
    var name: String,
    var code: Int
): Serializable {
    var id: Int = -1

    constructor(id: Int, name: String, code: Int): this(name, code) {
        this.id = id
    }

    override fun toString(): String {
        return "$id - $name - $code - ${toHex()}"
    }

    fun toHex(): String {
        return String.format("#%06X", (0xFFFFFF and this.code))
    }

}