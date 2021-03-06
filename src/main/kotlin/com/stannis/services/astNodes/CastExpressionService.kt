package com.stannis.services.astNodes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CastExpression
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCastExpression

object CastExpressionService {

    fun solveCastExpression(cppastCastExpression: CPPASTCastExpression, statement: Statement?) {
        val castExpr = CastExpression(operand = null, typeId = null)

        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(cppastCastExpression.operand as ASTNode, anonimStatement)
        castExpr.operand = anonimStatement.statement as Arguments

        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(cppastCastExpression.typeId as ASTNode, anonimStatement1)
        castExpr.typeId = anonimStatement1.statement as Arguments

        StatementMapper.addStatementToStatement(statement!!, castExpr)
    }
}
