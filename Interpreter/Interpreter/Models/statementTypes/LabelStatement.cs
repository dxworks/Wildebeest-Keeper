using System.Collections.Generic;

namespace Interpreter.Models{

    public class LabelStatement :IStatement
    {
        public string name { set; get; }

        public IList<IStatement> expressions = new List<IStatement>();

    }

};