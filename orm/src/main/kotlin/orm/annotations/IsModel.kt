package orm.annotations

import kotlin.reflect.KClass

/**
 * Created by classypimp on 9/6/17.
 */
@Target(AnnotationTarget.CLASS)
annotation class IsModel(val jooqTable: KClass<*>)

