package ca.nakednate.game;

import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.game.models.events.GameRequestEvent;
import ca.nakednate.game.models.events.NewPlayerEvent;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.ClientManager;
import ca.nakednate.game.p2p.MessageHandler;
import ca.nakednate.game.p2p.Peer;
import ca.nakednate.game.p2p.listeners.MainScreenListener;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


public class MainMenuScreen extends BaseScreen implements PeerDiscoveryListener, MainScreenListener {

    private static final String LOG_TAG = MainMenuScreen.class.getSimpleName();
    List<ClientHandler> mClientHandlerListView;
    TextField mDisplayNameTextField;
    GameRequestEvent mSentRequest = null;

    public MainMenuScreen(final UnfriendlyFire game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Label nameLabel = new Label("Name: ", getSkin());
        Label gameListLabel = new Label("Available Games:", getSkin());


        mDisplayNameTextField = new TextField("", getSkin());
        mDisplayNameTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                notifyClientHandlersOfNameChange();
            }
        });

        mClientHandlerListView = new List<ClientHandler>(getSkin());
        setClientHandlers(new java.util.ArrayList<ClientHandler>());
        mClientHandlerListView.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                ClientHandler selectedOpponent = mClientHandlerListView.getSelected();
                GameRequestEvent gameRequestEvent = new GameRequestEvent(null, true);
                selectedOpponent.sendJson(GameRequestEvent.class, gameRequestEvent.toJSON());

                mSentRequest = gameRequestEvent;
                // Set message originator so we remember who we asked
                mSentRequest.setMessageOriginator(selectedOpponent);
            }
        });
        ScrollPane scrollPane = new ScrollPane(mClientHandlerListView, getSkin());

        Table table = new Table(getSkin());
        table.align(Align.top);
        table.setBounds(Gdx.graphics.getWidth() * 0.05f, 10, Gdx.graphics.getWidth() * 0.9f,
                Gdx.graphics.getHeight() * 0.55f);
        table.add(nameLabel);
        table.add(mDisplayNameTextField).width(Gdx.graphics.getWidth() * 0.3f);
        table.row();
        table.add(gameListLabel).colspan(2);
        table.row();
        table.add(scrollPane).colspan(2);

        Image background = new Image(new TextureRegion(new Texture(Gdx.files.internal("skin/title_screen.png"))));
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        getStage().addActor(background);
        getStage().addActor(table);

        MessageHandler.setMainScreenListener(this);
        ClientManager.setMainScreenListener(this);
    }

    @Override
    public void onPeersDiscovered(java.util.List<Peer> peers) {

        for (Peer peer : peers) {

            ClientHandler clientHandler = ClientManager.getOrCreateClientHandler(peer);
            if (clientHandler == null) {
                Gdx.app.error(LOG_TAG, "Unable to establish a socket connection with clientHandler: " + clientHandler);
                return;
            }

            if (clientHandler.getPeer().getDisplayName() == null) {
                handshake(clientHandler);
            }
        }
    }

    /**
     * Sets up a client handler/socket with the peer if we don't have one.
     * Sends them our name
     *
     * @param clientHandler
     */
    private void handshake(ClientHandler clientHandler) {
        sendDisplayNameToClient(clientHandler);
    }

    /**
     * Tell this client of my name
     *
     * @param clientHandler
     */
    private void sendDisplayNameToClient(ClientHandler clientHandler) {
        PlayerInfo playerInfo = new PlayerInfo(mDisplayNameTextField.getText());
        NewPlayerEvent newPlayerEvent = new NewPlayerEvent(null, playerInfo);

        clientHandler.sendJson(NewPlayerEvent.class, newPlayerEvent.toJSON());
    }

    /**
     * Tell all clients about my new name
     */
    private void notifyClientHandlersOfNameChange() {
        for (ClientHandler clientHandler : ClientManager.getClientHandlers()) {
            sendDisplayNameToClient(clientHandler);
        }
    }

    /**
     * Update UI client handler list
     *
     * @param clientHandlerList
     */
    public void setClientHandlers(java.util.List<ClientHandler> clientHandlerList) {
        Array<ClientHandler> array = new Array<ClientHandler>(clientHandlerList.toArray(new ClientHandler[clientHandlerList.size()]));
        mClientHandlerListView.setItems(array);
    }

    @Override
    public void onNewPlayerRecieved(NewPlayerEvent newPlayerEvent) {

        Peer newPeer = newPlayerEvent.getMessageOriginator().getPeer();

        java.util.List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

        for (ClientHandler clientHandler : mClientHandlerListView.getItems()) {
            Peer currentPeer = clientHandler.getPeer();

            if (currentPeer.equals(newPeer)) {
                PlayerInfo playerInfo = newPlayerEvent.getPlayerInfo();
                currentPeer.setDisplayName(playerInfo.getDisplayName());
            }

            clientHandlers.add(clientHandler);
        }

        setClientHandlers(clientHandlers);
    }

    @Override
    public void onNewClientAdded() {
        java.util.List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
        clientHandlers.addAll(ClientManager.getClientHandlers());
        setClientHandlers(clientHandlers);
    }

    @Override
    public void onGameRequestEvent(GameRequestEvent incomingGameRequestEvent) {
        ClientHandler opponent = incomingGameRequestEvent.getMessageOriginator();

        if(mSentRequest != null && mSentRequest.getMessageOriginator() == opponent) {
            if(incomingGameRequestEvent.isAccepted()) {
                startGame();
            }
            mSentRequest = null;
            return;
        }

        // TODO: prompt modal dialog, "Do you want to joing the game with X"

        boolean accepted = true; // TODO: get response from modal dialog
        GameRequestEvent outgoingGameRequestEvent = new GameRequestEvent(null, accepted);

        opponent.sendJson(GameRequestEvent.class, outgoingGameRequestEvent.toJSON());
        startGame();
    }

    public void startGame() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                getGame().setScreen(new VehicleSelectionScreen(getGame()));
            }
        });
    }
}
