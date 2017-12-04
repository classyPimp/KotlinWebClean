package orm.annotations

import kotlin.reflect.KClass

/**
 * Created by classypimp on 9/29/17.
 */


@Target(AnnotationTarget.FIELD)
annotation class BelongsTo(
        val model: KClass<*>,
        val fieldOnThis: String,
        val fieldOnThat: String
)