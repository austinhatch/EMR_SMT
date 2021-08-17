package Solver.ValueRules;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Data.Value;
import java.util.HashMap;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

public class ValueConditionalDistRule extends ValueRule {
    public Field field1;
    public Field field2;
    
    public HashMap<Value, EnumeratedDistribution<Value>> distributions;
    
    /**
     * Creates a ValueConditionalDistRule
     * @param f1 is the field which we are assigning values for
     * @param f2 is the field which already has the assigned value
     * @param dists
     */
    
    
    public ValueConditionalDistRule(Field f1, Field f2, HashMap<Value, EnumeratedDistribution<Value>> dists) {
        super();
        
        this.field = f1;
        this.field1 = f1;
        this.field2 = f2;
        this.fields.add(f1);
        this.fields.add(f2);
        
        this.distributions = dists;
        this.name = field1.getConstantName() + "_"+field2.getConstantName()+"_weightedVals";
        
        String s = field1.getName() + " = (";
        for(Value v : distributions.keySet()) {
            s = s+field2.getName()+" == "+v.getStringVal()+" ? randomWeightedValue("+field1.getName()+",";
            EnumeratedDistribution<Value> distribution = distributions.get(v);        
            for(Pair<Value, Double> pair : distribution.getPmf()) {
                s = s+" "+pair.getSecond()+" ? "+pair.getFirst()+",";
            }
            s=s+"),";
        }
        s=s+")";
        this.string = s;
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
