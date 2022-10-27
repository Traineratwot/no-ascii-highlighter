package com.github.traineratwot.noasciihighlighter

import java.text.Normalizer

class AsciiTranslit {
    private val text: String

    constructor(text: String) {
        this.text = text
    }

    public fun getLetters(): MutableMap<String, String> {
        val letters: MutableMap<String, String> = HashMap()
        letters["А"] = "A"
        letters["В"] = "B"
        letters["Е"] = "E"
        letters["Ё"] = "E"
        letters["М"] = "M"
        letters["О"] = "O"
        letters["Р"] = "P"
        letters["С"] = "C"
        letters["К"] = "K"
        letters["Т"] = "T"
        letters["Н"] = "H"
        letters["а"] = "a"
        letters["е"] = "e"
        letters["ё"] = "e"
        letters["к"] = "k"
        letters["м"] = "m"
        letters["о"] = "o"
        letters["р"] = "p"
        letters["с"] = "c"
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
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(text, Normalizer.Form.NFD)
        val temp2 = regex.replace(temp, "")
        return toTranslit(temp2)
    }
}