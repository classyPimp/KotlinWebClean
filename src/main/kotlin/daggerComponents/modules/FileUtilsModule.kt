package daggerComponents.modules

import com.j256.simplemagic.ContentInfoUtil
import dagger.Module
import dagger.Provides
import utils.fileutils.IMimeDetector
import utils.fileutils.SimpleMagicMimeDetector
import javax.inject.Singleton

@Module
class FileUtilsModule {
    @Singleton
    @Provides
    fun providesIMimeDetector(): IMimeDetector {
        return SimpleMagicMimeDetector(ContentInfoUtil())
    }

}