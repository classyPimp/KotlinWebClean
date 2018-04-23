package models.uploadedfile

/**
 * Created by Муса on 23.04.2018.
 */
enum class UploadedFileHardcodedCategory(val code: Int) {
    USER_PRIMARY_DRIVE_FOLDER(1),
    USER_GENERIC_UPLOADS_FOLDER(2);

    companion object {
        val supportedCodes = UploadedFileHardcodedCategory.values().map { it.code }

        fun contains(code: Int): Boolean {
            return supportedCodes.contains(code)
        }
    }
}