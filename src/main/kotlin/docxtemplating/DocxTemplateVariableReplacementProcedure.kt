package docxtemplating

import java.io.File
import models.documenttemplatevariable.DocumentTemplateVariable
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.Text
import javax.xml.bind.JAXBElement

/**
 * Created by Муса on 23.01.2018.
 */
class DocxTemplateVariableReplacementProcedure(
        val file: File,
        val variableNameToTemplateVariableMap: MutableMap<String, DocumentTemplateVariable?>
) {

    lateinit var template: WordprocessingMLPackage
    lateinit var bodyContent: MutableList<Any>

    fun execute() {
        prepareRequiredFields()
    }

    private fun prepareRequiredFields() {
        prepareTemplate()
        prepareBody()
    }

    private fun prepareTemplate() {
        template = WordprocessingMLPackage.load(file)
    }

    private fun prepareBody(){
        bodyContent = template.mainDocumentPart.jaxbElement.body.content
    }

    private fun replaceVariablesTraversingContent(contentToTraverse: MutableList<Any>) {
        contentToTraverse.forEach {
            contentItem ->
            when (contentItem) {
                is ContentAccessor -> {
                    replaceVariablesTraversingContent(contentItem.content)
                }
                is JAXBElement<*> -> {
                    traverseJaxbElementUsingItsValue(contentItem)
                }
            }
        }
    }

    private fun traverseJaxbElementUsingItsValue(jaxbElement: JAXBElement<*>) {
        val value = jaxbElement.value
        when (value) {
            is Text -> {
                probeReplacingVariableInText(value)
            }
        }
    }

    private fun probeReplacingVariableInText(text: Text) {
        val string = text.value
        var startCharIndex: Int? = null
        var endCharIndex: Int? = null

        startCharIndex = probeFindStartCharIndex(string)
        if (startCharIndex == null) {
            return
        }

        endCharIndex = probeFindEndCharIndex(startCharIndex + 1, string)

        if (endCharIndex == null ) {
            return
        }

        replaceInText(startCharIndex, endCharIndex, text, string)
    }

    private fun probeFindStartCharIndex(string: String): Int? {
        val stringLentght = string.length
        var cursor: Int = 0
        while (cursor < stringLentght) {
            val charAtCursor = string[cursor]
            if (charAtCursor == '$') {
                if (stringLentght < cursor + 1 && string[cursor+1] == '{') {
                    return cursor
                }
            }
            cursor += 1
        }
        return null
    }

    private fun probeFindEndCharIndex(indexToStartFrom: Int, string: String): Int? {
        val stringLength = string.length
        var cursor = indexToStartFrom
        while (cursor < stringLength) {
            val charAtCursor = string[cursor]
            if (charAtCursor == '}') {
                return cursor
            }
            cursor += 1
        }
        return null
    }

    private fun replaceInText(variableStartIndex: Int, variableEndIndex: Int, text: Text, string: String) {
        val variableName = string.substring(variableStartIndex + 2, variableEndIndex).let {
            extractVariableNameWithoutIdentifier(it)
        }
        val documentTemplateVariable = variableNameToTemplateVariableMap[variableName]
        if (documentTemplateVariable == null) {
            variableNameToTemplateVariableMap[variableName] = null
            return
        }


    }

    private fun extractVariableNameWithoutIdentifier(string: String): String {
        return string.split('-')[0]
    }

}