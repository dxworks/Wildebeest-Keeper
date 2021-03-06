package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Return
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTReturnStatement

object ReturnStatementService {

    fun solveReturnStatement(data: CPPASTReturnStatement, statement: Statement?) {
        val returnStructure = Return(retValue = null)

        if (data.returnValue != null) {
            val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(data.returnValue as ASTNode, anonimStatement1)
            returnStructure.retValue = anonimStatement1.statement
        }

        StatementMapper.addStatementToStatement(statement!!, returnStructure)
    }
}
