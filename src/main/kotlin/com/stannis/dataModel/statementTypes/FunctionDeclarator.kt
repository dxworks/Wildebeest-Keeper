package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclaratorInterface
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class FunctionDeclarator(
    override val `$type`: String? = "FunctionDeclarator",
    var parameter: ArrayList<ParameterDeclaration>?,
    var name: NameInterface?,
    var cyclomaticComplexity: Int,
    var modifier: String,
) : Statement, DeclaratorInterface {
    fun addParameter(statement: Statement) {
        if (parameter == null) {
            parameter = ArrayList()
        }
        parameter!!.add(statement as ParameterDeclaration)
    }
}
