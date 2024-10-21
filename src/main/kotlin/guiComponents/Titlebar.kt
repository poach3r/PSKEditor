package guiComponents

import guiComponents.titlebarMenus.Developer
import guiComponents.titlebarMenus.Edit
import guiComponents.titlebarMenus.Files
import guiComponents.titlebarMenus.TitlebarMenu
import guiComponents.titlebarMenus.View
import java.awt.Font
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JMenuBar

class Titlebar(val panel: Panel) : JMenuBar() {
    private var fileName = JLabel("New File")
    private val components: ArrayList<TitlebarMenu> = ArrayList<TitlebarMenu>().apply {
        add(Files())
        add(Edit(panel.textArea))
        add(View(panel.scrollPane))
        add(Developer())
    }

    init {
        components.forEach { this.add(it) }
        border = null
        add(Box.createGlue())
        add(fileName)
        add(Box.createHorizontalStrut(8))
    }

    fun changeFont(font: Font) {
        fileName.font = font
        components.forEach { it.changeFont(font) }
    }

    fun setFile(name: String, content: String) {
        components.forEach { it.setFile(name, content) }
        fileName.text = name
    }

    fun changeTab(name: String) {
        fileName.text = name
    }
}