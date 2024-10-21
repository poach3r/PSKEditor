package guiComponents.titlebarMenus

import java.awt.Font
import javax.swing.JComponent
import javax.swing.JMenu

abstract class TitlebarMenu : JMenu() {
    abstract val title: String
    abstract val components: List<JComponent>

    // If we use an initializer we get a null pointer exception so we need to use this hacky solution
    fun init() {
        text = title
        font = Font(Main.config.fontFamily, Font.PLAIN, Main.config.fontSize)
        components.forEach {
            add(it)
        }
    }

    fun changeFont(font: Font) {
        this.font = font
        components.forEach {
            it.font = font
        }
        changeFontExtras(font)
    }

    abstract fun changeFontExtras(font: Font)

    abstract fun setFile(name: String, content: String)
}