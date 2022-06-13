﻿using System.Collections.Generic;

namespace Interpreter.Models.metrics{
    public class MetricsAditionalData
    {
        public MetricsAditionalData()
        {
            numberOfConstructors = 0;
            numberOfMethods = 0;
            totalComplexity = 0;
            numberOfPublicFields = 0;
            numberOfAbstractMethods = 0;
            numberOfContainedFields = 0;
            numberOfProtectedMethodsFields = 0;
            numberOfAccessedAttributes = 0;
            numberOfattributesDifferentClass = 0;
        }

        public MetricsModel metricsModel = new MetricsModel();
        public string name { set; get; }
        
        public string path { set; get; }

        public Dictionary<string, Dictionary<string,float>> numberOfClassesThatCallsMethodX = new();
        public float numberOfOverridingMethods { set; get; }
        public float numberOfattributesDifferentClass { set; get; }
        public float numberOfAccessedAttributes { set; get; }
        public float numberOfMethods { set; get; }
        public float numberOfConstructors { set; get; }
        public float numberOfPublicFields { set; get; }
        public float numberOfContainedFields { set; get; }
        public float numberOfProtectedMethodsFields { set; get; }
        public float totalComplexity { set; get; }
        public float numberOfAbstractMethods { set; get; }
    }
};