package guiComponents.titlebarMenus

import java.awt.Font
import javax.swing.JMenuItem
import javax.swing.text.JTextComponent

class Edit(textArea: JTextComponent) : TitlebarMenu() {
    private val copy = JMenuItem("Copy").apply {
        this.addActionListener {
            textArea.copy()
        }
    }
    private val cut = JMenuItem("Cut").apply {
        this.addActionListener {
            textArea.cut()
        }
    }
    private val paste = JMenuItem("Paste").apply {
        this.addActionListener {
            textArea.paste()
        }
    }
    override val title = "Edit"
    override val components = listOf(
        copy, cut, paste
    )

    init { init() }

    override fun changeFontExtras(font: Font) {}

    override fun setFile(name: String, content: String) {}
}