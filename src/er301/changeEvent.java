package er301;

import java.util.EventObject;

public class changeEvent extends EventObject {

    ER301 er301;

    public changeEvent(Object source, ER301 er301) {
        super(source);
        this.er301 = er301;
    }
}
