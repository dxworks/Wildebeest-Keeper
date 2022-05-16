using System.Collections.Generic;

namespace Interpreter.Models.complexStatementTypes
{

    public class ClassOrHeaderWithPath : IStatement
    {
        public string path;

        public ClassOrHeader classOrHeader;

        public IList<ClassOrHeaderWithPath> relationList = new List<ClassOrHeaderWithPath>();

    }

}