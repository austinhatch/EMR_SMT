package Solver.ValueRules;

import java.util.Date;
import java.util.Random;

import Data.DateValue;
import Data.DoubleValue;
import Data.Field;
import Data.IntegerValue;
import Data.SyntheticDataBase;
import Data.TimeValue;
import Data.Value;

public class ValueRandomNumberRule extends ValueRule {
    public Value min_value;
    public Value max_value;
    
    
    public ValueRandomNumberRule(Field f, Value min, Value max) {
        super();
        this.min_value = min;
        this.max_value = max;
        this.field = f;
        this.fields.add(f);
        this.name = f.getConstantName()+ "_RandomNumber";
        this.fields.add(field);      
        this.string = field.getName() + " = randomNumber("+this.min_value+", "+this.max_value+")"; 
        
    }

    
    public Double getRandomDoubleValue() {      
        Double max = Double.parseDouble(max_value.getStringVal());
        Double min = Double.parseDouble(min_value.getStringVal());
        double random = min + (Math.random() * (max-min));
        return random;          
    }
    
    public Long getRandomLongValue() { 
        Random rand = new Random();
        if(field.getType().equals(Field.DATE)) {    
            DateValue minDate = min_value.toDateValue();
            DateValue maxDate = max_value.toDateValue();
            System.out.println("Min Date "+minDate.string_value);
            System.out.println("Max Date "+maxDate.string_value);
            Long max =  maxDate.toMilliseconds();
            Long min =  minDate.toMilliseconds();
            System.out.println("Min Milli "+min);
            System.out.println("Max Milli "+max);
            while(true) {
                Long random = rand.nextLong();
                if(min <= random && random <= max) {
                    Date randdate = new Date(random);
                    System.out.println(randdate.toGMTString());
                    return random;
                }
            }
        }
        
        if(field.getType().equals(Field.TIME)) {
            TimeValue minDate = min_value.toTimeValue();
            TimeValue maxDate = max_value.toTimeValue();        
            Long max = maxDate.toMilliseconds();
            Long min = minDate.toMilliseconds();
            while(true) {
                Long random = rand.nextLong();
                if(min <= random && random <= max) {
                    Date randdate = new Date(random);
                    System.out.println(randdate.toGMTString());
                    return random;
                }
            }
        }
        return null;
    }

    
    @Override
    public String toZ3(SyntheticDataBase database) {
//        double random_blank = Math.random();
//        if(random_blank >= this.field.percent_blank) {
//            return this.name + "= (" + field.getConstantName()+" == None)";
//        }
        
        if(field.getType().equals(Field.DATE) || field.getType().equals(Field.TIME)) {
            Long rand = this.getRandomLongValue();
            Date date = new Date(rand);
            System.out.println("Milliseconds: "+rand);
            System.out.println(date.toGMTString());
            return this.name + "= (" + field.getConstantName()+" == "+ rand+")";
        }
        Double rand = this.getRandomDoubleValue();
        return this.name + "= (" + field.getConstantName()+" == "+ rand+")";
    }
}
