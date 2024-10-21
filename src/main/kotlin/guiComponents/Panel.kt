package guiComponents

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.Token
import org.fife.ui.rtextarea.RTextScrollPane
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.Insets
import java.io.File
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JToolBar
import javax.swing.SwingConstants
import javax.swing.UIManager

class Panel() : JPanel() {
    private var _activeTabIndex = 0

    val tabs = ArrayList<Tab>()

    val textArea = RSyntaxTextArea().apply {
        isCodeFoldingEnabled = true
        highlightCurrentLine = Main.config.lineHighlighting
        antiAliasingEnabled = true
        tabSize = 2;
        currentLineHighlightColor = Color(0, 0, 0, 15)
        lineWrap = true
        font = Font(Main.config.editorFontFamily, Font.PLAIN, Main.config.fontSize)
        addKeyListener(KeyListener)
    }

    val scrollPane = RTextScrollPane(textArea).apply {
        isVisible = true
        lineNumbersEnabled = Main.config.sidebarEnabled
        isFoldIndicatorEnabled = Main.config.sidebarEnabled
        background = UIManager.getColor("TextArea.background")
        gutter.borderColor = Color(0, 0, 0, 0)
    }

    val activeTab: Tab
        get() = tabs[_activeTabIndex]

    val fileName: String
        get() {
            if(activeTab.file == null)
                return "New File"

            return activeTab.file!!.name
        }

    val tabPanel = JToolBar().apply {
        orientation = SwingConstants.VERTICAL
    }

    init {
        layout = BorderLayout()
        add(tabPanel, BorderLayout.WEST)
        add(scrollPane, BorderLayout.CENTER)
        createNewTab()
    }
    
    fun moveToNextTab() {
        if(_activeTabIndex >= tabs.size - 1)
            moveToTab(0, false)
        else
            moveToTab(_activeTabIndex + 1, false)
    }

    fun createNewTab() {
        val index = tabs.size
        tabs.add(Tab("", null, SyntaxConstants.SYNTAX_STYLE_NONE))
        tabPanel.add(newTab(index))
        moveToTab(index, false)
    }

    fun moveToTab(index: Int, calledFromClose: Boolean) {
        if(!calledFromClose) { // this is to prevent the content of a tab from being overridden by the content of a deleted tab
            tabPanel.components[_activeTabIndex].foreground = UIManager.getColor("Button.foreground")
            activeTab.content = textArea.text
        }
        _activeTabIndex = index
        tabPanel.components[_activeTabIndex].foreground = UIManager.getColor("Objects.Green")
        textArea.text = activeTab.content
        textArea.syntaxEditingStyle = activeTab.syntaxEditingStyle
        println("Moving to $index")
    }

    fun changeFont(font: Font, editorFont: Font) {
        textArea.font = editorFont
        scrollPane.gutter.lineNumberFont = editorFont
        tabPanel.components.forEach { it.font = font }
    }

    fun setFile(content: String, file: File) {
        with(textArea) {
            activeTab.file = file
            activeTab.content = content
            this.name = file.name
            this.text = content
            activeTab.syntaxEditingStyle = getSyntaxHighlighting(file.name)
            textArea.syntaxEditingStyle = activeTab.syntaxEditingStyle
        }
    }

    fun hasTab(index: Int): Boolean {
        return !(index > tabs.size - 1 || index < 0)
    }

    fun closeActiveTab() {
        if(tabs.size == 1)
            return

        tabs.removeAt(_activeTabIndex)
        tabPanel.remove(_activeTabIndex)
        for(i in 0..tabs.size - 1) {
            tabPanel.remove(i)
            tabPanel.add(newTab(i), i)
        }
        moveToTab(0, true)
        tabPanel.revalidate()
        tabPanel.repaint()
    }

    fun updateTheme() {
        updateScrollPane()
        updateTextArea()
    }

    private fun updateScrollPane() {
        with(scrollPane) {
            this.border = null
            this.gutter.lineNumberColor = UIManager.getColor("EditorPane.inactiveForeground")
            this.gutter.background = UIManager.getColor("TextArea.background")
        }
    }

