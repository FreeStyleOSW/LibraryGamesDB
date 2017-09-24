package sample.model;

/**
 * Created by Marcin on 21.09.2017.
 */

import javafx.beans.property.*;
import java.sql.Date;

public class Game {
    // Declare Employees Table Columns
    private IntegerProperty game_id;
    private StringProperty name;
    private StringProperty develop;
    private SimpleObjectProperty<Date> create_date;
    private DoubleProperty cost;

    public Game() {
        this.game_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.develop = new SimpleStringProperty();
        this.create_date = new SimpleObjectProperty<>();
        this.cost = new SimpleDoubleProperty();
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

    public Date getCreate_date() {
        return create_date.get();
    }

    public SimpleObjectProperty<Date> create_dateProperty() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date.set(create_date);
    }

    public double getCost() {
        return cost.get();
    }

    public DoubleProperty costProperty() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }
}
