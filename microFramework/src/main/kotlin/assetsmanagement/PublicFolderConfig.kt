package config.filesAndPaths

/**
 * Created by Муса on 02.11.2017.
 */
class PublicFolderConfig(val map: MutableMap<String, Any?>) {
    var pathToPublicFolderParentDir: String by map
    var relativeToContextPublicFolderName: String by map
    var relativeToContextAssetsPath: String by map
    var pathToUploads: String by map
}