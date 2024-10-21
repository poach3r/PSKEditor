package guiComponents

import com.formdev.flatlaf.FlatLaf
import java.awt.Font
import java.io.File
import javax.swing.JFrame

class Frame() : JFrame() {
    private val panel = Panel()
    private val titleBar = Titlebar(panel)

    init {
        title = "PSKEdit"
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane = panel
        jMenuBar = titleBar
        pack()
    }

    fun changeFont(font: Font, editorFont: Font) {
        titleBar.changeFont(font)
        panel.changeFont(font, editorFont)
    }

    fun updateTheme() {
        FlatLaf.uninstallBorder(this.rootPane)
        panel.updateTheme()
        titleBar.border = null
    }

    fun setFile(name: String, content: String, file: File) {
        titleBar.setFile(name, content)
        panel.setFile(content, file)
    }

    fun getText(): String {
        return panel.textArea.text
    }

    fun moveToNextTab() {
        panel.moveToNextTab()
        reload()
    }

    fun createNewTab() {
        panel.createNewTab()
        reload()
    }

    fun moveToTab(index: Int) {
        if(!panel.hasTab(index))
            return

        panel.moveToTab(index, false)
        reload()
    }

    fun getFile(): File? {
        return panel.activeTab.file
    }

    fun closeActiveTab() {
        panel.closeActiveTab()
    }

    fun enterNormalMode() {
        panel.textArea.isEditable = false
    }

    fun enterInsertMode() {
        panel.textArea.isEditable = true
    }

    fun moveCaretHorizontal(amount: Int) {
        panel.textArea.caretPosition += amount
    }

    fun moveCaretVertical(amount: Int) {
        with(panel.textArea) {
            if (amount < 0)
                this.caretPosition -= this.caretOffsetFromLineStart
        }
    }

    private fun reload() {
        titleBar.changeTab(panel.fileName)
        revalidate()
        repaint()
    }
}
