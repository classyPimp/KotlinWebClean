package router.src

class RouteAdder(path: String, handler: ServletRequestHandler, root: PlainNode) {
    private val parts = RouterUtils.splitPathToList(path)
    private val partsSize = parts.size
    private var partIndex = 0
    private val handler: ServletRequestHandler = handler
    private val root = root

    fun add() {
        println("adding parts: ${parts}")
        addTo(root)
    }

    private fun getCurrentPart(): String {
        return parts[partIndex]
    }

    private fun partIndexOutOfBounds(): Boolean {
       return (partIndex == partsSize)
    }

    private fun incrementPartIndex() {
        partIndex += 1
    }

    private fun definePartType(part: String): NodeTypes {
        if (part[0] == ':') {
            return NodeTypes.NAMED
        } else if (part[0] == '*') {
            return NodeTypes.WILD
        } else {
            return NodeTypes.PLAIN
        }
    }

    private fun addTo(node: INode) {
        if (partIndexOutOfBounds()) {
            node.assignHandler(handler)
            return
        }
        val part = getCurrentPart()
        val partType = definePartType(part)
        incrementPartIndex()
        when (partType) {
            NodeTypes.PLAIN -> {
                val newNode = node.plainChildren[part] ?: PlainNode()
                node.addPlainChild(part, newNode)
                addTo(newNode)
            }
            NodeTypes.NAMED -> {
                val newNamedNode = NamedNode(part)
                node.addNamedNode(newNamedNode)
                addTo(newNamedNode)
            }
            NodeTypes.WILD -> {
                val newWildNode = WildNode()
                node.addWildNode(newWildNode)
                addTo(newWildNode)
            }
        }
    }

}