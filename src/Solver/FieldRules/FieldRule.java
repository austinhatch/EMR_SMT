package Solver.FieldRules;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Solver.ConstraintRule;

public abstract class FieldRule extends ConstraintRule {
   public Field field;
   
   public FieldRule() {
       this.type = ConstraintRule.FIELD;
   }

    @Override    
    public abstract String toZ3(SyntheticDataBase database);

}
