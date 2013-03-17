package com.scarytom.collider.model;

import static com.scarytom.collider.model.Position.at;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public final class Pitch {

//    private static final float UNIT = 6.4008f;
    private static final float UNIT = 2.0f;

    public final float height = 4.0f * UNIT;
    public final float width  = 5.0f * UNIT;
    
    public final List<Hoop> hoops = unmodifiableList(asList(new Hoop(at(1.0f * UNIT, 1.0f * UNIT)),
                                                            new Hoop(at(4.0f * UNIT, 1.0f * UNIT)),
                                                            new Hoop(at(4.0f * UNIT, 3.0f * UNIT)),
                                                            new Hoop(at(1.0f * UNIT, 3.0f * UNIT)),
                                                            new Hoop(at(1.5f * UNIT, 2.0f * UNIT)),
                                                            new Hoop(at(3.5f * UNIT, 2.0f * UNIT))));

    public final Peg peg = new Peg(at(2.5f * UNIT, 2.0f * UNIT));

}
