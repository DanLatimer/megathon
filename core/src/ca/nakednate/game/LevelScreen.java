package ca.nakednate.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LevelScreen extends BaseScreen {

    private final String mPlayerName;

    public LevelScreen(final UnfriendlyFire game, String playerName) {
        super(game);
        mPlayerName = playerName;
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();

        Label welcomeLabel = new Label("Suck it, " + mPlayerName + "!", getGame().getSkin());

        table.add(welcomeLabel);
        table.setBounds((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() / 2, 200, 100);

        getStage().addActor(table);
    }
}
