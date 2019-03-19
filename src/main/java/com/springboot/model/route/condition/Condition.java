package com.springboot.model.route.condition;

public class Condition {

   int order;
   enum relationExpression{
      AND,OR,NOT
   }
   boolean hasNext;
   String relation;
}
