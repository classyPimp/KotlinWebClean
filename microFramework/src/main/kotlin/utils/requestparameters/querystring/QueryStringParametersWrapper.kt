package utils.requestparameters.querystring

import java.net.URLDecoder

class QueryStringParametersWrapper {

    fun parse(queryString: String?): Map<String,String> {
        val map = mutableMapOf<String, String>()
        queryString?.let {
            parse(0, URLDecoder.decode(queryString, "UTF-8"), map)
        }
        return map
    }

    private fun parse(startIndex: Int, part: String, map: MutableMap<String, String>) {
        var equalSignIndex: Int? = null
        var delimeterMet: Int? = null
        for (index in startIndex until part.length) {
            if (part[index] == '=') {
                equalSignIndex = index
                continue
            }
            if (part[index] == '&'){
                delimeterMet = index
                break
            }
        }

        delimeterMet?.let {
            delim ->
            equalSignIndex?.let {
                if (it + 1 < delim) {
                    map[part.substring(startIndex, it)] = part.substring(it + 1, delim)
                    if (delim + 3 < part.length) {
                        parse(delim + 1, part, map)
                    }
                    return
                } else {
                    parse(delim + 1, part, map)
                    return
                }
            }
        }
        equalSignIndex?.let {
            if (it + 1 < part.length) {
                map[part.substring(startIndex, it)] = part.substring(it + 1, part.length)
            }
        }
    }



}