package ${packagesBean.baseGenerated}

import java.sql.ResultSet
import ${packagesBean.model}

object ${modelClass}FieldsAccessor {

    val fromResultSetRowFieldSetters: HashMap
        <
            String,
            (model: ${modelClass}, resultSet: ResultSet, cursorPosition: Int)-> Unit
        > = hashMapOf(

        <#list fieldBeans as fieldBean>
        "${fieldBean.actualDbFieldName}" to {
            model, resultSet, cursorPosition ->
            model.${fieldBean.property} = resultSet.get${fieldBean.nonNullableType}(cursorPosition)
        }<#sep>,</#sep>
        </#list>

    )

}
