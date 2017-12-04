package config.propertiesTypes

/**
 * Created by Муса on 05.10.2017.
 */
open class NamespacedPropertiesMap(
        val map: MutableMap<String, MutableMap<String, Any?>> = mutableMapOf<String, MutableMap<String, Any?>>()
) : MutableMap<String, MutableMap<String, Any?>> by map