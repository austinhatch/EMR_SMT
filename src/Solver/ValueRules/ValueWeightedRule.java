package Solver.ValueRules;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import Data.Field;
import Data.SyntheticDataBase;
import Data.SyntheticDataSet;
import Data.Value;

public class ValueWeightedRule extends ValueRule{
    
    public EnumeratedDistribution<Value> distribution;
    
    public ValueWeightedRule(Field f, EnumeratedDistribution dist) {
        super();   
        this.field = f;
        this.fields.add(f);
        this.name = f.getConstantName()+"_WeightedDistribution";
        this.distribution = dist;
        
        String s = field.getName() + " = randomWeightedValue(";
        for(Pair<Value, Double> pair : distribution.getPmf()) {
            s = s+" "+pair.getSecond()+" ? "+pair.getFirst()+",";
        }
        s=s+")";
        this.string = s;
    }
    
    
    public Value getRandomWeightedValue() {
        return distribution.sample();
    }
    
    @Override
    public String toZ3(SyntheticDataBase database) {
        Value random = this.getRandomWeightedValue(); 
        if(this.field.getType().equals(Field.STRING)) {
            return this.getName() + " = (" + this.field.getConstantName()+ " == \""+ random.string_value+"\")";
        }
        if(random.hasBlankVal()) {
            return this.getName() + " = (" + this.field.getConstantName()+ " == -1)";       
        }
        return this.getName() + " = (" + this.field.getConstantName()+ " == "+ random.string_value+")";       
    }
}
