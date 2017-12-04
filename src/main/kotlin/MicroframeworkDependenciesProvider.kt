import assetsmanagement.AssetsPathProvider
import clientimplementedutils.security.JwtPlucker
import dependencymanagement.IMicroFrameworkDependenciesProvider
import dependencymanagement.templating.ITemplateProcessor
import utils.requestparameters.ServletRequestParamtersWrapper
import utils.requestparameters.querystring.QueryStringParametersWrapper
import utils.sessions.JwtSessionHandler

/**
 * Created by Муса on 21.11.2017.
 */
object MicroframeworkDependenciesProvider : IMicroFrameworkDependenciesProvider {

    override val queryStringParametersWrapper: QueryStringParametersWrapper = App.component.queryStringParametersWrapper()

    override val servletRequestParametersWrapper: ServletRequestParamtersWrapper = App.component.servletRequestParametersWrapper()

    override val templateProcessor: ITemplateProcessor = App.component.templateProcessor()

    override val assetsPathsProvider: AssetsPathProvider = App.component.assetsPathProvider()

    override fun run(){
        JwtSessionHandler.let {
            it.algorithm = App.component.jwtHmacsAlgorithm()
            it.clientsJwtPlucker = JwtPlucker()
            it.sessionIdentifyingKey = "id"
            it.verifier = App.component.jwtVerifier()
        }
    }

}