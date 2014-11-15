package ca.nakednate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(final ScorchedPlanet game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Label welcomeLabel = new Label("Welcome", getSkin());

        Label nameLabel = new Label("Name: ", getSkin());

        TextField displayNameTextField = new TextField("", getSkin());

        Label gameListLabel = new Label("Available Games:", getSkin());

        final List<Object> gameList = new List<Object>(getSkin());
        Object[] games = {
                "Nate",
                "Dan",
                "Tristan",
                "Doagz",
                "Nick"
        };
        gameList.setItems(games);
        gameList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                getGame().setScreen(new LevelScreen(getGame(), gameList.getSelected().toString()));
            }
        });
        ScrollPane scrollPane = new ScrollPane(gameList, getSkin());

        Table table = new Table();
        table.add(welcomeLabel).colspan(2).center();
        table.row();
        table.add(nameLabel);
        table.add(displayNameTextField);
        table.row();
        table.add(gameListLabel).colspan(2).center();
        table.row();
        table.add(scrollPane).colspan(2).center();
        table.setBounds((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() / 2, 200, 200);

        getStage().addActor(table);
    }
}
