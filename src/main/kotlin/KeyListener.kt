import java.awt.event.KeyEvent
import java.awt.event.KeyListener

object KeyListener : KeyListener {
    var inputStr = ""

    override fun keyTyped(e: KeyEvent?) {
        e?.let {
//            inputStr += e.keyChar
//            when(Main.config.mode) {
//                Modes.NORMAL -> parseNormalInputs()
//                Modes.INSERT -> parseInsertInputs()
//            }
            if (!e.isControlDown)
                return

            if(e.keyChar.code > 48 && e.keyChar.code < 58) {
                Main.moveToTab(e.keyChar.code - 49)
                return
            }

            when (e.keyChar.code) {
                3 -> Main.closeActiveTab() // c
                14 -> Main.createNewTab() // n
                15 -> Main.openFile() // o
                19 -> Main.saveFile() // s
                61 -> Main.setFont(Main.config.fontFamily, Main.config.editorFontFamily, Main.config.fontSize + 1) // +
                45 -> Main.setFont(Main.config.fontFamily, Main.config.editorFontFamily, Main.config.fontSize - 1) // -
            }
        }
    }

//    fun parseNormalInputs() {
//        println(inputStr)
//
//        if(inputStr.filter { it.code == 27 }.isNotEmpty()) { // escape
//            inputStr = ""
//            return
//        }
//
//        when(inputStr[0].code) {
//            'w'.code -> { // up
//                Main.moveCaretVertical(-1)
//                inputStr = ""
//            }
//            'a'.code -> { // left
//                Main.moveCaretHorizontal(-1)
//                inputStr = ""
//            }
//            's'.code -> { // down
//                Main.moveCaretVertical(1)
//                inputStr = ""
//            }
//            'd'.code -> { // right
//                Main.moveCaretHorizontal(1)
//                inputStr = ""
//            }
//            'e'.code -> { // insert
//                Main.enterInsertMode()
//                inputStr = ""
//            }
//
//            'n'.code -> { // newTab
//                Main.createNewTab()
//                inputStr = ""
//            }
//            'c'.code -> { // closeTab
//                Main.closeActiveTab()
//                inputStr = ""
//            }
//            in '1'.code..'9'.code -> { // moveTab1-9
//                Main.moveToTab(inputStr[0].code - 49)
//                inputStr = ""
//            }
//
//            'f'.code -> { // find
//                inputStr = ""
//            }
//            'o'.code -> { // open
//                Main.openFile()
//                inputStr = ""
//            }
//            'd'.code -> { // delete and deleteLine
//                inputStr = ""
//            }
//            'c'.code -> { // copy
//                inputStr = ""
//            }
//            'v'.code -> { // paste
//                inputStr = ""
//            }
//            's'.code -> { // save
//                Main.saveFile()
//                inputStr = ""
//            }
//        }
//    }
//
//    fun parseInsertInputs() {
//        if(inputStr.filter { it.code == 27 }.isNotEmpty()) { // escape
//            Main.enterNormalMode()
//            inputStr = ""
//        }
//    }

    override fun keyPressed(e: KeyEvent?) {
    }

    override fun keyReleased(e: KeyEvent?) {
    }
}