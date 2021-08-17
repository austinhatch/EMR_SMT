package Solver.FieldRules;

import java.util.ArrayList;

import Data.DataSet;
import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Data.Value;

public class FieldMonotonicRule extends FieldRule {
    int start;
    int step;
    
    
    public FieldMonotonicRule(Field f, int s, int k) {
        super();
        this.field = f;
        this.fields.add(f);
        this.start = s;
        this.step = k;
        this.name = f.getConstantName() + "_monotonic_"+s;
        this.string = "monotonic(from("+ this.field.dataset.name+"), "+this.field.name+","+this.start+","+this.step+")";
    }
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        ArrayList<Value> vals = field.getAllSyntheticValues(database);
        System.out.print(vals.toString());
        if(vals.size() == 0) {
            return this.getName()+ " = ("+field.getConstantName()+" == "+ start + ")";
        }
        else {
            Value v = vals.get(vals.size()-1);
            Integer last = Integer.parseInt(v.getStringVal());
            Integer next = last + step;
            return this.getName()+ " = ("+field.getConstantName()+" == "+ next + ")";
        }
    }
}
