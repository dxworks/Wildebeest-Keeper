package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.DeleteExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeleteExpression

class DeleteExpressionService {
    fun solveDeleteExpression(cppastDeleteExpression: CPPASTDeleteExpression, statement: Statement) {
        val delExpression = DeleteExpression(cppastDeleteExpression.operand.rawSignature)
        StatementMapper.addStatementToStatement(statement, delExpression)
    }
}