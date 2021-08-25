package events;

import events.serverevents.ServerEvent;

public interface EventInterpreter {


    <E extends ServerEvent> void interpretServerEvent(E serverEvent);
}
