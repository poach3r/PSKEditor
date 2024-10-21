package guiComponents.titlebarMenus

import com.google.gson.Gson
import java.awt.Font
import javax.swing.JMenuItem
import kotlin.system.exitProcess

class Files : TitlebarMenu() {
    private val open = JMenuItem("Open").apply {
        addActionListener {
            Main.openFile()
        }
    }
    private val save = JMenuItem("Save").apply {
        addActionListener {
            Main.saveFile()
        }
    }
    private val saveSettings = JMenuItem("Save Settings").apply {
        addActionListener {
            println("Saving config")
            Main.config.configFile?.writeText(Gson().toJson(Main.config))
        }
    }
    private val exit = JMenuItem("Exit").apply {
        addActionListener {
            exitProcess(0)
        }
    }
    override val title = "Files"
    override val components = listOf(
        open, save, saveSettings, exit
    )

    init { init() }

    override fun changeFontExtras(font: Font) {}

    override fun setFile(name: String, content: String) {}
}