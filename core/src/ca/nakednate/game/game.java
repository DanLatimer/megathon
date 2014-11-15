package ca.nakednate.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Game extends ApplicationAdapter implements Disposable {
    private SpriteBatch batch;
    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainSound;

    private OrthographicCamera camera;

    private Rectangle bucket;

    private int width = 1920;
    private int height = 1080;
    private int bucketWidth = 64;
    private int bottomBuffer = 20;

    private Array<Rectangle> raindrops;
    private long lastDropTime;

    @Override
    public void create() {
        batch = new SpriteBatch();

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainSound = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        bucket = new Rectangle();
        bucket.x = width / 2 - bucketWidth / 2;
        bucket.y = bottomBuffer;
        bucket.width = bucketWidth;
        bucket.height = bucketWidth;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        rainSound.setLooping(true);
        rainSound.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();//200 px per second
            if (raindrop.y + bucketWidth < 0) {
                iter.remove();
            }
        }

        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                raindrops.removeIndex(raindrops.indexOf(raindrop, true));
            }
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - bucketWidth / 2;
            bucket.y = touchPos.y - bucketWidth / 2;
        }


        camera.update();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, width - bucketWidth);
        raindrop.y = height;
        raindrop.width = bucketWidth;
        raindrop.height = bucketWidth;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainSound.dispose();
        batch.dispose();
    }
}
