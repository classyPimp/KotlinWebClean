package daggerComponents.modules

import com.sun.javafx.PlatformUtil
import dagger.Module
import dagger.Provides
import org.im4java.core.ConvertCmd
import utils.imagehandling.AbstractImageProcessor
import utils.imagehandling.IImageProcessorFactory
import utils.imagehandling.Im4JavaImageProcessor
import utils.imagehandling.Im4JavaImageProcessorFactory
import java.io.File
import java.nio.file.Path
import javax.inject.Singleton
/**
 * Created by Муса on 17.11.2017.
 */
@Module
class ImageProcessingModule {

    @Singleton
    @Provides
    fun providesIImageProcessorFactory(): IImageProcessorFactory{
        return Im4JavaImageProcessorFactory()
    }

    @Singleton
    @Provides
    fun providesImageMagicConvertCommand(): ConvertCmd {
        val convertCmd = ConvertCmd()
        if (PlatformUtil.isWindows()) {
            convertCmd.searchPath = "C:\\Program Files\\ImageMagick-6.9.1-Q16"
        }
        return convertCmd
    }

}