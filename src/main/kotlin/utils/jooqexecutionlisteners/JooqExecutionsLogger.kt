package utils.jooqexecutionlisteners


import org.jooq.ExecuteContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DefaultExecuteListener
import org.jooq.tools.StringUtils

class JooqExecutionsLogger : DefaultExecuteListener() {

    val logger = App.component.defaultLogger()
    val context = DSL.using(
            SQLDialect.POSTGRES_9_5,
            Settings().withRenderFormatted(true)
    )

    override fun executeStart(ctx: ExecuteContext) {
        logger.info("--------------------------------")
        ctx.query()?.let {
            logger.info(context.renderInlined(ctx.query()))
        }

        ctx.routine()?.let {
            logger.info(context.renderInlined(ctx.routine()))
        }

        logger.info("---------------------------------")
    }
}