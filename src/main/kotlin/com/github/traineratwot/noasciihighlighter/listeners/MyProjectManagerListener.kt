package com.github.traineratwot.noasciihighlighter.listeners

import com.github.traineratwot.noasciihighlighter.MyBundle
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent

internal class MyProjectManagerListener : BulkFileListener {
    override fun after(events: MutableList<out VFileEvent>) {
        println(MyBundle.message("applicationService"))
    }

}
