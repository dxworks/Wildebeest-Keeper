package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class LinkageSpecification(
    var literal: String?,
    override val type: String? = "LinkageSpecification"
): Statement