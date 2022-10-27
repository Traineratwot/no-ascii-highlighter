package com.github.traineratwot.noasciihighlighter

class AsciiTranslit {
    private val text: String

    constructor(text: String) {
        this.text = text
    }

    public fun getLetters(): MutableMap<String, String> {
        val letters: MutableMap<String, String> = HashMap()
        letters["А"] = "A"
        letters["B"] = "В"
        letters["Е"] = "E"
        letters["Ё"] = "E"
        letters["М"] = "M"
        letters["О"] = "O"
        letters["Р"] = "P"
        letters["С"] = "С"
        letters["К"] = "К"
        letters["Т"] = "T"
        letters["а"] = "a"
        letters["в"] = "в"
        letters["е"] = "e"
        letters["ё"] = "e"
        letters["к"] = "k"
        letters["м"] = "m"
        letters["о"] = "o"
        letters["р"] = "p"
        letters["с"] = "с"
        return letters
    }

    public fun toTranslit(text: String): String {
        val letters = getLetters()
        val sb = StringBuilder(text.length)
        for (i in text.indices) {
            val l = text.substring(i, i + 1)
            if (letters.containsKey(l)) {
                sb.append(letters[l])
            } else {
                sb.append(l)
            }
        }
        return sb.toString()
    }

    override fun toString(): String {
        return toTranslit(text)
    }
}