package Solver.RecordRules;

import java.util.ArrayList;

import Data.EMR;
import Data.Field;
import Data.SyntheticDataBase;
import Solver.ConstraintRule;

public abstract class RecordRule extends ConstraintRule{
    public String name;
    public Field field;
    
    public RecordRule() {
        this.type = ConstraintRule.RECORD;
    }
    
    public String toString() {
        return this.string;
    }

    @Override
    public abstract String toZ3(SyntheticDataBase database);
    
}
