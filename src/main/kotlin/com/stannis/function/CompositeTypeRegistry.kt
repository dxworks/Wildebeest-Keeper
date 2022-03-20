package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.ComplexCompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit

object CompositeTypeRegistry {

    var list: ArrayList<ComplexCompositeTypeSpecifier>? = null
    lateinit var filepath: String

    fun addCompositeTypeSpecifier(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier,
        node: CompositeTypeSpecifier
    ) {
        if (list == null) {
            list = ArrayList()
        }
        var parent: ASTNode = cppastCompositeTypeSpecifier
        while (parent !is CPPASTTranslationUnit) {
            parent = parent.parent as ASTNode
        }
        val datax = (parent.allPreprocessorStatements).map { library -> Name(library.rawSignature) }
        list!!.add(ComplexCompositeTypeSpecifier(node, this.filepath, datax))
    }

    fun setPath(filepath: String) {
        this.filepath = filepath
    }
}