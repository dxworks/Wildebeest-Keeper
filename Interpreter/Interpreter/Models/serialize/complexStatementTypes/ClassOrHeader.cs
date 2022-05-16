using System.Collections.Generic;
using Interpreter.Models.statementTypes;

namespace Interpreter.Models.complexStatementTypes
{

        public class ClassOrHeader : IStatement
        {
                public IList<string> directives = new List<string>();

                public IList<DeclarationWithParent> globalDeclaration = new List<DeclarationWithParent>();

                public IList<DeclarationWithParent> internDeclaration = new List<DeclarationWithParent>();

                public IList<FunctionDefinition> methodsWithFunctionCalls = new List<FunctionDefinition>();

                public IList<FunctionDeclarator> functionCallsWithoutImplementation = new List<FunctionDeclarator>();

                public IList<ComplexCompositeTypeSpecifier> classList = new List<ComplexCompositeTypeSpecifier>();

                public IList<LinkageSpecification> linkageSpecification = new List<LinkageSpecification>();

                public IList<NameSpace> nameSpaces = new List<NameSpace>();
        }

}