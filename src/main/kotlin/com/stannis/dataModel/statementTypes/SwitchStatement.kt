package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SwitchStatement(
    var controllerExpression: Statement?,
    var body: Statement?,
    override val type: String? = "SwitchStatement"
): Statement {
    fun addControllerExpression(statement: Statement?) {
        controllerExpression = statement
    }

    fun addBody(statement: Statement?) {
        body = statement
    }
}