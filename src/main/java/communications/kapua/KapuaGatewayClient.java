package communications.kapua;

import company.company.Company;
import org.eclipse.kapua.gateway.client.Application;
import org.eclipse.kapua.gateway.client.Payload;
import org.eclipse.kapua.gateway.client.mqtt.fuse.FuseClient;
import org.eclipse.kapua.gateway.client.profile.kura.KuraMqttProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.eclipse.kapua.gateway.client.Credentials.userAndPassword;
import static org.eclipse.kapua.gateway.client.Transport.waitForConnection;

/**
 * Interface and communicate with Kapua
 * @author Arthur Deschamps
 * @since 1.0
 */
public class KapuaGatewayClient {

    private Company company;
    private org.eclipse.kapua.gateway.client.Client client;
    private Application application;
    private long communicationsDelay;

    private static final Logger logger = LoggerFactory.getLogger(KapuaGatewayClient.class);

    public KapuaGatewayClient(Company company, long communicationsDelay) {
        this.company = company;
        this.communicationsDelay = communicationsDelay;

        try {
            client = KuraMqttProfile.newProfile(FuseClient.Builder::new)
                    .accountName("kapua-sys")
                    .clientId("supply-chain-control-simulator")
                    .brokerUrl("tcp://localhost:1883")
                    .credentials(userAndPassword("kapua-broker", "kapua-password"))
                    .build();

        } catch (Exception e) {
           logger.error(e.getMessage());
        }

        application = client.buildApplication("Supply Chain Control Simulator").build();

    }

    /**
     * Initialize all subscriptions starts data sending to kapua.
    **/
    public void startCommunication() {
        try {
            // Wait for connection
            waitForConnection(application.transport());
            // Start subscribing to all topics
            startSubscriptions();
            // Start sending data
            Executors.newSingleThreadScheduledExecutor()
                    .scheduleWithFixedDelay(
                            new DataSenderRunner(company,application),
                            0,
                            communicationsDelay,
                            TimeUnit.SECONDS
                    );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Starts all subscriptions.
     * @throws Exception
     */
    private void startSubscriptions() throws Exception {
        //TODO
    }



    /**
     * Handles message reception
     * @param message
     * Message received from Kapua.
     */
    private void subscriptionHandler(Payload message) {
        logger.info("Received: "+message.toString());
    }



}