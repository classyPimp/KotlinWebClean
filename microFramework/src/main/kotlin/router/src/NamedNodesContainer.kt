package router.src

class NamedNodesContainer {
    var nodes: MutableList<NamedNode> = mutableListOf()
    var namesToNodeMap: MutableMap<String, NamedNode> = mutableMapOf()

    fun addNode(node: NamedNode) {
        nodes.add(node)
        namesToNodeMap[node.name!!] = node
    }
}