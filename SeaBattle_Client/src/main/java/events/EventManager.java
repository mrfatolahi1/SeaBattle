package events;

import events.serverevents.ServerEvent;
import events.clientevents.ClientEvent;

public interface EventManager {
    <E extends ServerEvent> void receiveServerEvent(E serverEvent);

    <E extends ClientEvent> void sendClientEvent(E clientEvent, boolean hasAnswer);
}