    private fun updateTextArea() {
        textArea.background = UIManager.getColor("EditorPane.background")
        textArea.foreground = UIManager.getColor("EditorPane.foreground")
        textArea.caretColor = UIManager.getColor("EditorPane.foreground")
        with(textArea.syntaxScheme) {
            this.getStyle(Token.COMMENT_EOL).foreground = UIManager.getColor("Objects.Grey")
            this.getStyle(Token.COMMENT_MARKUP).foreground = UIManager.getColor("Objects.Grey")
            this.getStyle(Token.COMMENT_KEYWORD).foreground = UIManager.getColor("Objects.Grey")
            this.getStyle(Token.COMMENT_MULTILINE).foreground = UIManager.getColor("Objects.Grey")
            this.getStyle(Token.COMMENT_DOCUMENTATION).foreground = UIManager.getColor("Objects.Grey")
            this.getStyle(Token.IDENTIFIER).foreground = UIManager.getColor("TextArea.foreground")
            this.getStyle(Token.RESERVED_WORD).foreground = UIManager.getColor("Objects.Blue")
            this.getStyle(Token.RESERVED_WORD_2).foreground = UIManager.getColor("Objects.Blue")
            this.getStyle(Token.LITERAL_CHAR).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.LITERAL_BOOLEAN).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.DATA_TYPE).foreground = UIManager.getColor("Objects.Yellow")
            this.getStyle(Token.ERROR_CHAR).foreground = UIManager.getColor("Objects.Red")
            this.getStyle(Token.ERROR_IDENTIFIER).foreground = UIManager.getColor("Objects.Red")
            this.getStyle(Token.ERROR_NUMBER_FORMAT).foreground = UIManager.getColor("Objects.Red")
            this.getStyle(Token.ERROR_STRING_DOUBLE).foreground = UIManager.getColor("Objects.Red")
            this.getStyle(Token.NULL).foreground = UIManager.getColor("Objects.Red")
            this.getStyle(Token.OPERATOR).foreground = UIManager.getColor("Objects.Green")
            this.getStyle(Token.SEPARATOR).foreground = UIManager.getColor("Objects.Purple")
            this.getStyle(Token.FUNCTION).foreground = UIManager.getColor("Objects.Green")
        }

        textArea.revalidate()
    }

    private fun getSyntaxHighlighting(name: String): String {
            when (name.split(".").last()) {
                "c" -> return SyntaxConstants.SYNTAX_STYLE_C
                "cpp" -> return SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS
                "asm" -> return SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86
                "py" -> return SyntaxConstants.SYNTAX_STYLE_PYTHON
                "php" -> return SyntaxConstants.SYNTAX_STYLE_PHP
                "html" -> return SyntaxConstants.SYNTAX_STYLE_HTML
                "js" -> return SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT
                "json" -> return SyntaxConstants.SYNTAX_STYLE_JSON
                "yaml" -> return SyntaxConstants.SYNTAX_STYLE_YAML
                "ruby" -> return SyntaxConstants.SYNTAX_STYLE_RUBY
                "rust" -> return SyntaxConstants.SYNTAX_STYLE_RUST
                "md" -> return SyntaxConstants.SYNTAX_STYLE_MARKDOWN
                "groovy" -> return SyntaxConstants.SYNTAX_STYLE_GROOVY
                "scala" -> return SyntaxConstants.SYNTAX_STYLE_SCALA
                "lua" -> return SyntaxConstants.SYNTAX_STYLE_LUA
                "clojure" -> return SyntaxConstants.SYNTAX_STYLE_CLOJURE
                "xml" -> return SyntaxConstants.SYNTAX_STYLE_XML
                "go" -> return SyntaxConstants.SYNTAX_STYLE_GO
                "java" -> return SyntaxConstants.SYNTAX_STYLE_JAVA
                "kt" -> return SyntaxConstants.SYNTAX_STYLE_KOTLIN
                "ini" -> return SyntaxConstants.SYNTAX_STYLE_INI
                "sh" -> return SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL // this could be problematic
                else -> return SyntaxConstants.SYNTAX_STYLE_NONE
            }
    }

    private fun newTab(index: Int): JButton {
        return JButton("${index + 1}").apply {
            addActionListener {
                moveToTab(index, false)
            }
        }
    }
}