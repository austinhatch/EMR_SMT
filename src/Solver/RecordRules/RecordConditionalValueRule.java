package Solver.RecordRules;

import Data.Field;
import Data.SyntheticDataBase;
import Data.Value;
import java.util.HashMap;

import org.apache.commons.math3.distribution.EnumeratedDistribution;

public class RecordConditionalValueRule extends RecordRule {
    public Field field1;
    public Field field2;
    
    public HashMap<Value, EnumeratedDistribution<Value>> distributions;
    
    public RecordConditionalValueRule(Field f1, Field f2, HashMap<Value, EnumeratedDistribution<Value>> dists) {
        super();
        this.field1 = f1;
        this.field2 = f2;
        
        this.distributions = dists;
        this.name = field1.getName() + "_"+field2.getName()+"_weightedVals";
    }
    
    public Value getSample(Value other) {
        EnumeratedDistribution<Value> dist = distributions.get(other);
        return dist.sample();
    }

    @Override
    public String toZ3(SyntheticDataBase database) {
        if(field1.getType().equals(Field.STRING) && field2.getType().equals(Field.STRING)) {
            String s = this.getName() + " = Or(";
            for(Value v : this.distributions.keySet()) {
                s = s+ "And("+ field2.getConstantName()+" == \""+ v.getStringVal()+"\", "+ field1.getConstantName()+ " == \"" + this.getSample(v).getStringVal()+"\"),";
            }
            return s = s+")";
        }
        
        else if(field1.getType().equals(Field.STRING)) {
            String s = this.getName() + " = Or(";
            for(Value v : this.distributions.keySet()) {
                s = s+ "And("+ field2.getConstantName()+" == "+ v.getStringVal()+", "+ field1.getConstantName()+ " == \"" + this.getSample(v).getStringVal()+"\"),";
            }
            return s = s+")";
        }
        
        else if(field2.getType().equals(Field.STRING)) {
            String s = this.getName() + " = Or(";
            for(Value v : this.distributions.keySet()) {
                s = s+ "And("+ field2.getConstantName()+" == \""+ v.getStringVal()+"\", "+ field1.getConstantName()+ " == " + this.getSample(v).getStringVal()+"),";
            }
            return s = s+")";
        }
        else {
            String s = this.getName() + " = Or(";
            for(Value v : this.distributions.keySet()) {
                s = s+ "And("+ field2.getConstantName()+" == "+ v.getStringVal()+", "+ field1.getConstantName()+ " == " + this.getSample(v).getStringVal()+"),";
            }
            return s = s+")"; 
        }
        
    }
    

}
