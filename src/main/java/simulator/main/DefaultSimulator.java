package simulator.main;

import company.company.Company;
import economy.Economy;
import kapua.gateway.KapuaGatewayClient;
import simulator.generator.CompanyGenerator;
import simulator.simulator.SupplyChainControlSimulator;
import simulator.simulator.Parametrizer;

import java.util.concurrent.TimeUnit;

/**
 * Default simulation. Runs economy, company and delivery transport simulations and sends data to kapua.
 *
 * @author Arthur Deschamps
 * @since 1.0
 */
public class DefaultSimulator {

    public static void main(String[] args) {
        // Create a default parametrizer
        Parametrizer parametrizer = new Parametrizer(100, TimeUnit.MICROSECONDS);
        // Generates an economy with default initial metrics
        Economy economy = new Economy();
        // Generates a random company with random data
        Company company = new CompanyGenerator().generateRandomCompany();

        // Starts the simulation
        new SupplyChainControlSimulator(company,economy,parametrizer).start(false);

        // Starts the communication with kapua
        KapuaGatewayClient kapuaGatewayClient = new KapuaGatewayClient(company, parametrizer);
        kapuaGatewayClient.startCommunications();
    }

}
