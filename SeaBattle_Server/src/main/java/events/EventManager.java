package events;

import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;

public interface EventManager {
    public <E extends ServerEvent> void sendClientEvent(E serverEvent);

    public <E extends ClientEvent> void receiveServerEvent(E clientEvent);
}
