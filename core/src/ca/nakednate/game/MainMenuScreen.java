package ca.nakednate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(final ScorchedPlanet game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();

        Label welcomeLabel = new Label("Welcome", getGame().getSkin());

        TextField displayNameTextField = new TextField("", getGame().getSkin());

        table.add(welcomeLabel);
        table.row();
        table.add(displayNameTextField);
        table.setBounds((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() / 2, 200, 100);

        getStage().addActor(table);
    }
}
