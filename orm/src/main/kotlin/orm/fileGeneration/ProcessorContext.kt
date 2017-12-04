package orm.fileGeneration

import java.lang.IllegalStateException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.util.Elements

/**
 * Created by classypimp on 10/28/17.
 */
object ProcessorContext {

    private var roundEnvironment: RoundEnvironment? = null

    private var processingEnvironment: ProcessingEnvironment? = null

    private var elementUtils: Elements? = null

    fun setRoundEnvironment(value: RoundEnvironment) {
//        roundEnvironment?.let {
//            throw IllegalStateException("roundEnvironment was previously set")
//        }
        roundEnvironment = value
    }

    fun setProcessingEnvironment(value: ProcessingEnvironment) {
        processingEnvironment?.let {
            throw IllegalStateException("processingEnvironment was previously set")
        }
        processingEnvironment = value
        elementUtils = value.elementUtils
    }

    fun getRoundEnvironment(): RoundEnvironment {
        return roundEnvironment!!
    }

    fun getProcessingEnvironment(): ProcessingEnvironment {
        return processingEnvironment!!
    }

    fun getElementUtils(): Elements {
        if (processingEnvironment == null) {
            throw IllegalStateException()
        }
        return elementUtils!!
    }

}