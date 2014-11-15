package ca.nakednate.game;

import ca.nakednate.game.models.PlayerInfo;
import ca.nakednate.game.models.events.NewPlayerEvent;
import ca.nakednate.game.p2p.ClientHandler;
import ca.nakednate.game.p2p.ClientManager;
import ca.nakednate.game.p2p.MessageHandler;
import ca.nakednate.game.p2p.Peer;
import ca.nakednate.game.p2p.listeners.MainScreenListener;
import ca.nakednate.game.p2p.listeners.PeerDiscoveryListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


public class MainMenuScreen extends BaseScreen implements PeerDiscoveryListener, MainScreenListener {

    private static final String LOG_TAG = MainMenuScreen.class.getSimpleName();
    List<ClientHandler> mClientHandlerListView;
    TextField mDisplayNameTextField;

    public MainMenuScreen(final UnfriendlyFire game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Label welcomeLabel = new Label("Welcome", getSkin());
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
                getGame().setScreen(new VehicleSelectionScreen(getGame()));
            }
        });
        ScrollPane scrollPane = new ScrollPane(mClientHandlerListView, getSkin());

        Table table = new Table();
        table.add(welcomeLabel).colspan(2).center();
        table.row();
        table.add(nameLabel);
        table.add(mDisplayNameTextField);
        table.row();
        table.add(gameListLabel).colspan(2).center();
        table.row();
        table.add(scrollPane).colspan(2).center();
        table.setBounds((Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() / 2, 200, 200);

        getStage().addActor(table);

        MessageHandler.setMainScreenListener(this);
        ClientManager.setMainScreenListener(this);
    }

    @Override
    public void onPeersDiscovered(java.util.List<Peer> peers) {

        for(Peer peer : peers) {

            ClientHandler clientHandler = ClientManager.getOrCreateClientHandler(peer);
            if (clientHandler == null) {
                Gdx.app.error(LOG_TAG, "Unable to establish a socket connection with clientHandler: " + clientHandler);
                return;
            }

            if(clientHandler.getPeer().getDisplayName() == null) {
                handshake(clientHandler);
            }
        }
    }

    /**
     * Sets up a client handler/socket with the peer if we don't have one.
     * Sends them our name
     *
     * @param peer
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
}
