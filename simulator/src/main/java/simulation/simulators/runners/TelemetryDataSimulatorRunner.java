package simulation.simulators.runners;

import company.company.Company;
import simulation.simulators.telemetry.AbstractTelemetryComponentSimulator;
import simulation.simulators.telemetry.DeliveryMovementSimulator;
import simulation.simulators.telemetry.DeliveryStatusSimulator;
import simulation.simulators.telemetry.TransportationHealthStateSimulator;

/**
 * Simulates different events related to telemetry data.
 * @author Arthur Deschamps
 * @since 1.0
 */
public class TelemetryDataSimulatorRunner extends AbstractRunner<AbstractTelemetryComponentSimulator> {

    public TelemetryDataSimulatorRunner(Company company) {
        super(new AbstractTelemetryComponentSimulator[] {
                new DeliveryMovementSimulator(company),
                new DeliveryStatusSimulator(company),
                new TransportationHealthStateSimulator(company)
        });
    }
}
