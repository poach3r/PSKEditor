import java.awt.Font
import java.io.File

data class Config(
    var theme: String,
    var lineHighlighting: Boolean,
    var fontFamily: String,
    var fontSize: Int,
    var editorFontFamily: String,
    var sidebarEnabled: Boolean,
    @Transient var configFile: File?,
    var developerModeEnabled: Boolean,
    @Transient var mode: Int,
) {
    companion object {
        fun create(configFile: File): Config {
            return Config("Light", true, Font.SANS_SERIF, 12, Font.MONOSPACED, true, configFile, false, Modes.INSERT)
        }

        fun createFromConfig(config: Config, configFile: File): Config {
            return Config(
                config.theme,
                config.lineHighlighting,
                config.fontFamily,
                config.fontSize,
                config.editorFontFamily,
                config.sidebarEnabled,
                configFile,
                config.developerModeEnabled,
                Modes.INSERT
            )
        }
    }
}