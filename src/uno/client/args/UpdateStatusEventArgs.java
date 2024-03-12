package uno.client.args;

import events.EventArgs;
import uno.common.cards.Card;

public class UpdateStatusEventArgs extends EventArgs {
    public record PlayerCardCount(int id, int count){}

    private final Card lastCard;
    private final Card[] playerCards;
    private final PlayerCardCount[] otherPlayerCardCounts;

    public UpdateStatusEventArgs(
            Card lastCard,
            Card[] playerCards,
            PlayerCardCount[] otherPlayerCardCounts
    ) {
        this.lastCard = lastCard;
        this.playerCards = playerCards;
        this.otherPlayerCardCounts = otherPlayerCardCounts;
    }

    public Card getLastCard() {
        return lastCard;
    }

    public Card[] getPlayerCards() {
        return playerCards;
    }

    public PlayerCardCount[] getOtherPlayerCardCounts() {
        return otherPlayerCardCounts;
    }
}
