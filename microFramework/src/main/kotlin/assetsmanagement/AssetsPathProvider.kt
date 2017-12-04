package assetsmanagement

import config.filesAndPaths.PublicFolderConfig
import java.io.File

/**
 * Created by Муса on 02.11.2017.
 */
class AssetsPathProvider {

    lateinit var js: JsAssets
    lateinit var css: CssAssets

    class JsAssets(
            val map: MutableMap<String, String>
    ): MutableMap<String, String> by map

    class CssAssets(
            val map: MutableMap<String,String>
    ): MutableMap<String,String> by map

    fun configure(config: PublicFolderConfig) {
        buildJsPaths(config)
        buildCssPaths(config)
    }

    private fun buildJsPaths(config: PublicFolderConfig){
        val file: File = config.let {
            File("${it.pathToPublicFolderParentDir}/${it.relativeToContextAssetsPath}/js")
        }

        if (!file.exists()) {
            throw Exception("no directory exists at ${config.relativeToContextAssetsPath}/js")
        }

        val map = mutableMapOf<String, String>()

        file.listFiles()?.forEach {
            iterateAndMapJsAssets(
                    file = it,
                    map = map,
                    depth = null,
                    config = config
            )
        }

        js = JsAssets(map)
        map.forEach { k, v ->
            println("$k : $v")
        }
    }

    private fun buildCssPaths(config: PublicFolderConfig) {
        val file: File = config.let {
            File("${it.pathToPublicFolderParentDir}/${it.relativeToContextAssetsPath}/css")
        }

        if (!file.exists()) {
            throw Exception("no directory exists at ${config.relativeToContextAssetsPath}/js")
        }

        val map = mutableMapOf<String, String>()

        file.listFiles()?.forEach {
            iterateAndMapCssAssets(
                    file = it,
                    map = map,
                    depth = null,
                    config = config
            )
        }

        css = CssAssets(map)

    }

    private fun iterateAndMapJsAssets(
            file: File,
            map: MutableMap<String, String>,
            depth: String?,
            config: PublicFolderConfig
    ) {
        if (file.isDirectory) {
            file.listFiles()?.let {
                it.forEach {
                    iterateAndMapJsAssets(
                            it, map, file.name, config
                    )
                }
            }
        } else {
            val splittedFileName = file.name.split(".")

            val fullName: String = depth?.let {
                config.relativeToContextAssetsPath + "/js/${depth}/" + file.name
            } ?: config.relativeToContextAssetsPath + "/js/" + file.name

            val simpleName: String = depth?.let {
                "${it}/${splittedFileName.first()}"
            } ?: splittedFileName.first()

            if (splittedFileName.last() == "gz" || splittedFileName.last() == "map") {
                return
            }

            map[simpleName] = fullName
        }
    }

    private fun iterateAndMapCssAssets(
            file: File,
            map: MutableMap<String, String>,
            depth: String?,
            config: PublicFolderConfig
    ) {
        if (file.isDirectory) {
            file.listFiles()?.let {
                it.forEach {
                    iterateAndMapJsAssets(
                            it, map, file.name, config
                    )
                }
            }
        } else {
            val splittedFileName = file.name.split(".")

            val fullName: String = depth?.let {
                config.relativeToContextAssetsPath + "/css/${depth}/" + file.name
            } ?: config.relativeToContextAssetsPath + "/css/" + file.name

            val simpleName: String = depth?.let {
                "${it}/${splittedFileName.first()}"
            } ?: splittedFileName.first()

            if (splittedFileName.last() == "js" || splittedFileName.last() == "gz" || splittedFileName.last() == "map") {
                return
            }

            map[simpleName] = fullName
        }
    }

}