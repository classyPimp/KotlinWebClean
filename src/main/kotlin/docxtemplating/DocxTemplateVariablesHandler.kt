package docxtemplating


import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import java.io.File
import org.docx4j.wml.*
import javax.xml.bind.JAXBElement

/**
 * Created by Муса on 23.01.2018.
 */
class DocxTemplateVariablesHandler(
        private val file: File
) {

    companion object {
        val factory = Context.getWmlObjectFactory()
    }

    val template: WordprocessingMLPackage = WordprocessingMLPackage.load(file)

    fun replaceVariables(
                    variableNamesToLinksMap: MutableMap<String, DocumentTemplateToDocumentVariableLink>
    ) {
        val sdtTags = getStdTags()
        sdtTags.forEach {
            replaceAtTag(it, variableNamesToLinksMap)
        }
    }

    private fun replaceAtTag(
            tag: Tag,
            variableNamesToLinksMap: MutableMap<String, DocumentTemplateToDocumentVariableLink>
    ) {
        val name: String = tag.`val`
        var correspondingLink: DocumentTemplateToDocumentVariableLink? = variableNamesToLinksMap[name]

        if (correspondingLink == null) {
            throw IllegalStateException()
        }

        val tagParent = (tag.parent as SdtPr).parent
        when (tagParent) {
            is SdtRun -> {
                replaceInSdtRun(tagParent, correspondingLink)
            }
            is CTSdtCell -> {
                replaceInCTSdtCell(tagParent, correspondingLink)
            }
            else -> {
                throw IllegalStateException()
            }
        }


    }

    private fun replaceInSdtRun(sdtRun: SdtRun, link: DocumentTemplateToDocumentVariableLink) {
        val valueToReplaceWith: String? = link.defaultValue
        val text = factory.createText()
        val run = factory.createR()
        text.value = valueToReplaceWith
        run.content.add(text)
        val sdtContent = factory.createCTSdtContentRun()
        sdtContent.content.add(run)
        sdtRun.sdtContent = sdtContent
    }

    private fun replaceInCTSdtCell(sdtCell: CTSdtCell, link: DocumentTemplateToDocumentVariableLink) {
        var contents = sdtCell.sdtContent.content

        val valueToReplaceWith: String? = link.defaultValue
        val text = factory.createText()
        val run = factory.createR()
        text.value = valueToReplaceWith
        run.content.add(text)

        contents = contents.mapTo(mutableListOf<Any>()) {
            val jaxbElement = it as JAXBElement<out Any>
            jaxbElement.value
        }

        contents.forEach {
            when (it) {
                is Tc -> {
                    it.content.forEach {
                        when (it) {
                            is P -> {
                                println("got p")
                                it.content.clear()
                                it.content.add(run)
                            }
                        }
                    }
                }
            }
        }
    }

    fun extractVariableNamesAsSet(sdtTags: MutableList<Tag> = getStdTags()): MutableSet<String> {
        val setToReturn = mutableSetOf<String>()
        sdtTags.forEach {
            val nameWithIdentifier = it.`val`
            nameWithIdentifier.split('-').let {
                setToReturn.add(it[0])
            }

        }
        return setToReturn
    }

    fun extractVariableNamesWithIdentifiersAsSet(sdtTags: MutableList<Tag> = getStdTags()): MutableSet<String> {
        val setToReturn = mutableSetOf<String>()
        sdtTags.forEach {
            val nameWithIdentifier = it.`val`
            setToReturn.add(nameWithIdentifier)
        }
        return setToReturn
    }

    @Suppress("UNCHEKED_CAST")
    private fun getStdTags(): MutableList<Tag> {
        return template.mainDocumentPart.getJAXBNodesViaXPath("//w:tag", false) as MutableList<Tag>
    }


    fun extractStdRuns(stdTags: MutableList<Tag>): MutableList<SdtRun> {
        val listToReturn = mutableListOf<SdtRun>()
        stdTags.forEach {
            val stdPr = it.parent as SdtPr
            val stdRun = stdPr.parent as SdtRun
            listToReturn.add(stdRun)
        }
        return listToReturn
    }



}