package hilfhund.controllers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.sun.javafx.PlatformUtil
import com.sun.org.apache.xpath.internal.operations.Bool
import controllers.BaseController
import hilfhund.models.Migration
import org.omg.CORBA.Object
import org.zeroturnaround.exec.InvalidExitValueException
import router.src.ServletRequestContext
import org.zeroturnaround.exec.ProcessExecutor
import java.io.File


class MigrationsController(context: ServletRequestContext) : BaseController(context) {

    companion object {
        val migrationFile = File(File("migrations").absolutePath)
    }

    private fun addPlatformSpecificCommandTokens(commands: MutableList<String>){
        if (PlatformUtil.isWindows()) {
            commands.add("cmd.exe")
            commands.add("/c")
        } else {
            commands.add("bash")
            commands.add("-c")
        }
    }

    fun create(){
        val json = ObjectMapper().readTree(context.request.reader)
        val migration = Migration(json)

        if (!migration.isValid()) {
            renderJson(Migration.ToJson.createErrors(migration).toString())
            return
        }

        try {
            val commands = mutableListOf<String>()

            addPlatformSpecificCommandTokens(commands)

            commands.let {
                it.add("rake")
                it.add("db:new_migration")
                it.add("name=${migration.name}")
                it.add("options=${migration.options}")
            }

            val outPut = ProcessExecutor()
                    .directory(migrationFile)
                    .command( commands )
                    .readOutput(true).exitValues(0)
                    .execute().outputUTF8()

            migration.message = outPut

            renderJson(
                    Migration.ToJson.createSuccess(migration).toString()
            )
           return

        } catch (e: InvalidExitValueException) {
            val output = e.getResult().outputUTF8()
            migration.addError("general", output)
            renderJson(
                Migration.ToJson.createErrors(migration).toString()
            )
            return
        }
    }

    fun migrate() {
        val migration = Migration()
        try {
            val commands = mutableListOf<String>()

            addPlatformSpecificCommandTokens(commands)

            commands.let {
                it.add("rake")
                it.add("db:migrate")
            }

            val outPut = ProcessExecutor()
                    .directory(migrationFile)
                    .command( commands )
                    .readOutput(true).exitValues(0)
                    .execute().outputUTF8()

            migration.message = outPut

            renderJson(
                    Migration.ToJson.createSuccess(migration).toString()
            )
            return

        } catch (e: InvalidExitValueException) {
            val output = e.getResult().outputUTF8()
            migration.addError("general", output)
            renderJson(
                    Migration.ToJson.createErrors(migration).toString()
            )
            throw e
        }
    }

    fun rollBack() {
        val migration = Migration()
        try {
            val commands = mutableListOf<String>()

            addPlatformSpecificCommandTokens(commands)

            commands.let {
                it.add("rake")
                it.add("db:rollback")
            }

            val outPut = ProcessExecutor()
                    .directory(migrationFile)
                    .command( commands )
                    .readOutput(true).exitValues(0)
                    .execute().outputUTF8()

            migration.message = outPut

            renderJson(
                    Migration.ToJson.createSuccess(migration).toString()
            )
            return

        } catch (e: InvalidExitValueException) {
            val output = e.getResult().outputUTF8()
            migration.addError("general", output)
            renderJson(
                    Migration.ToJson.createErrors(migration).toString()
            )
            return
        }
    }

    fun listMigrations(){
        val migrationsDir = File(migrationFile, "/db/migrate")
        val migrationsList = mutableListOf<String>()
        migrationsDir.listFiles()?.forEach {
            migrationsList.add(it.name)
        }
        val migration = Migration()
        migration.migrations = migrationsList

        renderJson(Migration.ToJson.createSuccess(migration).toString())

    }

}

