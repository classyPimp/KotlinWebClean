
              /////////////////////////////////////////////////////////////////////////////////////
                                    //BELONGS_TO_POLYMORPHIC ${ab.capitalizedPropertyName}
              ////////////////////////////////////////////////////////////////////////////////////

    class ${ab.capitalizedPropertyName}Union() {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}: ${associatedModelDataModel.modelClass}? = null
        </#list>
    }

    class ${ab.capitalizedPropertyName}Lists() {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}List: MutableList<${associatedModelDataModel.modelClass}>? = null
        </#list>
    }

    class ${ab.capitalizedPropertyName}SelectQueryBuilders() {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}: ${associatedModelDataModel.modelClass}SelectQueryBuilder? = null
        </#list>
    }

    class ${ab.capitalizedPropertyName}ReferenceMaps(models: MutableList<${modelClass}>) {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map = mutableMapOf<${ab.fieldOnThat.nonNullableType}, MutableList<${modelClass}>>()
        </#list>
        init {
            for (model in models) {
                model.${ab.fieldOnThis.property}?.let {
                    when(model.${ab.polymorphicTypeField.property}) {
                        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                        "${associatedModelDataModel.modelClass}" -> {
                            if (${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map[it] != null) {
                               ${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map[it]!!.add(model)
                            } else {
                               ${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map[it] = mutableListOf<${modelClass}>(model)
                            }
                        }
                        </#list>
                        else -> {

                        }
                    }
                }
            }
        }
    }


    class ${ab.capitalizedPropertyName}${ab.fieldOnThat.capitalizedProperty}Sets(
      val referenceMaps: ${ab.capitalizedPropertyName}ReferenceMaps
    ) {
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        var ${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}Set: MutableSet<${ab.fieldOnThis.nonNullableType}>? = null
        </#list>
        init {
            <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
            referenceMaps.${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map.keys.let {
                if (!it.isEmpty()) {
                    ${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}Set = it
                }
            }
            </#list>
        }
    }

    fun load${ab.capitalizedPropertyName}(model: ${modelClass}){
        if (model.${ab.polymorphicTypeField.property} == null) {
            return
        }
        when(model.${ab.polymorphicTypeField.property}) {
            <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
            "${associatedModelDataModel.modelClass}" -> {
                val associatedModel = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}Eq(model.${ab.fieldOnThis.property}).execute().firstOrNull()
                model.${ab.propertyName} = associatedModel
            }
            </#list>
        }
    }

    inline fun load${ab.capitalizedPropertyName}(
            model: ${modelClass},
            blockYieldingQueryBuilder: (${ab.capitalizedPropertyName}SelectQueryBuilders)->Unit
        ){
        val queryBuilders = ${ab.capitalizedPropertyName}SelectQueryBuilders()
        when(model.${ab.polymorphicTypeField.property}) {
            <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
            "${associatedModelDataModel.modelClass}" -> {
                val queryBuilder = ${associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}Eq(model.${ab.fieldOnThis.property})
                queryBuilders.${associatedModelDataModel.modelClassDecapitalized} = queryBuilder
                blockYieldingQueryBuilder.invoke(queryBuilders)
                model.${ab.propertyName} = queryBuilder.execute().firstOrNull()
            }
            </#list>
        }
    }


    fun load${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>){
        val referenceMaps = ${ab.capitalizedPropertyName}ReferenceMaps(models)
        val thatModelLists = ${ab.capitalizedPropertyName}Lists()

        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        referenceMaps.${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map.keys.let {
            if (!it.isEmpty()) {
                thatModelLists.${associatedModelDataModel.modelClassDecapitalized}List = ${associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}In(it).execute()
            }
        }
        </#list>
        merge${ab.capitalizedPropertyName}(referenceMaps, thatModelLists)
    }

    fun load${ab.capitalizedPropertyName}(
          models: MutableList<${modelClass}>,
          blockYieldingQueryBuilder: (${ab.capitalizedPropertyName}SelectQueryBuilders)->Unit
        ){
        val referenceMaps = ${ab.capitalizedPropertyName}ReferenceMaps(models)
        val thatModelLists = ${ab.capitalizedPropertyName}Lists()
        val queryBuilders = ${ab.capitalizedPropertyName}SelectQueryBuilders()

        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        referenceMaps.${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map.keys.let {
            if (!it.isEmpty()) {
                queryBuilders.${associatedModelDataModel.modelClassDecapitalized} = ${associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}In(it)
            }
        }
        </#list>
        
        blockYieldingQueryBuilder.invoke(queryBuilders)

        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        queryBuilders.${associatedModelDataModel.modelClassDecapitalized}?.let {
            thatModelLists.${associatedModelDataModel.modelClassDecapitalized}List = it.execute()
        }
        </#list>

        merge${ab.capitalizedPropertyName}(referenceMaps, thatModelLists)
    }


    fun merge${ab.capitalizedPropertyName}(
            models: MutableList<${modelClass}>,
            blockWhereYouSupposedToAddCommentableLists: (${ab.capitalizedPropertyName}Lists)->Unit
        ) {
        val thatModelLists = ${ab.capitalizedPropertyName}Lists()
        blockWhereYouSupposedToAddCommentableLists.invoke(thatModelLists)
        val referenceMaps = ${ab.capitalizedPropertyName}ReferenceMaps(models)
        merge${ab.capitalizedPropertyName}(referenceMaps, thatModelLists)
    }


    inline fun merge${ab.capitalizedPropertyName}(
            models: MutableList<${modelClass}>,
            blockProvidingAssociatedModelsToMerge: (${ab.capitalizedPropertyName}${ab.fieldOnThat.capitalizedProperty}Sets, ${ab.capitalizedPropertyName}Lists)->Unit
        ) {
        val referenceMaps = ${ab.capitalizedPropertyName}ReferenceMaps(models)
        val ${ab.propertyName}${ab.fieldOnThat.capitalizedProperty}Sets = ${ab.capitalizedPropertyName}${ab.fieldOnThat.capitalizedProperty}Sets(referenceMaps)
        val thatModelLists = ${ab.capitalizedPropertyName}Lists()
        blockProvidingAssociatedModelsToMerge.invoke(${ab.propertyName}${ab.fieldOnThat.capitalizedProperty}Sets, thatModelLists)
        merge${ab.capitalizedPropertyName}(referenceMaps, thatModelLists)
    }

    inline fun merge${ab.capitalizedPropertyName}(referenceMaps: ${ab.capitalizedPropertyName}ReferenceMaps, thatModelLists: ${ab.capitalizedPropertyName}Lists){
        <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
        thatModelLists.${associatedModelDataModel.modelClassDecapitalized}List?.let {
            for (thatModel in it) {
                thatModel.${ab.fieldOnThat.property}?.let {
                    referenceMaps.${associatedModelDataModel.modelClassDecapitalized}${ab.fieldOnThat.capitalizedProperty}To${modelClass}Map[it]?.forEach {
                        it.${ab.propertyName} = thatModel
                    }
                }
            }
        }
        </#list>
    }

    fun copyForeignKeyFromContained${ab.capitalizedPropertyName}(model: ${modelClass}) {
        val associatedModel = model.${ab.propertyName}
        if (associatedModel != null) {
            when (associatedModel) {
                <#list ab.associatedPolymorphicModelDataModels as associatedModelDataModel>
                is ${associatedModelDataModel.modelClass} -> {
                    model.${ab.fieldOnThis.property} = associatedModel.${ab.fieldOnThat.property}
                    model.${ab.polymorphicTypeField.property} = "${associatedModelDataModel.modelClass}"
                }
                </#list>
            }
        }
    }

    fun copyForeignKeyFromContained${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>) {
        for (model in models) {
            copyForeignKeyFromContained${ab.capitalizedPropertyName}(model)
        }
    }





  ////////////////////
