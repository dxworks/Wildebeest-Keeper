package com.stannis.services.cppastService

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.declSpecifier.*
import com.stannis.parser.reader.visitor.ASTVisitorOverride
import com.stannis.services.*
import com.stannis.services.astNodes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.astNodes.SimpleDeclSpecifierService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object ASTNodeService {

    private var modifier = "public"

    fun solveASTNode(node: ASTNode, statement: Statement?) {
        when (node) {
            is CPPASTBinaryExpression -> {
                BinaryExpressionService.solveBinaryExpressionService(node, statement)
            }
            is CPPASTFunctionCallExpression -> {
                FunctionCallsService.setFunctionCallExpression(node, statement)
            }
            is CPPASTUnaryExpression -> {
                UnaryExpressionService.solveUneryExpression(node, statement)
            }
            is CPPASTFieldReference -> {
                FieldReferenceService.solveFieldReference(node, statement)
            }
            is CPPASTDeleteExpression -> {
                DeleteExpressionService.solveDeleteExpression(node, statement)
            }
            is CPPASTCastExpression -> {
                CastExpressionService.solveCastExpression(node, statement)
            }
            is CPPASTLiteralExpression -> {
                LiteralExpressionService.solveLiteralExpression(node, statement)
            }
            is CPPASTIdExpression -> {
                StatementMapper.addStatementToStatement(
                    statement!!,
                    IdExpression(node.rawSignature)
                )
            }
            is CPPASTNewExpression -> {
                NewExpressionService
                    .solveNewExpression(node, statement!!)
            }
            is CPPASTConditionalExpression -> {
                ConditionalExpressionService
                    .solveConditionalExpression(node, statement!!)
            }
            is CPPASTArraySubscriptExpression -> {
                ArraySubscriptExpressionService
                    .solveArraySubscript(node, statement!!)
            }
            is CPPASTExpressionList -> {
                ExpressionListService
                    .solveExpressionList(node, statement!!)
            }
            is CPPASTTypeIdExpression -> {
                TypeIdExpressionService.solveTypeIdExpression(
                    statement!!,
                    node
                )
            }
            is CPPASTSimpleTypeConstructorExpression -> {
                SimpleTypeConstructorExpressionService.solveTypeConstructorExpre(
                    node,
                    statement!!
                )
            }
            is CPPASTIfStatement -> {
                IfStatementService
                    .solveIfStatement(node, statement)
            }
            is CPPASTCompoundStatement -> {
                node.statements.iterator()
                            .forEachRemaining { dataStatement: IASTStatement ->
                                CoreParserClass.seeCPASTCompoundStatement(
                                    dataStatement,
                                    statement
                                )
                            }
            }
            is CPPASTDeclarationStatement -> {

                this.solveASTNode(node.declaration as ASTNode, statement)

//                when (node.declaration) {
//                    is CPPASTSimpleDeclaration -> {
//                        DeclarationStatementParser
//                            .declStatement(node.declaration as CPPASTSimpleDeclaration, statement, modifier)
//                    }
//                    is CPPASTStaticAssertionDeclaration -> {
//                        //TODO
//                    }
//                    is CPPASTAliasDeclaration -> {
//
//                    }
//                    is CPPASTUsingDirective -> {
//
//                    }
//                    else -> {
//                        throw Exception()
//                    }
//                }
            }
            is CPPASTExpressionStatement -> {
                ExpressionStatementService
                    .solveExpressionStatement(node,statement)
            }
            is CPPASTSwitchStatement -> {
                SwitchStatementService
                    .solveSwitchStatement(node, statement)
            }
            is CPPASTProblemStatement -> {
                ProblemStatementService
                    .solveProblemStatement(node, statement)
            }
            is CPPASTReturnStatement -> {
                ReturnStatementService
                    .solveReturnStatement(node, statement)
            }
            is CPPASTDoStatement -> {
                DoStatementService
                    .solveDoStatement(node, statement)
            }
            is CPPASTVisibilityLabel -> {
                modifier = node.rawSignature
            }
            is CPPASTSimpleDeclaration -> {
                if(!SimpleDeclarationService.solveDeclSpecifier(
                        node, statement!!, ASTVisitorOverride.getUnit())) {

                }
            }
            is CPPASTFunctionDefinition -> {
                FunctionDefinitionService.solveFunctionDefinition(node, statement)
//                FunctionDefinitionService
//                    .handleCPPASTFunctionDefinition(node, statement)
            }
            is CPPASTForStatement -> {
                ForBlockService.solveForBlock(node, statement)
            }
            is CPPASTDeclarator -> {
                DeclaratorService
                    .solveDeclaratorService(node, statement)
            }
            is CPPASTBreakStatement -> {
                val breaks = BreakStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, breaks)
            }
            is CPPASTWhileStatement -> {
                WhileStatementService
                    .solveWhileStatement(node, statement)
            }
            is CPPASTConstructorInitializer -> {
                ConstructorInitializerService
                    .solveConstructorInitializer(node, statement)
            }
            is CPPASTContinueStatement -> {
                val continueStat =ContinueStatement(node.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, continueStat)
            }
            is CPPASTRangeBasedForStatement -> {
                RangeBaseForStatementService
                    .solveRangeBaseForStatement(node, statement)
            }
            is CPPASTGotoStatement -> {
                val goto = GotoStatement(node.name.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, goto)
            }
            is CPPASTLabelStatement -> {
                LabelStatementService
                    .solveLabelStatement(node, statement)
            }
            is CPPASTNullStatement -> {
                StatementMapper.addStatementToStatement(statement!!, NullStatement(node.rawSignature))
            }
            is CPPASTTemplateDeclaration -> {
                TemplateDeclarationService.solveTemplateDeclaration(node, statement)
            }
            is CPPASTProblemDeclaration -> {
                ProblemDeclarationService.solveProblemDeclaration(node, statement)
            }
            is CPPASTStaticAssertionDeclaration -> {
                StaticAssertionDeclarationService.solveStaticAssertionDeclaration(node, statement!!)
            }
            is CPPASTAliasDeclaration -> {
                AliasDeclarationService.solveAliasDeclarationService(node, statement)
            }
            is CPPASTInitializerList -> {
                println(node)
                //TODO
            }
            is CPPASTTryBlockStatement -> {
                println(node)
                //TODO
            }
            is CPPASTUsingDirective -> {
                UsingDeclarationService.solveUnitDeclaration(node, statement)
            }
            is CPPASTUsingDeclaration -> {
                println(node)
                //TODO
            }
            is CPPASTEqualsInitializer -> {
                val equals = EqualsInitializer(node.rawSignature, null)
                StatementMapper.addStatementToStatement(statement!!, equals)
            }
            is CPPASTSimpleTypeTemplateParameter -> {
                val simpleType = SimpleTypeTemplateParameter(node.name.rawSignature)
                StatementMapper.addStatementToStatement(statement!!, simpleType)
            }
            is CPPASTParameterDeclaration -> {
                ParameterDeclarationService.solveParameterDeclaration(node, statement)
            }
            is CPPASTCaseStatement -> {
                CaseStatementService.solveCaseStatement(node, statement)
            }
            is CPPASTDefaultStatement -> {
                DefaultStatementService.solveDefaultStatement(node, statement)
            }
            is CPPASTTemplateSpecialization -> {
                TemplateSpecializationService.solveTemplateSpecialization(node, statement)
            }
            is CPPASTSimpleDeclSpecifier -> {
                SimpleDeclSpecifierService.solveSimpleDeclSpecifieService(node, statement)
            }
            is CPPASTNamedTypeSpecifier -> {
                NamedTypeSpecifierService.solveNamedTypeSpecifier(node, statement)
            }
            else -> throw Exception()
        }

    }

}