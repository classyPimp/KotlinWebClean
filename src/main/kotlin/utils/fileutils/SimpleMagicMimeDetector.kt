package utils.fileutils

import com.j256.simplemagic.ContentInfoUtil
import java.io.File

/**
 * Created by Муса on 17.11.2017.
 */
class SimpleMagicMimeDetector
constructor(val contentInfoUtil: ContentInfoUtil) : IMimeDetector
{

    override fun detect(file: File): String? {
        return contentInfoUtil.findMatch(file).mimeType
    }

}