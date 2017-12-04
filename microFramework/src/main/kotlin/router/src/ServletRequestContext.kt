package router.src

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Муса on 10.10.2017.
 */
class ServletRequestContext(
        val request: javax.servlet.http.HttpServletRequest,
        val response: javax.servlet.http.HttpServletResponse,
        val routeParameters: Map<String, String>,
        val format: String?
)