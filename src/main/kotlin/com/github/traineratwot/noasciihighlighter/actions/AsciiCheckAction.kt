package com.github.traineratwot.noasciihighlighter.actions

import com.github.traineratwot.noasciihighlighter.AsciiTranslit
import com.github.traineratwot.noasciihighlighter.MyBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
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
                    arrayOf(Messages.getCancelButton(), MyBundle.message("fix")),
                    0,
                    Messages.getWarningIcon()
                )
                if (answer == 1) {
                    replaceText(e)
                }
            } else {
                Messages.showMessageDialog(text2, "Ascii", Messages.getInformationIcon())
            }
        }
    }

    private fun replaceText(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        val selectedText = editor!!.selectionModel.selectedText ?: return
        val replaced = AsciiTranslit(selectedText).toString()
        if (replaced != selectedText) {
            val primaryCaret: Caret = editor.caretModel.primaryCaret
            val start: Int = primaryCaret.selectionStart
            val end: Int = primaryCaret.selectionEnd
            val document: Document = editor.document
            val project: Project = e.getRequiredData(CommonDataKeys.PROJECT)

            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(start, end, replaced)
            }
            primaryCaret.removeSelection()
            Messages.showMessageDialog("Ok", "Ascii", Messages.getInformationIcon())
        } else {
            Messages.showMessageDialog("Nothing do", "Ascii", Messages.getErrorIcon())
        }
    }
}