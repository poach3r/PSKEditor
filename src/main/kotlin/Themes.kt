import com.formdev.flatlaf.*
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme
import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkMediumIJTheme
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkSoftIJTheme
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme
import com.formdev.flatlaf.intellijthemes.FlatMonocaiIJTheme
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme
import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme
import com.formdev.flatlaf.intellijthemes.FlatSpacegrayIJTheme
import com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme
import com.formdev.flatlaf.themes.FlatMacDarkLaf
import com.formdev.flatlaf.themes.FlatMacLightLaf

object Themes {
    val get = LinkedHashMap<String, Class<FlatLaf>>().apply {
        put("Light", FlatLightLaf().javaClass)
        put("Light Flat", FlatLightFlatIJTheme().javaClass)
        put("MacOS Light", FlatMacLightLaf().javaClass)
        put("Solarized Light", FlatSolarizedLightIJTheme().javaClass)
        put("Arc", FlatArcIJTheme().javaClass)
        put("Gray", FlatGrayIJTheme().javaClass)
        put("Dark", FlatDarkLaf().javaClass)
        put("Material Design Dark", FlatMaterialDesignDarkIJTheme().javaClass)
        put("MacOS Dark", FlatMacDarkLaf().javaClass)
        put("Gruvbox Dark Hard", FlatGruvboxDarkHardIJTheme().javaClass)
        put("Gruvbox Dark Medium", FlatGruvboxDarkMediumIJTheme().javaClass)
        put("Gruvbox Dark Soft", FlatGruvboxDarkSoftIJTheme().javaClass)
        put("Solarized Dark", FlatSolarizedDarkIJTheme().javaClass)
        put("Arc Dark", FlatArcDarkIJTheme().javaClass)
        put("Carbon", FlatCarbonIJTheme().javaClass)
        put("Cobalt 2", FlatCobalt2IJTheme().javaClass)
        put("Dracula", FlatDraculaIJTheme().javaClass)
        put("Monocai", FlatMonocaiIJTheme().javaClass)
        put("Monokai Pro", FlatMonokaiProIJTheme().javaClass)
        put("Nord", FlatNordIJTheme().javaClass)
        put("OneDark", FlatOneDarkIJTheme().javaClass)
        put("Spacegray", FlatSpacegrayIJTheme().javaClass)
        put("Vuesion", FlatVuesionIJTheme().javaClass)
    }

    fun enable(name: String, laf: Class<FlatLaf>) {
        executeStaticMethod(laf, "setup")
        executeStaticMethod(laf, "updateUI")
        Main.updateTheme(name)
    }

    private fun executeStaticMethod(cls: Class<*>, methodName: String, vararg args: Any) {
        try {
            cls.getMethod(methodName, *args.map { arg -> arg::class.java }.toTypedArray()).invoke(null, *args)
        } catch (e: Exception) {
            println("Error executing method: ${e.message}")
        }
    }
}