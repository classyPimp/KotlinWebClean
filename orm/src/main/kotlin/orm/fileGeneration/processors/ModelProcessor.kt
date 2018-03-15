package orm.fileGeneration.processors

import orm.annotations.HasOne
import orm.annotations.IsModel
import orm.configs.templatingEngine.TemplatingEngineConfig
import orm.fileGeneration.AggregateModelsBank
import orm.fileGeneration.ProcessorContext
import orm.fileGeneration.services.KotlinGeneratedDirPathAccessor
import orm.fileGeneration.fileGenerators.GenerateModelFiles
import orm.fileGeneration.fileGenerators.GenerateTransactionRunner
import orm.templateDataModels.model.AssociationBeansFactory
import orm.templateDataModels.model.ModelDataModelFactory
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement


/**
 * Created by classypimp on 9/6/17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ModelProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(
                "orm.annotations.IsModel"
        )
    }

    override fun init(processingEnv: ProcessingEnvironment?) {
        runTemplateEngineConfigIfNotRunAlready()
        ProcessorContext.setProcessingEnvironment(processingEnv!!)
        super.init(processingEnv)
    }

    private fun runTemplateEngineConfigIfNotRunAlready() {
        TemplatingEngineConfig.runIfNotRunAlready()
    }

    override fun process(
            annotations: MutableSet<out TypeElement>,
            roundEnv: RoundEnvironment
    ): Boolean {

        val annotatedElements: MutableSet<out Element> = roundEnv.getElementsAnnotatedWith(IsModel::class.java)
        if (annotatedElements.isEmpty()) {
            return false
        }
        KotlinGeneratedDirPathAccessor.setByExtractingFrom(this.processingEnv)

        ProcessorContext.setRoundEnvironment(roundEnv)

        GenerateTransactionRunner().run()
        //associations are not build here, because they require that all models are generated first
        //each model there will be added to AggregateModelsBank
        ModelDataModelFactory.createAllAndAddToModelsBank(annotatedElements, processingEnv.elementUtils)
        //will use models from AggregateModelsBank that are build upper
        AssociationBeansFactory.createAll(AggregateModelsBank.models.values)

        GenerateModelFiles.generate(AggregateModelsBank.models.values)

        return true
    }




}