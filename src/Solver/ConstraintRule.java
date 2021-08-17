package Solver;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;

import java.util.*;

public abstract  class ConstraintRule {
    
    public int priority;
    public String name;
    public String string = "";
    public ArrayList<Field> fields = new ArrayList<Field>();
    public Field field;
    
    public static final String VALUE = "value";
    public static final String FIELD = "field";
    public static final String ENTRY = "entry";
    public static final String DATASET = "dataset";
    public static final String RECORD = "record";
    public static final String DATABASE = "database";
    public static final String PATHWAY = "pathway";
    public static final String IMPORT = "import";
    
    public String type;
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public ArrayList<Field> getFields(){
        return this.fields;
    }
    
    public boolean hasField(Field f) {
        if(this.fields.contains(f)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.string;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }
        
        if(!(other instanceof ConstraintRule)) {
            return false;
        }
        
        ConstraintRule o = (ConstraintRule) other;
        
        if(this.toString().equals(o.toString())) {
            return true;
        }
        
        return false;
    }

    public abstract String toZ3(SyntheticDataBase database);
    
}
