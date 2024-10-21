package guiComponents.titlebarMenus

import Themes
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.Font
import javax.swing.JMenu
import javax.swing.JMenuItem

class View(val scrollPane: RTextScrollPane) : TitlebarMenu() {
    private val themes = JMenu("Themes").apply {
        for (theme in Themes.get) {
            this.add(JMenuItem(theme.key)).apply {
                addActionListener {
                    println("clicked ${theme.key}")
                    Themes.enable(theme.key, theme.value)
                }
            }
        }
    }
    private val sidebarToggler = JMenuItem("Toggle Sidebar").apply {
        addActionListener {
            with(Main.config.sidebarEnabled) {
                Main.config.sidebarEnabled = !this
                scrollPane.lineNumbersEnabled = !this
                scrollPane.isFoldIndicatorEnabled = !this
            }
        }
    }
    override val title = "View"
    override val components = listOf(
        themes, sidebarToggler
    )

    init { init() }

    override fun changeFontExtras(font: Font) {}

    override fun setFile(name: String, content: String) {}
}