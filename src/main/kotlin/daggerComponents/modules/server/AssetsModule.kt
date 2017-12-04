package daggerComponents.modules.server

import config.filesAndPaths.PublicFolderConfig
import dagger.Module
import dagger.Provides
import assetsmanagement.AssetsPathProvider
import javax.inject.Singleton

/**
 * Created by Муса on 14.11.2017.
 */
@Module
class AssetsModule {

    @Singleton
    @Provides
    fun providesAssetsPathProvider(publicFolderConfig: PublicFolderConfig): AssetsPathProvider {
        return AssetsPathProvider().apply {
            configure(publicFolderConfig)
        }
    }


}