package servlets

import router.src.Router
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.io.FileInputStream
import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
import java.io.File
import javax.servlet.ServletConfig
import javax.servlet.annotation.MultipartConfig


/**
 * Created by classypimp on 10/2/17.
 */
@WebServlet(name = "frontServlet", urlPatterns = arrayOf("/"))
@MultipartConfig
class FrontServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doGet(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doPost(req, resp)
    }

    override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doDelete(req, resp)
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        Router.doPut(req, resp)
    }

}