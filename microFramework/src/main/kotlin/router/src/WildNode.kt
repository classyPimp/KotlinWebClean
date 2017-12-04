package router.src

class WildNode : INode {
    override var name: String? = null

    override var handler: ServletRequestHandler? = null
    override var hasHandler: Boolean = false

    override var hasAnyTypeOfChildren: Boolean = false
    override var hasPlainChildren: Boolean = false
    override var hasNamedChildren: Boolean = false
    override var hasWildChildren: Boolean = false

    override val plainChildren: MutableMap<String, PlainNode> = mutableMapOf()
    override val namedChildren = NamedNodesContainer()
    override val wildChildren = WildNodesContainer()
}