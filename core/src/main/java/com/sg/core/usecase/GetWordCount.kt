package com.sg.core.usecase

import com.sg.core.data.Note

class GetWordCount {

    operator fun invoke(note: Note): Int {
        var wordCount = 0
        wordCount += getCount(note.title)
        wordCount += getCount(note.content)
        return wordCount
    }

    private fun getCount(str: String) =
            str.split(" ","\n")
                    .filter {
                        it.contains(Regex(".*[a-zA-Z].*"))
                    }
                    .count()

}