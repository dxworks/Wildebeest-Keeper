﻿namespace Interpreter.Models
{

    public class FunctionCallWithDeclaration : IStatement
    {
        public IStatement functionCalls { set; get; }
        
        public IStatement declaration { set; get; }
        
        public ComplexCompositeTypeSpecifier complexClass { set; get; }
        
    }

}