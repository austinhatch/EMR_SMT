package Solver.RecordRules;

import Data.SyntheticDataBase;

import java.util.ArrayList;

import Data.Field;

public class RecordAllDiffRule extends RecordRule{
    
    public ArrayList<String> strings;
    
    public RecordAllDiffRule(ArrayList<Field> fields) {
        this.fields.addAll(fields);
        this.name = "All_Diff_"+fields.get(0).getConstantName();
        this.string = "allDiff(items({";
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
