package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.UnaryExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTUnaryExpression

object UnaryExpressionService {

    fun solveUneryExpression(data: CPPASTUnaryExpression, statement: Statement?) {
        val unaryExpression = UnaryExpression(operand = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(data.operand as ASTNode, anonimStatement)
        unaryExpression.operand = anonimStatement.statement as Arguments
        StatementMapper.addStatementToStatement(statement!!, unaryExpression)
    }
}
