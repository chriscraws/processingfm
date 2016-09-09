package SynthEngine;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 8/27/16.
 */
public class ModularValue {

    private float value;
    private List<Envelope> modulators;

    public static ModularValue value(float value) {
        ModularValue v = new ModularValue();
        v.value = value;
        return v;
    }

    private ModularValue() {
        modulators = new ArrayList<>();
    }

    public ModularValue add(ModularValue add) {
        ModularValue self = this;
        return new ModularValue() {
            @Override
            public float getValue() {
                return self.getValue() + add.getValue();
            }
        };
    }

    public ModularValue scale(ModularValue scale) {
        ModularValue self = this;
        return new ModularValue() {
            @Override
            public float getValue() {
                return self.getValue() * scale.getValue();
            }
        };
    }

    public void addModulator(Envelope modulator) {
        if (!modulators.contains(modulator)) {
            modulators.add(modulator);
        }
    }

    public float getValue() {
        float modSum = (float) modulators.stream().mapToDouble(env -> env.next(0)).sum();
        return value + modSum;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
