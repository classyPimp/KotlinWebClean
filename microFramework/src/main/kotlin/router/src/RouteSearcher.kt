package router.src

class RouteSearcher(uriString: String, root: PlainNode) {
    private val parts = RouterUtils.splitPathToList(uriString)
    private val partsSize = parts.size
    var partIndex = 0
    private val root = root

    var foundServletRequestHandler: ServletRequestHandler? = null
    var format: String? = null
    var routeParameters: MutableMap<String, String> = mutableMapOf()

    private fun notLastPart(): Boolean {
        return (partIndex + 1 != partsSize)
    }

    private fun isLastPart(): Boolean {
        return partIndex + 1 == partsSize
    }

    private fun currentPart(): String {
        return parts[partIndex]
    }

    private fun incrementPartIndex() {
        partIndex += 1
    }

    fun find(): RouteSearcher {
        val handler = findInPlain(root)
        foundServletRequestHandler = handler
        format = RouterUtils.extractFormat(parts.last())
        return this
    }

    private fun bothAreLast(node: INode): Boolean {
        return (!notLastPart() && !node.hasAnyTypeOfChildren)
    }

    private fun findInPlain(node: INode): ServletRequestHandler? {
        if (!node.hasPlainChildren) {
            return findInNamed(node)
        }
        val nodeForPart = node.plainChildren[currentPart()]
        if (nodeForPart != null) {
            if (isLastPart() && nodeForPart.hasHandler) {
                return nodeForPart.handler
            }
            if (!bothAreLast(node)) {
                incrementPartIndex()
                return findInPlain(nodeForPart)
            }
            return null
        } else {
            return findInNamed(node)
        }
    }

    inline fun withBranchedPartIndex(block: (savePointIndex: Int)->Unit) {
        val partIndexSavePoint = partIndex
        block(partIndexSavePoint)
        partIndex = partIndexSavePoint
    }

    private fun findInNamed(node: INode): ServletRequestHandler? {
        if (!node.hasNamedChildren) {
            return findInWild(node)
        }
        node.namedChildren.nodes.iterator().let {
            while(it.hasNext()) {
                val namedChild = it.next()
                if (bothAreLast(namedChild) && namedChild.hasHandler) {
                    routeParameters[namedChild.nonNullName()] =  currentPart()
                    return namedChild.handler
                } else if (notLastPart() && namedChild.hasAnyTypeOfChildren) {
                    withBranchedPartIndex {
                        savePointIndex ->
                        incrementPartIndex()
                        findInPlain(namedChild)?.let {
                            routeParameters[namedChild.nonNullName()] = parts[savePointIndex]
                            return it
                        }
                    }
                }

            }
        }
        return findInWild(node)
    }

    private fun findInWild(node: INode): ServletRequestHandler?{
        if (!node.hasWildChildren) {
            return null
        }
        var lastFoundAppropriateNode: WildNode? = null
        node.wildChildren.nodes.iterator().let {
            while(it.hasNext()) {
                val wildChild = it.next()
                if (wildChild.hasHandler) {
                    lastFoundAppropriateNode = wildChild
                    if (it.hasNext()) {
                        continue
                    } else {
                        return wildChild.handler
                    }
                } else {
                    withBranchedPartIndex {
                        searchRecursivelyOnWildNode(wildChild)?.let {
                            return it
                        }
                    }
                }
            }
        }
        lastFoundAppropriateNode?.let {
            return it.handler
        }
        return null
    }

    private fun searchRecursivelyOnWildNode(wildChild: WildNode): ServletRequestHandler? {
        incrementPartIndex()
        findInPlain(wildChild)?.let {
            return it
        } ?: if (notLastPart()){
            return searchRecursivelyOnWildNode(wildChild)
        }
        return null
    }

}