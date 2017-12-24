
    // HAS ONE ${modelClass}

    fun load${ab.capitalizedPropertyName}(model: ${modelClass}){
        if (model.${ab.fieldOnThis.property} == null) {
            return
        }
        val associatedModel = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}Eq(model.${ab.fieldOnThis.property}).execute().firstOrNull()
        model.${ab.propertyName} = associatedModel
    }

    inline fun load${ab.capitalizedPropertyName}(
            model: ${modelClass},
            blockYieldingQueryBuilder: (${ab.associatedModelDataModel.modelClass}SelectQueryBuilder)->Unit
        ){
        if (model.${ab.fieldOnThis.property} == null) {
            return
        }
        val selectQueryBuilder = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}Eq(model.${ab.fieldOnThis.property})
        blockYieldingQueryBuilder.invoke(selectQueryBuilder)
        val associatedModel = selectQueryBuilder.execute().firstOrNull()
        model.${ab.propertyName} = associatedModel
    }

    fun load${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>){
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        if (modelReferenceMap.keys.isEmpty()) {
            return
        }
        val associatedModels = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}In(modelReferenceMap.keys).execute()
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    fun load${ab.capitalizedPropertyName}(
          models: MutableList<${modelClass}>,
          blockYieldingQueryBuilder: (${ab.associatedModelDataModel.modelClass}SelectQueryBuilder)->Unit
        ){
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        if (modelReferenceMap.keys.isEmpty()) {
            return
        }
        val selectQueryBuilder = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where${ab.fieldOnThat.capitalizedProperty}In(modelReferenceMap.keys)
        blockYieldingQueryBuilder.invoke(selectQueryBuilder)
        val associatedModels = selectQueryBuilder.execute()
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    fun merge${ab.capitalizedPropertyName}(
            models: MutableList<${modelClass}>,
            associatedModels: MutableList<${ab.associatedModelDataModel.modelClass}>
        ) {
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    fun merge${ab.capitalizedPropertyName}(
            models: MutableList<${modelClass}>,
            blockReturningAssociatedModelsToMerge: (MutableSet<${ab.fieldOnThis.nonNullableType}>)->MutableList<${ab.associatedModelDataModel.modelClass}>
        ) {
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        val associatedModels = blockReturningAssociatedModelsToMerge.invoke(modelReferenceMap.keys)
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    fun merge${ab.capitalizedPropertyName}(modelReferenceMap: MutableMap<${ab.fieldOnThis.nonNullableType}, MutableList<${modelClass}>>, thoseModels: MutableList<${ab.associatedModelDataModel.modelClass}>){
        for (thatModel in thoseModels) {
            thatModel.${ab.fieldOnThat.property}?.let {
                modelReferenceMap[it]?.forEach {
                    it.${ab.propertyName} = thatModel
                }
            }
        }
    }

    fun assignForeignKeyToContained${ab.capitalizedPropertyName}(model: ${modelClass}) {
        val associatedModel = model.${ab.propertyName}
        if (associatedModel != null) {
            associatedModel.${ab.fieldOnThat.property} = model.${ab.fieldOnThis.property}
        }
    }

    fun assignForeignKeyToContained${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>) {
        for (model in models) {
            assignForeignKeyToContained${ab.capitalizedPropertyName}(model)
        }
    }
