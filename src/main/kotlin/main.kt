import applicationServices.ApplicationBootstrapper
import applicationServices.ApplicationCloser
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import java.io.File


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
   // processDocx()
//    val algorithmHS = Algorithm.HMAC256("secret")
//    val token: String = JWT.create().withClaim("userId", 10).sign(algorithmHS)
//
//    val verifier = JWT.require(algorithmHS)
//            .build() //Reusable verifier instance
//
//    val jwt = verifier.verify(token)
//
//    jwt.let {
//        println("getting: userId")
//        it.claims.let {
//            it.forEach {
//                k, v ->
//                println("$k - ${v.asInt()}")
//            }
//        }
//    }
}


fun processDocx(){
    val template = WordprocessingMLPackage.load(File("uploads/foo.docx"))
    val mainDoc = template.mainDocumentPart

    VariablePrepare.prepare(template)

    val mappings = mutableMapOf<String,String>("foo" to "gello world!")

    mainDoc.variableReplace(mappings)

    template.save(File("uploads/mutated.docx").also {it.createNewFile()})

}


//
//class UserValidator(override val model: User) : UserValidatorTrait(model, model.record.validationManager) {
//    fun validate(){
//        name()
//    }
//
//    fun name(){
//        val tester = nameTester()
//        val name = model.name
//        tester.shouldNotBeNull(name)
//    }
//}
//
//object JacksonAdapter {
//    fun createArray() {
//
//    }
//
//    fun createObject() {
//
//    }
//
//    fun set() {
//
//    }
//
//}
//

