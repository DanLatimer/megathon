package ca.nakednate.game;

import ca.nakednate.game.p2p.Peer;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


public class MainMenuScreen extends BaseScreen implements PeerDiscoveryListener {

    List<Peer> mPeerListView;

    public MainMenuScreen(final ScorchedPlanet game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Label welcomeLabel = new Label("Welcome", getSkin());
        Label nameLabel = new Label("Name: ", getSkin());
        Label gameListLabel = new Label("Available Games:", getSkin());

        TextField displayNameTextField = new TextField("", getSkin());

        mPeerListView = new List<Peer>(getSkin());
        setPeers(new java.util.ArrayList<Peer>());
        mPeerListView.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                getGame().setScreen(new LevelScreen(getGame(), mPeerListView.getSelected().toString()));
            }
        });
        ScrollPane scrollPane = new ScrollPane(mPeerListView, getSkin());

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

    @Override
    public void onPeersDiscovered(java.util.List<Peer> peers) {
        setPeers(peers);
    }

    public void setPeers(java.util.List<Peer> peerList) {

        Array<Peer> array = new Array<Peer>(peerList.toArray(new Peer[peerList.size()]));
        mPeerListView.setItems(array);
    }
}
