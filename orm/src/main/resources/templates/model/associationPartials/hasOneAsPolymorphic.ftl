
    // HAS ONE as polymorphic ${modelClass}

    fun load${ab.capitalizedPropertyName}(model: ${modelClass}){
        model.${ab.fieldOnThis.property}?.let {
            val associatedModel = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where(${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.eq(it).and(${ab.associatedModelDataModel.jooqTableInstance}.${ab.polymorphicTypeField.tableFieldName}.eq("${modelClass}"))).execute().firstOrNull()
            model.${ab.propertyName} = associatedModel
        }
    }

    inline fun load${ab.capitalizedPropertyName}(
            model: ${modelClass},
            blockYieldingQueryBuilder: (${ab.associatedModelDataModel.modelClass}SelectQueryBuilder)->Unit
        ){
        model.${ab.fieldOnThis.property}?.let {
            val queryBuilder = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where(${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.eq(it).and(${ab.associatedModelDataModel.jooqTableInstance}.${ab.polymorphicTypeField.tableFieldName}.eq("${modelClass}")))
            blockYieldingQueryBuilder.invoke(queryBuilder)
            val associatedModel = queryBuilder.execute().firstOrNull()
            model.${ab.propertyName} = associatedModel
        }
    }

    fun load${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>){
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        if (modelReferenceMap.keys.isEmpty()) {
            return
        }
        val associatedModels = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where(${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.`in`(modelReferenceMap.keys).and(${ab.associatedModelDataModel.jooqTableInstance}.${ab.polymorphicTypeField.tableFieldName}.eq("${modelClass}"))).execute()
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
        val queryBuilder = ${ab.associatedModelDataModel.modelClass}SelectQueryBuilder().where(${ab.associatedModelDataModel.jooqTableInstance}.${ab.fieldOnThat.tableFieldName}.`in`(modelReferenceMap.keys).and(${ab.associatedModelDataModel.jooqTableInstance}.${ab.polymorphicTypeField.tableFieldName}.eq("${modelClass}")))
        blockYieldingQueryBuilder.invoke(queryBuilder)
        val associatedModels = queryBuilder.execute()
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    fun merge${ab.capitalizedPropertyName}(
            models: MutableList<${modelClass}>,
            associatedModels: MutableList<${ab.associatedModelDataModel.modelClass}>
        ) {
        val modelReferenceMap = ${modelClass}Utils.build${ab.fieldOnThis.capitalizedProperty}To${modelClass}Map(models)
        merge${ab.capitalizedPropertyName}(modelReferenceMap, associatedModels)
    }

    inline fun merge${ab.capitalizedPropertyName}(
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
            associatedModel.${ab.polymorphicTypeField.property} = "${modelClass}"
        }
    }

    fun assignForeignKeyToContained${ab.capitalizedPropertyName}(models: MutableList<${modelClass}>) {
        for (model in models) {
            assignForeignKeyToContained${ab.capitalizedPropertyName}(model)
        }
    }
