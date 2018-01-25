package docxtemplating


import models.documenttemplatetodocumentvariablelink.DocumentTemplateToDocumentVariableLink
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import java.io.File
import org.docx4j.wml.*

/**
 * Created by Муса on 23.01.2018.
 */
class DocxTemplateVariablesHandler(
        private val file: File
) {

    companion object {
        val factory = Context.getWmlObjectFactory()
    }

    private val template: WordprocessingMLPackage = WordprocessingMLPackage.load(file)

    fun replaceVariables(
                    variableNamesToLinksMap: MutableMap<String, MutableList<DocumentTemplateToDocumentVariableLink>>
    ): MutableSet<String> {
        val unsupportedVariables = mutableSetOf<String>()
        val sdtTags = getStdTags()
        sdtTags.forEach {
            replaceAtTag(it, variableNamesToLinksMap, unsupportedVariables)
        }
        return unsupportedVariables
    }

    private fun replaceAtTag(
            tag: Tag, variableNamesToLinksMap: MutableMap<String, MutableList<DocumentTemplateToDocumentVariableLink>>,
            unsupportedVariables: MutableSet<String>
    ) {
        val nameWithIdentifier = tag.`val`.split('-')
        val name: String = nameWithIdentifier[0]
        val identifier: String? = nameWithIdentifier.getOrNull(1)
        var correspondingLink: DocumentTemplateToDocumentVariableLink? = null

        val links = variableNamesToLinksMap[name]

        if (links == null) {
            unsupportedVariables.add(tag.`val`)
            return
        }

        for (link in links) {
            if (name == link.documentTemplateVariable?.name) {
                if (identifier == null && link.uniqueIdentifier == null) {
                    correspondingLink = link
                    break
                } else {
                    if (identifier == link.uniqueIdentifier) {
                        correspondingLink = link
                        break
                    }
                }
            }
        }

        if (correspondingLink == null) {
            unsupportedVariables.add(tag.`val`)
            return
        }

        val sdtRun = getSdtRunFromTag(tag)
        replaceInSdtRun(sdtRun, correspondingLink)

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

    fun extractVariableNamesAsSet(sdtTags: MutableList<Tag> = getStdTags()): MutableSet<String> {
        val setToReturn = mutableSetOf<String>()
        sdtTags.forEach {
            setToReturn.add(it.`val`)
        }
        return setToReturn
    }

    @Suppress("UNCHEKED_CAST")
    private fun getStdTags(): MutableList<Tag> {
        return template.mainDocumentPart.getJAXBNodesViaXPath("//w:tag", false) as MutableList<Tag>
    }

    fun getSdtRunFromTag(tag: Tag): SdtRun {
        val stdPr = tag.parent as SdtPr
        return stdPr.parent as SdtRun
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