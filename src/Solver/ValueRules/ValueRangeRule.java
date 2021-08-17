package Solver.ValueRules;
import Data.DateValue;
import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Data.TimeValue;
import Data.Value;
import Solver.ValueRules.ValueRule;

public class ValueRangeRule extends ValueRule{
    
    public Value min_value;
    public Value max_value;
    
    public ValueRangeRule(Field f, Value min, Value max) {
        super();
        this.min_value = min;
        this.max_value = max;
        this.field = f;
        this.name = f.getConstantName()+ "_Range";
        this.fields.add(field);
        
        this.string = min_value.getStringVal()+ "<="+field.getName()+"<="+max_value.getStringVal();
        
    }
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        if(field.getType().equals(Field.DATE)) {    
            DateValue minDate = min_value.toDateValue();
            DateValue maxDate = max_value.toDateValue();           
            return this.name + "=(" + field.getConstantName()+" >= "+ minDate.toMilliseconds() + ", "+ field.getConstantName()+" <= "+maxDate.toMilliseconds()+")";
        }
        if(field.getType().equals(Field.TIME)) {
            TimeValue minDate = min_value.toTimeValue();
            TimeValue maxDate = max_value.toTimeValue();           
            return this.name + "=(" + field.getConstantName()+" >= "+ minDate.toMilliseconds() + ", "+ field.getConstantName()+" <= "+maxDate.toMilliseconds()+")";
                    
        }
        return this.name + "=(" + field.getConstantName()+" >= "+ Double.parseDouble(min_value.string_value) + ", "+ field.getConstantName()+" <= "+Double.parseDouble(max_value.string_value)+")";
    }
}
