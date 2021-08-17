package Solver.FieldRules;

import java.util.ArrayList;


import Data.DataSet;
import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Data.Value;

public class FieldAllDiffRule extends FieldRule {
    
    public FieldAllDiffRule(Field f) {
        super();
        this.field = f;
        this.fields.add(f);
        this.name = f.getConstantName()+"_AllDiff";
        this.string = "allDiff("+field.getName()+")";
        this.fields.add(f);  
    }
    
    public String toZ3(SyntheticDataBase database) {
        String rule = this.name + " = (";
        ArrayList<Value> vals = field.getAllSyntheticValues(database);
        System.out.println(vals.toString());
        for(int i = 0 ; i < vals.size(); i++) {
            try {
                if(! vals.get(i).isBlank()) {
                    rule = rule + field.getConstantName() + " != "+vals.get(i).string_value;
                }
                if(i < vals.size()-1) {
                    rule = rule + ", ";
                }
            }
            catch(NullPointerException npe) {
                
            }
        }
        rule = rule+")";
        return rule;
    }

    public void setName(String n) {
        this.name = n;
    }

}
