package orm.annotations

import kotlin.reflect.KClass

/**
 * Created by classypimp on 10/15/17.
 */
@Target(AnnotationTarget.FIELD)
annotation class HasOneAsPolymorphic(
        val model: KClass<*>,
        val fieldOnThis: String,
        val fieldOnThat: String,
        val polymorphicTypeField: String,
        val onDelete: String = "none"
)