using System.Collections.Generic;

namespace Interpreter.Models.serialize.statementTypes{

    public class ExpressionList: IStatement
    {
        public IList<IStatement> expression = new List<IStatement>();
    }

};