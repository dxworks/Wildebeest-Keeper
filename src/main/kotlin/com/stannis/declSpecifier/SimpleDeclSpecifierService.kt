package com.stannis.declSpecifier

import com.stannis.dataModel.Class
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.Unit
import com.stannis.dataModel.statementTypes.TypedefStructure
import com.stannis.services.ClassService
import com.stannis.services.MethodService
import com.stannis.services.UnitService
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class SimpleDeclSpecifierService {

    private fun simpleDeclSpec(declaration: CPPASTSimpleDeclaration, method: Method, methodService: MethodService) {
        declaration.declarators.iterator().forEachRemaining { data ->
            val typedefSt  =TypedefStructure(null, null, null)
            methodService.addStatement(method, typedefSt)
            if(data is CPPASTFunctionDeclarator) {
                data.parameters.iterator().forEachRemaining { parametersx ->
                    val declaratorTT = Declaration(
                        if(parametersx.declarator.nestedDeclarator != null) {
                            parametersx.declarator.nestedDeclarator.rawSignature
                        } else {
                            parametersx.declarator.name.rawSignature
                        },
                        parametersx.declSpecifier.rawSignature
                        , parametersx.declarator.pointerOperators.size == 1, null
                        ,
                        if (parametersx is CPPASTArrayModifier ) {
                            (parametersx as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                .rawSignature.toInt()
                        } else { 0 }
                        , null
                    )
                    typedefSt.add(declaratorTT)
                }
            }
            val decl = Declaration(
                if(data.nestedDeclarator != null) {
                    data.nestedDeclarator.rawSignature
                } else {
                    data.name.rawSignature
                },
                declaration.declSpecifier.rawSignature,
                data.pointerOperators.size == 1,
                null,
                if (data is CPPASTArrayModifier ) {
                    (data as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 },
                null
            )
            typedefSt.initialization = decl
        }

        println("simpl Declaration")
        declaration.declSpecifier.rawSignature // return type
        declaration.declarators // array of Declarators
        // much more like int x = function(smth...)
    }

    private fun compositeTypeSpecifier(declaration: CPPASTSimpleDeclaration, method: Method, unit: Unit, methodService: MethodService, classService: ClassService, unitService: UnitService): Boolean{
        (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).storageClass // fkey 1 struct fkey 3 class
        if((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).key == 3) {
            val classDeclaration = Class((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature, null, null)
            unitService.addClass(unit, classDeclaration)
            (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).members // array of members
            classService.parseDecl(classDeclaration, (declaration.declSpecifier as CPPASTCompositeTypeSpecifier))
            return false
        }
        (declaration.declSpecifier as CPPASTCompositeTypeSpecifier).members
        val typedefT = TypedefStructure((declaration.declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature, null, null)
        if(declaration.declarators.isNotEmpty()) {
            declaration.declarators.iterator().forEachRemaining {
                    declrS ->
                run {
                    val declAfterTypedef = Declaration(
                        if(declrS.nestedDeclarator != null) {
                            declrS.nestedDeclarator.rawSignature
                        } else {
                            declrS.name.rawSignature
                        },
                        ((declrS.parent as CPPASTSimpleDeclaration).declSpecifier as CPPASTCompositeTypeSpecifier).name.rawSignature,
                        null,
                        null,
                        if (declrS is CPPASTArrayModifier ) {
                            (declrS as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                .rawSignature.toInt()
                        } else { 0 },
                        null
                    )
                    methodService.addDeclaration(method, declAfterTypedef)
                }
            }
        }
        methodService.addStatement(method, typedefT)
        declaration.declSpecifier.children.iterator().forEachRemaining {
                data ->
            run {
                if (data is CPPASTSimpleDeclaration) {
                    println(data.rawSignature)
                    data.declarators.iterator().forEachRemaining {
                            datax ->
                        run {
                            typedefT.add(
                                Declaration(
                                    datax.name.rawSignature,
                                    data.declSpecifier.rawSignature,
                                    datax.pointerOperators.size == 1,
                                    null,
                                    if (datax is CPPASTArrayModifier ) {
                                        (datax as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                                            .rawSignature.toInt()
                                    } else { 0 },
                                    null
                                )
                            )
                        }
                    }
                }
            }
        }
        return true
    }

    fun solveDeclSpecifier(declaration: CPPASTSimpleDeclaration, method: Method, unit: Unit, methodService: MethodService, classService: ClassService, unitService: UnitService): Boolean {
        when (declaration.declSpecifier) {
            is CPPASTSimpleDeclSpecifier -> {
                simpleDeclSpec(declaration, method, methodService)
            }
            is CPPASTCompositeTypeSpecifier -> {
                if(!compositeTypeSpecifier(declaration, method, unit, methodService, classService, unitService)) {
                    return false
                }
            }
            else -> {
                println("Apel de functie data.ceva()")
            }
        }
        return true
    }


}