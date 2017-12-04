package models.avatar.factories

import models.avatar.Avatar
import utils.requestparameters.IParam

object Create {

    fun create(params: IParam): Avatar {
        val avatar = Avatar()
        params.get("file")?.let {
            it.fileItem()?.let {
                avatar.file.assign(it)
            }
        }
        return avatar
    }

}