package er301;

import java.util.EventListener;

public interface rfidListener extends EventListener {

    public abstract void onRfidDetect(changeEvent ev);
}
