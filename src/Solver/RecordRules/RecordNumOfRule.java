package Solver.RecordRules;

import Data.SyntheticDataBase;

import java.util.ArrayList;

import Data.Field;

public class RecordNumOfRule extends RecordRule{
    
    public ArrayList<String> strings;
    
    public RecordNumOfRule(Field f1, ArrayList<Field> conditionals) {
        this.field = f1;
        this.fields.addAll(fields);
        this.name = "NumOf_"+field.getConstantName();
        this.string = "numOf( ";
        for(Field f : fields) {
            this.string = string + f.getName()+",";
        }
        this.string = string + "})";
    }
    
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        String rule = this.name + " = Distinct(";
        for(Field f : this.fields) {
            rule = rule + f.getConstantName()+",";
        }
        rule = rule + ")";
        return rule;
    }

}
