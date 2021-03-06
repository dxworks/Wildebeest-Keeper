package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.LambdaExpression
import com.stannis.dataModel.statementTypes.Name
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLambdaExpression

object LambdaExpressionService {
    fun solveLambdaExpression(data: CPPASTLambdaExpression, statement: Statement?) {
        val lambdaExpression = LambdaExpression(captureDefault = null, declarator = null, body = null, captures = null, closureTypeName = null, implicitFunctionCallName = null)
        if (data.captureDefault != null) {
            val name = Name(name = data.captureDefault.name)
            lambdaExpression.captureDefault = name
        }

        if(data.captures != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            data.captures.forEach { element -> run {
                ASTNodeService.solveASTNode(element as ASTNode, anonimStatement)
                lambdaExpression.addCaptures(anonimStatement.statement)
            } }
        }

        if (data.body != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(data.body as ASTNode, anonimStatement)
            lambdaExpression.body = anonimStatement.statement
        }

        if (data.declarator != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(data.declarator as ASTNode, anonimStatement)
            lambdaExpression.declarator = anonimStatement.statement
        }

        if (data.closureTypeName != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(data.closureTypeName as ASTNode, anonimStatement)
            lambdaExpression.closureTypeName = anonimStatement.statement
        }

        if (data.implicitNames != null) {
            val anonimStatement = AnonimStatement.getNewAnonimStatement()
            data.implicitNames.forEach { elem -> run {
                ASTNodeService.solveASTNode(elem as ASTNode, anonimStatement)
                lambdaExpression.addImplicitFunctionCallName(anonimStatement.statement)
            } }
        }

        StatementMapper.addStatementToStatement(statement!!, lambdaExpression)
    }
}
