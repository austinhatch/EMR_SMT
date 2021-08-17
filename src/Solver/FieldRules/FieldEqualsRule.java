package Solver.FieldRules;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;

public class FieldEqualsRule extends FieldRule{
    public Field other;
    
    public FieldEqualsRule(Field f, Field o) {
        super();
        this.field = f;
        this.other = o;
        this.name = f.getConstantName()+"_Equals_"+o.getConstantName();
        this.string = field.getName() + " = "+ other.getName();
        this.fields.add(field);
        this.fields.add(other);
        
    }
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        return this.name + " = ("+ field.getConstantName() + " == "+ other.getConstantName()+ ")";
    }
    

}
