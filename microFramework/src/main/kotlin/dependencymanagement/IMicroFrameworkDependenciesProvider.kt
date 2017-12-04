package dependencymanagement

import assetsmanagement.AssetsPathProvider
import dependencymanagement.templating.ITemplateProcessor
import utils.requestparameters.ServletRequestParamtersWrapper
import utils.requestparameters.querystring.QueryStringParametersWrapper

/**
 * Created by Муса on 21.11.2017.
 */
interface IMicroFrameworkDependenciesProvider {

    val servletRequestParametersWrapper: ServletRequestParamtersWrapper
    val templateProcessor: ITemplateProcessor
    val queryStringParametersWrapper: QueryStringParametersWrapper
    val assetsPathsProvider: AssetsPathProvider

    fun run()

}