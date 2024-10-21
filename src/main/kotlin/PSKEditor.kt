import com.google.gson.Gson
import guiComponents.Frame
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.time.LocalDateTime
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.UIManager

fun main(args: Array<String>) {
    println("Starting")
    if (args.isEmpty()) {
        Main
    } else { // Load file from argument
        with(File(args[0])) {
            if (!this.isFile)
                Main

            Main.setFile(this)
        }
    }
}

object Main {
    val config = loadConfig(System.getenv("PSKE_CONFIG"), false)
    private val fileChooser = JFileChooser().apply { name = "PSKEditor - Files" }
    private val frame = Frame()

    init {
        Themes.enable(config.theme, Themes.get[config.theme]!!)
        frame.isVisible = true
    }

    fun updateTheme(name: String) {
        config.theme = name
        setFont(config.fontFamily, config.editorFontFamily, config.fontSize)
        frame.updateTheme()
        fileChooser.updateUI()
    }

    fun setFont(fontFamily: String, editorFontFamily: String, fontSize: Int) {
        config.fontFamily = fontFamily
        config.fontSize = fontSize
        frame.changeFont(Font(fontFamily, Font.PLAIN, fontSize), Font(editorFontFamily, Font.PLAIN, fontSize))
        UIManager.put("defaultFont", Font(fontFamily, Font.PLAIN, fontSize))
    }

    fun saveFile() {
        with(frame.getFile()) {
            if (this == null) {
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    println("Saving ${fileChooser.selectedFile}")
                    fileChooser.selectedFile.writeText(frame.getText())
                    setFile(fileChooser.selectedFile)
                }

                return
            }

            if (!this.canWrite()) {
                error("Error: Cannot write to ${this.path}")
                return
            }

            println("Saving $this")
            this.writeText(frame.getText())
        }
    }

    fun openFile() {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            println("Opening ${fileChooser.selectedFile.path}")
            setFile(fileChooser.selectedFile.absoluteFile)
        }
    }

    fun setFile(newFile: File) {
        with(newFile) {
            println("Setting file to " + this.path)
            if (!canRead()) {
                error("Error: Cannot read file '${this.path}'.")
                return
            }

            if (!isFile) {
                error("Error: '${this.path}' is not a file.")
                return
            }

            frame.setFile(this.name, readFile(this), this)
        }
    }

    // Screenshots all themes
//    fun screenshot() {
//        println("Screenshotting")
//        frame.setSize(768, 512)
//        for (theme in Themes.get) {
//            Themes.enable(theme.key, theme.value)
//            val image = BufferedImage(frame.width, frame.height, BufferedImage.TYPE_INT_RGB)
//            frame.paint(image.graphics)
//            ImageIO.write(image, "png", File("screenshots/${theme.key}-screenshot.png"))
//            Thread.sleep(100)
//        }
//    }

    // Screenshot current window
    fun screenshot() {
        println("Screenshotting")
        val image = BufferedImage(frame.width, frame.height, BufferedImage.TYPE_INT_RGB)
        frame.paint(image.graphics)
        ImageIO.write(image, "png", File("screenshots/${LocalDateTime.now()}.png"))
    }

    fun reload() {
        frame.getFile()?.let {
            setFile(it)
        }
    }

    fun moveToNextTab() {
        frame.moveToNextTab()
    }

    fun createNewTab() {
        frame.createNewTab()
    }

    fun moveToTab(index: Int) {
        frame.moveToTab(index)
    }

    fun closeActiveTab() {
        frame.closeActiveTab()
    }

    fun enterNormalMode() {
        config.mode = Modes.NORMAL
        frame.enterNormalMode()
    }

    fun enterInsertMode() {
        config.mode = Modes.INSERT
        frame.enterInsertMode()
    }

    fun moveCaretHorizontal(amount: Int) {
        frame.moveCaretHorizontal(amount)
    }

    fun moveCaretVertical(amount: Int) {
        frame.moveCaretVertical(amount)
    }

    private fun readFile(file: File): String {
        var content: String = ""
        file.forEachLine { content += it + "\n" }
        return content
    }

    private fun error(message: String) {
        println(message)
        JOptionPane.showMessageDialog(null, message)
    }

    private fun loadConfig(file: String?, recursive: Boolean): Config {
        file?.let {
            val file = File(it)

            if (file.isFile)
                return Config.createFromConfig(
                    Gson().fromJson<Config>(readFile(file), Config.create(file).javaClass),
                    file
                )

            if (recursive)
                return Config.create(file)
        }

        return loadConfig(System.getenv("HOME") + "/.config/pske/config.json", true)
    }
}
