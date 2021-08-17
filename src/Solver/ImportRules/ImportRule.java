package Solver.ImportRules;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import Data.Field;
import Data.StringValue;
import Data.SyntheticDataBase;
import Data.Value;
import Data.ValueTypeException;
import Solver.ConstraintRule;
import Solver.FieldRules.FieldAllDiffRule;
import Solver.RecordRules.RecordAllDiffRule;
import Solver.ValueRules.ValueConditionalDistRule;
import Solver.ValueRules.ValueRandomNumberRule;

public class ImportRule extends ConstraintRule{
    
    public ArrayList<Field> fields;  
    public ArrayList<String> constants = new ArrayList<String>();
    public ArrayList<String> operators;
    public static ArrayList<String> operatorTypes = new ArrayList<String>(){{
        add("allDiff");
        add("numOf");
        add(">=");
        add("=");
        add("<=");
        add("+");
        add("-");
        add("*");
        add("%");
        add("concat");
        add("reuseData");
        add("randomWeightedValue");
        add("monotonic");
        add("lastTableValue");
        add("normalDistribution");

    }};
    public ArrayList<String> values = new ArrayList<String>();
    
   
    public ConstraintRule parsedRule;
    public Field field;
    
    public ImportRule(Field f, ArrayList<Field> rule_fields, String constraint_string, String n) {
        this.name = n;
        this.field =f;
        this.string = constraint_string;
        this.fields = rule_fields;
        this.type = ConstraintRule.IMPORT;               
    }
    
    public Field getField() {
        return this.field;
    }
    
    public void parseRuleType() throws ParseException{
        String[] string_array = this.string.split(" ");
        ArrayList<String> strings = (ArrayList<String>) Arrays.asList(string_array);
        
        for(String s : strings) {
            if(!constants.contains(s)) {
                if(ImportRule.operatorTypes.contains(s)) {
                    this.operators.add(s);
                }
                else {
                    values.add(s);
                }
            }
        }
        
        if(this.fields.size() == 1) {
            //Parse an All Diff
            if(strings.get(0).contains("allDiff")){
                this.parsedRule = new FieldAllDiffRule(this.field);
                return;
            }
            
            //Parse a RandomValueRule
            if(values.contains(strings.get(0)) && operators.contains("<=")) {
                if(values.size() == 2) {
                    //If the field is a String Type, convert to Integer
                    if(this.field.getType().equals(Field.STRING) || this.field.getType().equals(Field.UNDEFINED)) {
                        field.setType(Field.INTEGER);
                    }
                    StringValue min = new StringValue(values.get(0));
                    StringValue max  = new StringValue(values.get(1));
                    this.parsedRule = new ValueRandomNumberRule(field,min, max);
                }
            }
            
            //Parse a ValueWeightedNormalDist
            if(operators.contains("normalDistribution") && operators.contains("randomWeightedValue")) {
                
            }
            
        }
        
        else {
            
            //Parse a record all diff
            if(strings.get(0).contains("allDiff")){
                this.parsedRule = new RecordAllDiffRule(this.fields);
                return;
            }
            
            //Parse a record conditional
            if(operators.contains("->")) {
                
            }
            
            //Parse a Conditional randomWeightedValue
            if(operators.contains("randomWeightedValue") && this.fields.size() == 2) {
                Field f1 = fields.get(0);
                Field f2 = fields.get(1);
                try {
                    this.parsedRule = new ValueConditionalDistRule(f1,f2,f1.getConditionalEnumeratedDistribution(f2, f1.dataset.database));
                    return;
                } 
                catch (ValueTypeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            //Parse a Record reuseData
            
            
              
            //Parse a Record numOf
            
            
            //Parse a Record Arithmnetic
            
            //Parse a Record Compare
            
            
            
        }
        
    }
    
    public void parseConstants() {
        for(Field f : this.fields) {
            this.constants.add(field.getName());            
        }
    }

    @Override
    public String toZ3(SyntheticDataBase database) {
        
     
        
        
        
        
        
        
        
        
        return parsedRule.toZ3(database);
    }
    
    public ConstraintRule getParsedRule() {
        return this.parsedRule;
    }
}
