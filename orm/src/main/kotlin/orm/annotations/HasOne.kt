package orm.annotations

import kotlin.reflect.KClass

/**
 * Created by classypimp on 9/14/17.
 */

@Target(AnnotationTarget.FIELD)
annotation class HasOne(
        val model: KClass<*>,
        val fieldOnThis: String,
        val fieldOnThat: String,
        val onDelete: String = "none"
)