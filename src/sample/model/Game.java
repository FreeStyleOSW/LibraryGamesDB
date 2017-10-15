package sample.model;

/**
 * Created by Marcin on 21.09.2017.
 */

import javafx.beans.property.*;

public class Game {
    // Declare Employees Table Columns
    private IntegerProperty game_id;
    private StringProperty name;
    private StringProperty develop;
    private DoubleProperty price;

    public Game(StringProperty name, StringProperty develop, DoubleProperty price) {
        this.name = name;
        this.develop = develop;
        this.price = price;
    }

    public Game() {
        this.game_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.develop = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
    }

    public Game(Game another){
        this.game_id = another.game_idProperty();
        this.name = another.nameProperty();
        this.develop = another.developProperty();
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
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDevelop() {
        return develop.get();
    }

    public StringProperty developProperty() {
        return develop;
    }

    public void setDevelop(String develop) {
        this.develop.set(develop);
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
