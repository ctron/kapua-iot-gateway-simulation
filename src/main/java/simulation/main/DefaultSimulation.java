package simulation.main;

import kapua.gateway.KapuaGatewayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulation.simulators.SupplyChainControlSimulator;

/**
 * Default simulation. Runs economy, company and delivery transport simulations and sends data to kapua.
 * @author Arthur Deschamps
 * @since 1.0
 */
public class DefaultSimulation {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(DefaultSimulation.class);

        logger.info("Creating parametrizer... ");
        // Uncomment the two lines below to parametrize the simulation
        // Parametrizer parametrizer = new Parametrizer(timeFlow, dataSendingDelay, displayMetrics, displayMetricsDelay,
        //       companyType, companyName, economy);

        // Comment the line below if you don't want the default parameters for the simulation
        Parametrizer parametrizer = new Parametrizer();

        // Or use the default parametrizer and set the things you want:
        // parametrizer.setDisplayMetrics(false);

        // Starts the simulation
        new SupplyChainControlSimulator(parametrizer).start();

        // Start sending data
        logger.info("Initializing communication with Kapua...");
        new KapuaGatewayClient(parametrizer.getCompany(),parametrizer.getDataSendingDelay()).startCommunication();
    }

}
