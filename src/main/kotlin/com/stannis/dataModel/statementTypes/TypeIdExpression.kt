package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class TypeIdExpression(
    var posixMethodCall: String?,
    var parameter: String?
): Statement