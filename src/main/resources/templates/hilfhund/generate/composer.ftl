package composers${composerPackage}

import utils.composer.ComposerBase
import models.${classNameLowerCase}.${className}

class ${composerName} : ComposerBase() {

    lateinit var onSuccess: (${className})->Unit
    lateinit var onError: (${className})->Unit

    override fun beforeCompose(){

    }

    override fun compose(){

    }

    override fun fail(error: Throwable) {
        when(error) {

            else -> {
                throw error
            }
        }
    }

    override fun success() {
        onSuccess.invoke()
    }

}

