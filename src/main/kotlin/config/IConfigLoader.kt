package config

import java.io.InputStream

/**
 * Created by Муса on 05.10.2017.
 */
interface IConfigLoader {

    fun getPropertiesMapForEnvironment(
            environmentName: String, pathToResource: String
    ): MutableMap<String, Any?>

    fun getProperties(pathToResource: String): MutableMap<String, Any?>

}