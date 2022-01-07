object CompareUtil {

    fun compareTitle(oldTitle: Title, newTitle: Title): List<String>? {
        val diffKeys = mutableListOf<String>()
        if (newTitle != oldTitle) {
            if (newTitle.title != oldTitle.title) {
                diffKeys.add("title")
            }
            if (newTitle.category != oldTitle.category) {
                diffKeys.add("category")
            }
            if (newTitle.description != oldTitle.description) {
                diffKeys.add("description")
            }
            if (newTitle.image != oldTitle.image) {
                diffKeys.add("image")
            }
            if (newTitle.awsS3FolderName != oldTitle.awsS3FolderName) {
                diffKeys.add("awsS3FolderName")
            }
            if (newTitle.imageFileName != oldTitle.imageFileName) {
                diffKeys.add("imageFileName")
            }
            if (newTitle.owner != oldTitle.owner) {
                diffKeys.add("owner")
            }
            if (newTitle.recipeId != oldTitle.recipeId) {
                diffKeys.add("recipeId")
            }
            if (newTitle.time != oldTitle.time) {
                diffKeys.add("time")
            }
            return diffKeys
        }
        return null
    }

    fun compareIngredients(
        oldIngredients: List<Ingredient>,
        newIngredients: List<Ingredient>
    ): Map<String, Map<Int, List<String>>?>? {

        val oldIngredientsDeleteMap = mutableMapOf<Int, List<String>>()
        val newIngredientsAddMap = mutableMapOf<Int, List<String>>()
        val newIngredientsPutMap = mutableMapOf<Int, List<String>>()
        val oldIngredientsIds = oldIngredients.map { it.id }
        val newIngredientsIds = newIngredients.map { it.id }
        val wholeFiledList = listOf("container", "ingredient", "quantity")

        if (oldIngredients != newIngredients) {
            for (newIngredient in newIngredients) {
                val newIngredientsFields = mutableListOf<String>()
                if (newIngredient.id in oldIngredientsIds) {
                    val oldIngredient = oldIngredients.first { it.id == newIngredient.id }
                    if (newIngredient.quantity != oldIngredient.quantity ||
                        newIngredient.ingredient != oldIngredient.ingredient ||
                        newIngredient.container != oldIngredient.container
                    ) {
                        if (newIngredient.quantity != oldIngredient.quantity) {
                            newIngredientsFields.add("quantity")
                        }
                        if (newIngredient.ingredient != oldIngredient.ingredient) {
                            newIngredientsFields.add("ingredient")
                        }
                        if (newIngredient.container != oldIngredient.container) {
                            newIngredientsFields.add("container")
                        }
                        newIngredientsPutMap[newIngredient.id] = newIngredientsFields
                    }
                } else {
                    newIngredientsAddMap[newIngredient.id] = wholeFiledList
                }
            }
            for (oldIngredient in oldIngredients) {
                if (oldIngredient.id !in newIngredientsIds) {
                    oldIngredientsDeleteMap[oldIngredient.id] = wholeFiledList
                }
            }
            return mapOf(
                "oldIngredientsToDelete" to oldIngredientsDeleteMap,
                "newIngredientsToAdd" to newIngredientsAddMap,
                "newIngredientsToPut" to newIngredientsPutMap
            )
        }
        return null
    }

    fun compareSteps(
        oldSteps: List<Step>,
        newSteps: List<Step>
    ): Map<String, Map<Int, List<String>>?>? {

        val oldStepsDeleteMap = mutableMapOf<Int, List<String>>()
        val newStepsAddMap = mutableMapOf<Int, List<String>>()
        val newStepsPutMap = mutableMapOf<Int, List<String>>()
        val oldStepsIds = oldSteps.map { it.id }
        val newStepsIds = newSteps.map { it.id }
        val wholeFiledList = listOf("description", "image", "imageFileName", "stepNumber")

        if (oldSteps != newSteps) {
            for (newStep in newSteps) {
                val newStepsFields = mutableListOf<String>()
                if (newStep.id in oldStepsIds) {
                    val oldStep = oldSteps.first { it.id == newStep.id }
                    if (newStep.description != oldStep.description ||
                        newStep.stepNumber != oldStep.stepNumber ||
                        newStep.image != oldStep.image ||
                        newStep.imageFileName != oldStep.imageFileName
                    ) {
                        if (newStep.description != oldStep.description) {
                            newStepsFields.add("description")
                        }
                        if (newStep.stepNumber != oldStep.stepNumber) {
                            newStepsFields.add("stepNumber")
                        }
                        if (newStep.image != oldStep.image) {
                            newStepsFields.add("image")
                        }
                        if (newStep.imageFileName != oldStep.imageFileName) {
                            newStepsFields.add("imageFileName")
                        }
                        newStepsPutMap[newStep.id] = newStepsFields
                    }
                } else {
                    newStepsAddMap[newStep.id] = wholeFiledList
                }
            }
            for (oldStep in oldSteps) {
                if (oldStep.id !in newStepsIds) {
                    oldStepsDeleteMap[oldStep.id] = wholeFiledList
                }
            }
            return mapOf(
                "oldStepsToDelete" to oldStepsDeleteMap,
                "newStepsToAdd" to newStepsAddMap,
                "newStepsToPut" to newStepsPutMap
            )
        }
        return null
    }

}