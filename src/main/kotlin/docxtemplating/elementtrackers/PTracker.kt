package docxtemplating.elementtrackers

import docxtemplating.EOperationsPerformedOnDocxElement
import docxtemplating.IDocxElementTracker

/**
 * Created by Муса on 24.01.2018.
 */
class PTracker(
        override val element: Any,
        override val indexOfThisAtParentsContent: Int,
        override val parent: Any?
) : IDocxElementTracker {

    override var operationToPerform: EOperationsPerformedOnDocxElement = EOperationsPerformedOnDocxElement.DO_NOTHING
    override var replaceWith: MutableList<Any>? = null

}