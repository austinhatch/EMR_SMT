package Solver.ValueRules;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Solver.ConstraintRule;

/**
 * Creates rules for values within a field
 * @author austinhatch
 *
 */

public abstract class ValueRule extends ConstraintRule{
    public Field field;
    
    public ValueRule() {
        this.type = ConstraintRule.VALUE;
    }
    
    @Override
    public abstract String toZ3(SyntheticDataBase database);

}
