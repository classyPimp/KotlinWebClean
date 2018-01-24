package docxtemplating

import docxtemplating.elementtrackers.PTracker
import docxtemplating.elementtrackers.RTracker
import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import java.io.File
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.*
import javax.xml.bind.JAXBElement

/**
 * Created by Муса on 23.01.2018.
 */
class DocxTemplateVariableReplacementProcedure(
        val file: File,
        val nameToLinkMap: MutableMap<String, DocumentTemplateToDocumentVariableLink?>
) {

    val trackedElements: MutableList<IDocxElementTracker> = mutableListOf()
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
        VariablePrepare.prepare(template)
    }

    private fun prepareBody(){
        bodyContent = template.mainDocumentPart.jaxbElement.body.content
    }

    private fun replaceVariablesTraversingContent(contentToTraverse: MutableList<Any>) {
        val size = contentToTraverse.size
        var index = 0
        while (index < size) {
            val contentItem = contentToTraverse[index]

            when (contentItem) {
                is P -> {
                    val tracker = PTracker(
                            indexOfThisAtParentsContent = index,
                            element = contentItem,
                            parent = null
                    )
                    traverseP(contentItem, tracker)
                }
            }

            index += 1
        }
    }

    private fun traverseP(p: P, tracker: IDocxElementTracker) {
        val content = p.content
        val size = content.size
        var index = 0
        while(index < size) {
            val contentItem = content[index]

            when(contentItem) {
                is R -> {
                    val tracker = RTracker(
                            indexOfThisAtParentsContent = index,
                            element = contentItem,
                            parent = p
                    )
                    traverseR(contentItem, tracker)
                }
            }

            index += 1
        }
    }

    private fun traverseR(r: R, tracker: IDocxElementTracker) {
        val content = r.content
        val size = content.size
        var index = 0
        while (index < size) {
            val contentItem = content[index]

            when(contentItem) {
                is Text -> {
                    traverseText(contentItem, tracker)
                }
            }
            index += 1
        }
    }

    private fun traverseText(text: Text, tracker: IDocxElementTracker) {
        val stringValue = text.value

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
        val documentTemplateVariable = nameToLinkMap[variableName]
        if (documentTemplateVariable == null) {
            nameToLinkMap[variableName] = null
            return
        }


    }

    private fun extractVariableNameWithoutIdentifier(string: String): String {
        return string.split('-')[0]
    }

}