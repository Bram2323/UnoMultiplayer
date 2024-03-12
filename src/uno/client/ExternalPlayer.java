package uno.client;

public class ExternalPlayer extends Player {
    private int count;

    public ExternalPlayer(int id, String name) {
        super(id, name);
        count = 0;
    }

    public void setCardCount(int count) {
        this.count = count;
    }


    @Override
    public void display() {
        System.out.printf("%s(%d): %d cards\n", getName(), getId(), count);
    }
}
