package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class CastExpression(
    var operand: Statement?,
    var typeId: Statement?,
    override val type: String? = "CastExpression"
) : Statement
