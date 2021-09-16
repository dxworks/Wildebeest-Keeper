package com.stannis.parser.reader.visitor

import org.eclipse.cdt.core.dom.ast.*
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator
import org.eclipse.cdt.core.dom.ast.cpp.*
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ASTVisitorOverride: ASTVisitor() {

    override fun visit(classVirtSpecifier: ICPPASTClassVirtSpecifier?): Int {
        println("Found a ICPPASTClassVirtSpecifier" + classVirtSpecifier?.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(declaration: IASTDeclaration): Int {
        println("Found a declaration: " + declaration.rawSignature)
        if(declaration is CPPASTFunctionDefinition) {
            println("func Defi")
            declaration.declSpecifier.rawSignature // return type
            declaration.declarator.name.rawSignature // function name
            (declaration.declarator as CPPASTFunctionDeclarator).parameters // array of parameterDeclaration (int x, int y) function signature/antet
            (declaration.body as CPPASTCompoundStatement).statements
                .iterator().forEachRemaining { data: IASTStatement -> seeCPASTCompoundStatement(data) }
            // CPPASTCompoundStatement array
            // WhileStatement, ExpressionStatement, ProblemStatement, Declaration, IfStatement, etc...
        } else if (declaration is CPPASTSimpleDeclaration) {
            println("simpl Declaration")
            declaration.declSpecifier.rawSignature // return type
            declaration.declarators // array of Declarators
            // much more like int x = function(smth...)
        }
        return PROCESS_CONTINUE
    }

    override fun visit(initializer: IASTInitializer): Int {
        println("Found a Initializer: " + initializer.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(translationUnit: IASTTranslationUnit): Int {
        println("Found a translationUnit: " + translationUnit.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(name: IASTName): Int {
        println("Found a IASTName: " + name.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(parameterDeclaration: IASTParameterDeclaration): Int {
        println("Found a IASTParameterDeclaration: " + parameterDeclaration.rawSignature )
        return PROCESS_CONTINUE
    }

    override fun visit(declarator: IASTDeclarator): Int {
        println("Found an IASTDeclarator " + declarator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(declarSpec: IASTDeclSpecifier): Int {
        println("Found an IASTDeclSpecifier: " + declarSpec.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iast: IASTArrayModifier): Int {
        println("Found an IASTArrayModifier: " + iast.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(ptrOper: IASTPointerOperator): Int {
        println("Found an IASTPointerOperator: " + ptrOper.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iastatr: IASTAttribute): Int {
        println("Found an IASTAttribute: " + iastatr.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(specifier :IASTAttributeSpecifier) : Int {
        println("Found an IASTAttributeSpecifier: " + specifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTToken) : Int {
        println("Found an IASTToken: " + token.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(token: IASTExpression) : Int {
        println("Found an IASTToken: " + token.rawSignature)
        return PROCESS_CONTINUE
    }

     private fun getOperands(binaryExpression: CPPASTBinaryExpression) {
        while((binaryExpression.operand1 !is CPPASTIdExpression) || (binaryExpression.operand1 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand1 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand1 as CPPASTBinaryExpression)
            }
            break
        }
        while((binaryExpression.operand2 !is CPPASTIdExpression) || (binaryExpression.operand2 !is CPPASTLiteralExpression)) {
            if(binaryExpression.operand2 is CPPASTBinaryExpression) {
                getOperands(binaryExpression.operand2 as CPPASTBinaryExpression)
            }
            break
        }
        if((binaryExpression.operand1 is CPPASTIdExpression) || (binaryExpression.operand1 is CPPASTLiteralExpression) || (binaryExpression.operand1 is CPPASTUnaryExpression)) {
            println(binaryExpression.operand1.rawSignature)
        } else if(binaryExpression.operand1 is CPPASTFunctionCallExpression) {
            println(binaryExpression.operand1.rawSignature)
        }
        if((binaryExpression.operand2 is CPPASTIdExpression) || (binaryExpression.operand2 is CPPASTLiteralExpression) || (binaryExpression.operand2 is CPPASTUnaryExpression)) {
            println(binaryExpression.operand2.rawSignature)
        } else if(binaryExpression.operand2 is CPPASTFunctionCallExpression) {
            println(binaryExpression.operand2.rawSignature)
        }
    }

    private fun declStatement(simpleDeclaration: CPPASTSimpleDeclaration) {
        simpleDeclaration.declSpecifier // type
        simpleDeclaration.declarators // array of CPPASTDeclarator
    }

    private fun seeCPASTCompoundStatement(data: IASTStatement) {
        println("---------")
        println(data.rawSignature)
        when (data) {
            is CPPASTDeclarationStatement -> {
//                (data.declaration as CPPASTSimpleDeclaration).declSpecifier.rawSignature  // type
//                (data.declaration as CPPASTSimpleDeclaration).declarators.iterator().next().name.rawSignature // name
//                refactorCPPASTEEqualsInitializer((data.declaration as CPPASTSimpleDeclaration).declarators[0].initializer)
                declStatement(data.declaration as CPPASTSimpleDeclaration)
            }
            is CPPASTExpressionStatement -> {
                println("expr")
                if (data.expression is CPPASTBinaryExpression) {
                    getOperands(data.expression as CPPASTBinaryExpression)
                } else if( data.expression is CPPASTFunctionCallExpression) {
                    ((data.expression as CPPASTFunctionCallExpression).functionNameExpression as CPPASTIdExpression).name.rawSignature // name
                    (data.expression as CPPASTFunctionCallExpression).arguments // array or arguments
                }
            }
            is CPPASTIfStatement -> {
                println("ifStatement")
                getOperands(data.conditionExpression as CPPASTBinaryExpression)
                seeCPASTCompoundStatement(data.thenClause)
                seeCPASTCompoundStatement(data.elseClause)
            }
            is CPPASTWhileStatement -> {
                println("while")
                getOperands(data.condition as CPPASTBinaryExpression)
                seeCPASTCompoundStatement(data.body)
            }
            is CPPASTProblemStatement -> {
                println("problStatement")
            }
            is CPPASTCompoundStatement -> {
                println("cmpStat")
                data.statements.iterator()
                    .forEachRemaining { dataStatement: IASTStatement -> seeCPASTCompoundStatement(dataStatement) }
            }
        }
        println()
    }

    override fun visit(iastStatement: IASTStatement): Int {
        println("Found an IASTStatement: " + iastStatement.rawSignature)
        if ( iastStatement is CPPASTCompoundStatement ) { //  CPPASTExpressionStatement)
            iastStatement.statements.iterator()
                .forEachRemaining { data: IASTStatement -> seeCPASTCompoundStatement(data) }
        }
        return PROCESS_CONTINUE
    }

    override fun visit(iastTypeId: IASTTypeId): Int {
        println("Found an IASTTypeId: " + iastTypeId.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(iastEnumerator: IASTEnumerator): Int {
        println("Found an IASTTypeId: " + iastEnumerator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(problem: IASTProblem): Int {
        println("Found an IASTTypeId: " + problem.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastBaseSpecifier: ICPPASTBaseSpecifier): Int {
        println("Found an IASTTypeId: " + icppastBaseSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastNamespaceDefinition: ICPPASTNamespaceDefinition): Int {
        println("Found an IASTTypeId: " + icppastNamespaceDefinition.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastTemplateParameter: ICPPASTTemplateParameter): Int {
        println("Found an IASTTypeId: " + icppastTemplateParameter.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastCapture: ICPPASTCapture): Int {
        println("Found an IASTTypeId: " + icppastCapture.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icastDesignator: ICASTDesignator): Int {
        println("Found an IASTTypeId: " + icastDesignator.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastVirtSpecifier: ICPPASTVirtSpecifier): Int {
        println("Found an IASTTypeId: " + icppastVirtSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }

    override fun visit(icppastDecltypeSpecifier: ICPPASTDecltypeSpecifier): Int {
        println("Found an IASTTypeId: " + icppastDecltypeSpecifier.rawSignature)
        return PROCESS_CONTINUE
    }
}
