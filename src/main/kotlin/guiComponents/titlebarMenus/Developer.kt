package guiComponents.titlebarMenus

import java.awt.Font
import javax.swing.JMenu
import javax.swing.JMenuItem

class Developer : TitlebarMenu() {
    private val screenshot = JMenuItem("Screenshot").apply {
        this.addActionListener {
            Main.screenshot()
        }
    }
    private val reload = JMenuItem("Reload").apply {
        this.addActionListener {
            Main.reload()
        }
    }
    private val openConfig = JMenuItem("Open Config").apply {
        this.addActionListener {
            Main.config.configFile?.let {
                Main.setFile(it)
            }
        }
    }
    private val moveToNextTab = JMenuItem("Move to Next Tab").apply {
        this.addActionListener {
            Main.moveToNextTab()
        }
    }
    private val createNewTab = JMenuItem("Create New Tab").apply {
        this.addActionListener {
            Main.createNewTab()
        }
    }
    override val title = "Developer"
    override val components = listOf(screenshot, reload, openConfig, moveToNextTab, createNewTab)

    init { init() }

    override fun changeFontExtras(font: Font) {}
    override fun setFile(name: String, content: String) {}
}