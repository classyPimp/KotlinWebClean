package orm.annotations

import kotlin.reflect.KClass

/**
 * Created by classypimp on 10/10/17.
 */

@Target(AnnotationTarget.FIELD)
annotation class BelongsToPolymorphic(
        val models: Array<KClass<*>>,
        val fieldOnThis: String,
        val fieldOnThat: String,
        val polymorphicTypeField: String
)