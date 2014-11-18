package ca.nakednate.game.models.events;

public class GameRequestEvent extends BaseEvent {

    private boolean accepted;

    public GameRequestEvent(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
