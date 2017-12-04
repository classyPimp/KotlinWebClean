package hilfhund.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import controllers.BaseController
import hilfhund.models.Model
import router.src.ServletRequestContext
import java.io.File

/**
 * Created by Муса on 23.11.2017.
 */
class GenerationController(context: ServletRequestContext): BaseController(context) {

    fun jooqGenerate(){

        try {
            org.jooq.util.GenerationTool.main(arrayOf("src/main/resources/jooqConfig.xml"))
        } catch (error: Exception) {
            renderJson(
                    ObjectMapper().createObjectNode().let {
                        it.put("error", "failed: ${error.message}")
                        it.toString()
                    }
            )
            return
        }
        renderJson(
                ObjectMapper().createObjectNode().let {
                    it.put("message", "ok")
                    it.toString()
                }
        )
    }

    fun generateModel() {
        val json = ObjectMapper().readTree(context.request.reader)

        val model = Model(json.get("model"))

        model.errors?.let {
            if (it.isNotEmpty()) {
                renderJson(
                        model.toJsonWithErrors()
                )
                return
            }
        }

        val fileContent = renderTemplateToString("hilfhund/generate/model.ftl", model)

        val modelFileToGenerate = File("src/main/kotlin/models/${model.className!!.toLowerCase()}/${model.className!!}.kt")

        modelFileToGenerate.let {
            if (it.exists()) {
                model.addError("general", "model file already exists")
                renderJson(model.toJsonWithErrors())
                return
            }
            if (!it.parentFile.exists()) {
                it.parentFile.mkdirs()
                it.createNewFile()
            }
            it.writeText(fileContent)
        }

        renderJson(model.toJson())
    }

    fun generateFactory(model: Model? = null){
        val dataModel: Model
        val json = ObjectMapper().readTree(context.request.reader)
        dataModel = model ?: Model(json.get("model"))

        dataModel.let {
            if (dataModel.className == null) {
                it.addError("general", "can't generate factory no model class name given")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            }
            if (dataModel.factoryName == null) {
                it.addError("general", "no factory name provided")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            }
        }

        val factoryFilecontent = renderTemplateToString("hilfhund/generate/factory.ftl", dataModel)

        val factoryFileName = dataModel.factoryName + ".kt"

        val packagePart = dataModel.factoryPackage?.replace('.', '/') ?: ""

        val fileToCreate = File("src/main/kotlin/models/${dataModel.decapitalizedClassName}/factories${packagePart}/${factoryFileName}")

        fileToCreate.let {
            if (it.exists()) {
                dataModel.addError("general", "such factory already exists")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            } else {
                it.parentFile.let {
                    if (!it.exists()) {
                        it.mkdirs()
                    }
                }
                it.createNewFile()
            }
        }

        fileToCreate.writeText(factoryFilecontent)

        val file = File("src/main/kotlin/models/${dataModel.classNameLowerCase}/factories/${dataModel.className}Factories.kt")

        file.let {
            if (it.exists()) {

            } else {
                it.createNewFile()
                renderTemplateToString("hilfhund/generate/factories.ftl", dataModel).let {
                    content ->
                    it.writeText(content)
                }
            }
        }

        renderJson("{\"message\": \"ok\"}")
    }

    fun generateToJsonSerializer(model: Model? = null){
        val dataModel: Model
        val json = ObjectMapper().readTree(context.request.reader)
        dataModel = model ?: Model(json.get("model"))

        dataModel.let {
            if (dataModel.className == null) {
                it.addError("general", "can't generate factory no model class name given")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            }
            if (dataModel.toJsonSerializerName == null) {
                it.addError("general", "no json serializer name provided")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            }
        }

        val factoryFilecontent = renderTemplateToString("hilfhund/generate/toJsonSerializer.ftl", dataModel)

        val jsonSerializerFileName = dataModel.toJsonSerializerName + ".kt"

        val packagePart = dataModel.jsonSerializerPackage?.replace('.', '/') ?: ""

        val fileToCreate = File("src/main/kotlin/models/${dataModel.decapitalizedClassName}/tojsonserializers${packagePart}/${jsonSerializerFileName}")

        fileToCreate.let {
            if (it.exists()) {
                dataModel.addError("general", "such to json serializer already exists")
                renderJson(
                        dataModel.toJsonWithErrors()
                )
                return
            } else {
                it.parentFile.let {
                    if (!it.exists()) {
                        it.mkdirs()
                    }
                }
                it.createNewFile()
            }
        }

        fileToCreate.writeText(factoryFilecontent)

        val file = File("src/main/kotlin/models/${dataModel.classNameLowerCase}/tojsonserializers/${dataModel.className}Serializers.kt")

        file.let {
            if (it.exists()) {

            } else {
                it.createNewFile()
                renderTemplateToString("hilfhund/generate/toJsonSerializers.ftl", dataModel).let {
                    content ->
                    it.writeText(content)
                }
            }
        }

        renderJson("{\"message\": \"ok\"}")
    }


}