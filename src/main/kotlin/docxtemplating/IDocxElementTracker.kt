package docxtemplating

/**
 * Created by Муса on 24.01.2018.
 */
interface IDocxElementTracker {
    val indexOfThisAtParentsContent: Int
    val element: Any
    val parent: Any?
    var operationToPerform: EOperationsPerformedOnDocxElement
    var replaceWith: MutableList<Any>?
}