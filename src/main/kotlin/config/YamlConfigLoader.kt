package config

import config.propertiesTypes.NamespacedPropertiesMap
import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Paths


/**
 * Created by classypimp on 10/4/17.
 */
class YamlConfigLoader : IConfigLoader {

     override fun getPropertiesMapForEnvironment(environmentName: String, pathToResource: String): MutableMap<String, Any?> {
        var valueToReturn: MutableMap<String, Any?>? = null

        useResourceInputStream(pathToResource) {
            val namespacedProperties = Yaml().load(it) as MutableMap<String, MutableMap<String, Any?>>
            valueToReturn = namespacedProperties.get(environmentName)
        }

         valueToReturn?.let {
             return it
         } ?: throw Exception("no properties for env: ${environmentName} defined in resource under: ${pathToResource}")

    }

    override fun getProperties(pathToResource: String): MutableMap<String, Any?> {
        var valueToReturn: MutableMap<String, Any?>? = null

        useResourceInputStream(pathToResource) {
            valueToReturn = Yaml().load(it) as MutableMap<String, Any?>
        }

        valueToReturn?.let {
            return it
        } ?: throw Exception("configuration at ${pathToResource} could not be read")

    }

    private fun  useResourceInputStream(
            pathToResource: String,
            block: (InputStream)->Unit
    ) {
        val resourceUrl = this.javaClass.classLoader.getResource(pathToResource)
                ?: throw Exception("no such resource found: ${pathToResource}")

        val path = resourceUrl.toURI()

        try {
            val inputStream = FileInputStream(Paths.get(path).toFile())
            inputStream.use(block)
        } catch (error: Exception) {
            throw error
        }
    }

}
