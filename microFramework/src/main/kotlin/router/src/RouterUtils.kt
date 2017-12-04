package router.src

/**
 * Created by Муса on 10.10.2017.
 */
object RouterUtils {

    fun extractFormat(uriPart: String): String? {
        var index = 0
        while (index < uriPart.length) {
            if (uriPart[index] == '.' && index != uriPart.length - 1) {
                return uriPart.substring(index + 1, uriPart.length)
            }
            index++
        }
        return null
    }

    fun splitPathToList(path: String): ArrayList<String> {
        val indexesWithSlash = ArrayList<Int>(5)

        var index = 0
        if (path[0] == '/') {
            if (path.length == 1) {
                return arrayListOf(" ")
            }
            index = 1
        }

        var substrBegin = index

        var lastIndex = path.length - 1
        if (path[lastIndex] == '/'){
            lastIndex -= 1
        }

        var slashCount = 0

        while (index <= lastIndex) {
            if (path[index] == '/') {
                slashCount += 1
                indexesWithSlash.add(index)
            }
            index ++
        }

        if (slashCount > 0) {
            var listToReturn = ArrayList<String>(slashCount + 1)
            var indexesWithSlashIndex = 0

            while (indexesWithSlashIndex < indexesWithSlash.size) {
                listToReturn.add(
                        path.substring(
                                substrBegin, indexesWithSlash[indexesWithSlashIndex]
                        )
                )
                substrBegin = indexesWithSlash[indexesWithSlashIndex] + 1
                indexesWithSlashIndex ++
            }

            listToReturn.add(path.substring(indexesWithSlash.last() + 1, path.length))

            return listToReturn

        } else {
            return arrayListOf(path.substring(substrBegin, lastIndex + 1))
        }
    }

}