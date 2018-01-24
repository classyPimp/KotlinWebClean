import applicationServices.ApplicationBootstrapper
import applicationServices.ApplicationCloser
import org.docx4j.jaxb.Context
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.*

import java.io.File
import javax.xml.bind.JAXBElement


//
///**
// * Created by classypimp on 9/6/17.
// */
//
//
//

fun main(args: Array<String>) {
    ApplicationBootstrapper.userDefinedApplicatinBootstrapper.add(RunOnApplicationBootstrap())
    ApplicationCloser.userDefinedApplicationCloser.add(RunOnApplicationShutdown())
    ApplicationBootstrapper.run()
    processDocx()

}


fun processDocx(){
    println("processing docx")
    val template = WordprocessingMLPackage.load(File("uploads/foo.docx"))

    val stds = template.mainDocumentPart.getJAXBNodesViaXPath("//w:tag", false)

    stds.forEach {
        println(it)
        when (it) {
            is Tag -> {
                println(it.`val`)
                getStdContentByTag(it)
            }
        }
    }

    template.save(File("uploads/mutated.docx").also {it.createNewFile()})
}

fun getStdContentByTag(tag: Tag) {
    val parent = tag.parent
    when (parent) {
        is SdtPr -> {
            val std = parent.parent
            println(std)
            when (std) {
                is SdtRun -> {
                    mutateSdtRun(std)
                }
            }

        }
    }
}

fun replaceP(p: P) {
    val factory = Context.getWmlObjectFactory()
    val text = factory.createText()
    val run = factory.createR()
    text.value = "HELLO"
    run.content.add(text)
    p.content.clear()
    p.content.add(run)
}

fun mutateSdtRun(std: SdtRun) {
    val factory = Context.getWmlObjectFactory()
    val sdtContent = factory.createCTSdtContentRun()
    val p = factory.createP()
    val text = factory.createText()
    val run = factory.createR()
    text.value = "HELLO"
    run.content.add(text)
    //p.content.add(run)
    sdtContent.content.add(run)
    println("mutating")
    std.sdtContent = sdtContent
}

