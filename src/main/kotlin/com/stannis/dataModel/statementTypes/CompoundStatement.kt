package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.BodyParent
import com.stannis.dataModel.Statement

data class CompoundStatement(
    override val `$type`: String? = "CompoundStatement",
    var statements: ArrayList<Statement>?
) : Statement, BodyParent {
    fun addStatement(statement: Statement) {
        if (statements == null) {
            statements = ArrayList()
        }
        statements!!.add(statement)
    }
}
