package router.src

class WildNodesContainer {
    var nodes: MutableList<WildNode> = mutableListOf()
    fun addNode(node: WildNode) {
        nodes.add(node)
    }
}