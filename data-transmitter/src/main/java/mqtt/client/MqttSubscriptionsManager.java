package mqtt.client;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import websocket.server.IotDataServer;

/**
 * Handles subscriptions to Kapua topics.
 * @since 1.0
 * @author Arthur Deschamps
 */
public class MqttSubscriptionsManager {

    private String broker;
    private IotDataServer wsServer;

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriptionsManager.class);

    public MqttSubscriptionsManager(final String host, final int port, IotDataServer wsServer) {
        if (wsServer == null)
            throw new IllegalArgumentException("Websocket server can't be null.");
        this.broker = "tcp://"+host+":"+Integer.toString(port);
        this.wsServer = wsServer;
    }

    /**
     * Starts listening the gateway to Kapua and subscribes to every data.
     */
    public void startListening() {
        try {
            final String clientId = "listener";
            IMqttAsyncClient client = new MqttAsyncClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(true);
            connOpts.setMaxInflight(1000);
            connOpts.setKeepAliveInterval(1000000);
            connOpts.setUserName("kapua-sys");
            connOpts.setPassword("kapua-password".toCharArray());
            logger.info("Connecting to broker: "+broker);
            client.setCallback(new MqttListener(client,"supply-chain-control-simulation",
                    "kapua-iot-gateway-simulation-scm","kapua-sys", wsServer));
            client.connect(connOpts);
        } catch(MqttException me) {
            logger.error("Reason: "+me.getReasonCode());
            logger.error("Message: "+me.getMessage());
            logger.error("Location: "+me.getLocalizedMessage());
            logger.error("Cause: "+me.getCause());
            logger.error("Exception: "+me);
            me.printStackTrace();
        }

    }
}
