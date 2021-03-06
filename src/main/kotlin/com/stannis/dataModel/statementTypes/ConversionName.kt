package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class ConversionName(override val `$type`: String = "ConversionName", var typeId: TypeId?) :
    Statement, NameInterface {
    override fun getWrittenName(): String {
        if (typeId!!.declSpecifier is NameInterface) {
            return (typeId!!.declSpecifier as NameInterface).getWrittenName()
        }
        return ""
    }
}
