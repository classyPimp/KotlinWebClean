import applicationServices.ApplicationBootstrapper
import applicationServices.ApplicationCloser
import org.docx4j.jaxb.Context
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.Body
import org.docx4j.wml.ContentAccessor
import org.docx4j.wml.R
import org.docx4j.wml.Text
import org.jooq.generated.Tables.USERS
import org.jooq.generated.tables.Users
import org.jooq.impl.DSL.field
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
    //processDocx()

}


fun processDocx(){
    println("processing docx")
    val template = WordprocessingMLPackage.load(File("uploads/foo.docx"))


    VariablePrepare.prepare(template)

    val bodyContent = template.mainDocumentPart.jaxbElement.body.content

    traverse(bodyContent)

    template.save(File("uploads/mutated.docx").also {it.createNewFile()})
}

fun traverse(content: MutableList<Any>) {
    content.forEach {
        println("processing ${it.javaClass}")
        when (it) {
            is ContentAccessor -> {
                println("content accessor:")
                traverse(it.content)
            }
            is JAXBElement<*> -> {
                println("jaxb")
                val value = it.value
                println(value)
                when (value) {
                    is Text -> {
                        //value.value = "hello world!"
                        println("parennt is :${value.parent}")
                        println(value.value)
                        //value.value = "GELLO"
                        val parent = value.parent
                        when(parent) {
                            is R -> {
                                //addToR(parent)
                            }
                        }
                    }
                }
            }
            else -> {
                println("else")
                println(it)
            }
        }
    }
}


fun addToR(r: R) {
    r.content.forEach {
        when (it) {
            is Text -> {
                println("should change in addToR")
                it.value = "changed"
            }
            is JAXBElement<*> -> {
                println("addToR jaaxb")
                val value = it.value
                when(value) {
                    is Text -> {
                        it.value = "FOO!"
                    }
                }
            }
        }
    }
}


