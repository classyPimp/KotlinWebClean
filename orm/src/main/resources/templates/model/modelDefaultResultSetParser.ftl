package ${packagesBean.baseGenerated}

import orm.modelUtils.RepositoryDbUtils
import orm.services.ResultSetUtils
import java.sql.ResultSet
import ${packagesBean.model}
<#list packagesBean.fieldTypes as fieldType>
import ${fieldType}
</#list>

object ${modelClass}ResultSetParser {

    fun parseResultSet(resultSet: ResultSet): MutableList<${modelClass}> {
        val listOfModelToReturn = mutableListOf<${modelClass}>()

        val columnNames: MutableList<String> = ResultSetUtils.buildColumnNameList(resultSet)

        RepositoryDbUtils.executeThisBlockAndCloseResultSet(resultSet) {
            while (resultSet.next()) {
                val model = instantiateModelUsingResultSetRow(resultSet, columnNames)
                listOfModelToReturn.add(model)
            }
        }

        return listOfModelToReturn
    }

    fun instantiateModelUsingResultSetRow(resultSet: ResultSet, listOfColumnNames: MutableList<String>): ${modelClass} {
        val model = ${modelClass}()
        var cursorPosition = 1
        val fieldSetters = ${modelClass}FieldsAccessor.fromResultSetRowFieldSetters

        for (columnName in listOfColumnNames) {
            val fieldSetter = fieldSetters.get(columnName)
            if (fieldSetter != null) {
                fieldSetter.invoke(model, resultSet, cursorPosition)
            }
            cursorPosition += 1
        }

        return model
    }

}
