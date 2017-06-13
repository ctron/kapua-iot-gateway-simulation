package simulator.main;

/**
 * Created by Arthur Deschamps on 02.06.17.
 */
public class Parametrizer {

    // Time flow speediness in seconds per second
    private long timeFlow;

    Parametrizer() {
        // Default timeFlow: 1 sec = 10 sec
        this.timeFlow = 10;
    }

    public Parametrizer(long timeFlow) {
        this.timeFlow = timeFlow;
    }

    public long getTimeFlow() {
        return timeFlow;
    }

    public void setTimeFlow(long timeFlow) {
        this.timeFlow = timeFlow;
    }
}