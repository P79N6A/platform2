package com.springboot.model.route.rulePath;

import com.springboot.model.route.condition.*;
import lombok.Data;

import java.util.List;

@Data
public class RulePath {
    Condition ageCondition;
    Condition amountCondition;
    Condition areaCondition;
    Condition handCondition;
    Condition productCondition;
    Condition randomCondition;
    Condition riskCondition;
    Condition weightCondition;
    List<Condition> conditions;
    String conditionType; //single or multicondition

    public RulePath(){

    }

}
