package docxtemplating

import models.documenttemplatevariable.DocumentTemplateVariable
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.Text
import java.io.File
import javax.xml.bind.JAXBElement

/**
 * Created by Муса on 23.01.2018.
 */
class DocxTemplateVariablesExtractionProcedure(val file: File) {

    lateinit var template: WordprocessingMLPackage
    lateinit var bodyContent: MutableList<Any>
    val variablesMap: MutableMap<String, DocumentTemplateVariable?> = mutableMapOf()

    fun execute(): MutableMap<String, DocumentTemplateVariable?> {
        prepareRequiredFields()
        extractVariablesTraversingContent(bodyContent)
        return variablesMap
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

    private fun extractVariablesTraversingContent(contentToTraverse: MutableList<Any>) {
        contentToTraverse.forEach {
            contentItem ->
            when (contentItem) {
                is ContentAccessor -> {
                    extractVariablesTraversingContent(contentItem.content)
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
                probeExtractingVariableFromText(value.value)?.forEach {
                    variablesMap[it] = null
                }
            }
        }
    }

    private fun probeExtractingVariableFromText(textValue: String, startIndex: Int = 0, suplliedSet: MutableSet<String>? = null): MutableSet<String>? {
        var setToReturn: MutableSet<String>? = suplliedSet

        var variableIdentifierStartCharIndex: Int? = null
        var variableIdentifierEndCharIndex: Int? = null

        variableIdentifierStartCharIndex = probeFindStartCharIndex(textValue, startIndex)

        if (variableIdentifierStartCharIndex == null) {
            return null
        }

        variableIdentifierEndCharIndex = probeFindEndCharIndex(variableIdentifierStartCharIndex + 1, textValue)

        if (variableIdentifierEndCharIndex == null ) {
            return null
        }

        if (setToReturn == null) {
            setToReturn = mutableSetOf()
        }

        setToReturn.add(textValue.substring(variableIdentifierStartCharIndex + 2, variableIdentifierEndCharIndex))

        if (variableIdentifierEndCharIndex + 1 < textValue.length) {
            probeExtractingVariableFromText(textValue, variableIdentifierEndCharIndex, setToReturn)
        }

        return setToReturn
    }

    private fun probeFindStartCharIndex(string: String, passedCursor: Int = 0): Int? {
        val stringLentght = string.length
        var cursor: Int = passedCursor
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

}