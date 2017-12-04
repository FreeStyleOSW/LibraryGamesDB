package app.model;

/**
 * Created by Marcin on 21.09.2017.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private IntegerProperty game_id;
    private StringProperty title;
    private StringProperty developer;
    private DoubleProperty price;

    public Game(StringProperty title, StringProperty developer, DoubleProperty price) {
        this.title = title;
        this.developer = developer;
        this.price = price;
    }

    public Game() {
        this.game_id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.developer = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
    }

    public Game(Game another){
        this.game_id = another.game_idProperty();
        this.title = another.nameProperty();
        this.developer = another.developerProperty();
        this.price = another.priceProperty();
    }

    public int getGame_id() {
        return game_id.get();
    }

    public IntegerProperty game_idProperty() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id.set(game_id);
    }

    public String getName() {
        return title.get();
    }

    public StringProperty nameProperty() {
        return title;
    }

    public void setName(String name) {
        this.title.set(name);
    }

    public String getDeveloper() {
        return developer.get();
    }

    public StringProperty developerProperty() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer.set(developer);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
