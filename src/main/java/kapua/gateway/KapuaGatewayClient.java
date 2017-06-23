package kapua.gateway;

import company.company.Company;
import company.delivery.Delivery;
import org.eclipse.kapua.gateway.client.Application;
import org.eclipse.kapua.gateway.client.Payload;
import org.eclipse.kapua.gateway.client.Topic;
import org.eclipse.kapua.gateway.client.mqtt.fuse.FuseClient;
import org.eclipse.kapua.gateway.client.profile.kura.KuraMqttProfile;
import simulator.simulator.Parametrizer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.eclipse.kapua.gateway.client.Credentials.userAndPassword;
import static org.eclipse.kapua.gateway.client.Transport.waitForConnection;

/**
 * Interface and communicate with Kapua
 * @author Arthur Deschamps
 * @since 1.0
 */
public class KapuaGatewayClient {

    private Company company;
    private Parametrizer parametrizer;
    private org.eclipse.kapua.gateway.client.Client client;
    private Application application;

    private static final Logger logger = Logger.getLogger(KapuaGatewayClient.class.getName());

    public KapuaGatewayClient(Company company, Parametrizer parametrizer) {
        this.company = company;
        this.parametrizer = parametrizer;

        try {
            client = KuraMqttProfile.newProfile(FuseClient.Builder::new)
                    .accountName("kapua-sys")
                    .clientId("supply-chain-control-simulator")
                    .brokerUrl("tcp://localhost:1883")
                    .credentials(userAndPassword("kapua-broker", "kapua-password"))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        application = client.buildApplication("Supply Chain Control Simulator").build();

    }

    /**
     * Initialize all publications and subscriptions. Every data created in the simulation will be transferred to Kapua.
     */
    public void startCommunications() {
        try {
            // Wait for connection
            waitForConnection(application.transport());

            startSubscriptions();

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
            executorService.scheduleWithFixedDelay(this::updateDeliveries,0,
                    2, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts all subscriptions.
     * @throws Exception
     */
    private void startSubscriptions() throws Exception {
        application.data(Topic.of("company","deliveries")).subscribe(this::subscriptionHandler);
    }

    /**
     * Sends all new or updated delivery data to Kapua
     */
    private void updateDeliveries() {
        final Payload.Builder payload = new Payload.Builder();

        // TODO: split json in multiple attributes
        for (final Delivery delivery : company.getDeliveries())
                payload.put(delivery.getId(),delivery.toJson());

        // Sends everything
        try {
            application.data(Topic.of("company","deliveries")).send(payload);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
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