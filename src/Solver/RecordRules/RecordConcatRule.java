package Solver.RecordRules;

import Data.SyntheticDataBase;

import java.util.ArrayList;

import Data.Field;

public class RecordConcatRule extends RecordRule{
    
    public ArrayList<String> strings;
    
    public RecordConcatRule(Field f1, ArrayList<Field> add, ArrayList<String> slist) {
        this.fields.add(f1);
        this.fields.addAll(add);
        this.field = f1;
        this.name = f1.getConstantName()+"_Concat";
        this.strings = slist;
        this.string = field.getName() + " = concat(";
        for(String s : strings) {
            this.string = string + "\'"+s+"\', ";
        }
        this.string = string + ")";
    }
    
    
    
    
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        String rule = this.name + " = ("+ this.field.getConstantName()+" == ";
        for(int i = 0 ; i < this.strings.size(); i++) {
            String s = strings.get(i);
            rule = rule + "\""+s+"\"";
            if(i <  this.strings.size()-1) {
                rule = rule + " + ";
            }
        }
        rule = rule + ")";
        return rule;
    }

}
