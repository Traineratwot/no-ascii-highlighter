package com.github.traineratwot.noasciihighlighter.actions

import com.github.traineratwot.noasciihighlighter.AsciiTranslit
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages
import org.apache.commons.lang.StringEscapeUtils.escapeHtml


public class AsciiCheckAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val selectedText = editor!!.selectionModel.selectedText
        if (selectedText != null) {
            var found = false
            val txt = StringBuilder()
            val lines = selectedText.split("\n")
            for (line in lines) {
                val chars = line.toCharArray()
                for (char in chars) {
                    val check = char.code
                    print("$char ")
                    println(check)
                    if (check <= 127) {
                        txt.append(char.toString())
                    } else {
                        found = true
                        txt.append("""|[${char}]|""")
                    }
                }
                txt.append("\n")
            }
            //русский
            val text = escapeHtml(txt.toString());
            val text2 = text.replace("""\|\[(\&.+?)\]\|""".toRegex(), """<span style="color:red;">$1</span>""")
            if (found) {
                val answer: Int = Messages.showDialog(
                    text2,
                    "Ascii",
                    arrayOf(Messages.getCancelButton(),Messages.getYesButton()),
                    0,
                    Messages.getWarningIcon()
                )
                if(answer===1){
                    replaceText(e)
                }
            } else {
                Messages.showMessageDialog(text2, "Ascii", Messages.getInformationIcon())
            }


        }


    }
    private fun replaceText(e: AnActionEvent){
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val selectedText = editor!!.selectionModel.selectedText ?: return
        println(AsciiTranslit(selectedText).toString())
    }


}