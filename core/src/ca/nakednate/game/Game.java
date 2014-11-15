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
    private SpriteBatch mBatch;
    private Texture mDropImage;
    private Texture mBucketImage;
    private Sound mDropSound;
    private Music mRainSound;
    private OrthographicCamera mCamera;
    private Rectangle mBucket;
    private Array<Rectangle> mRaindrops;
    private long mLastDropTime;

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int BUCKET_WIDTH = 64;
    private static final int BUCKET_HEIGHT = 64;
    private static final int BOTTOM_BUFFER = 20;

    @Override
    public void create() {
        mBatch = new SpriteBatch();

        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));

        mDropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        mRainSound = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, WIDTH, HEIGHT);

        mBucket = new Rectangle();
        mBucket.x = WIDTH / 2 - BUCKET_WIDTH / 2;
        mBucket.y = BOTTOM_BUFFER;
        mBucket.width = BUCKET_WIDTH;
        mBucket.height = BUCKET_HEIGHT;

        mRaindrops = new Array<Rectangle>();
        spawnRaindrop();

        mRainSound.setLooping(true);
        mRainSound.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.nanoTime() - mLastDropTime > 1000000000) {
            spawnRaindrop();
        }

        Iterator<Rectangle> iter = mRaindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();//200 px per second
            if (raindrop.y + BUCKET_HEIGHT < 0) {
                iter.remove();
            }
        }

        mBatch.begin();
        mBatch.draw(mBucketImage, mBucket.x, mBucket.y);
        for (Rectangle raindrop : mRaindrops) {
            mBatch.draw(mDropImage, raindrop.x, raindrop.y);
            if (raindrop.overlaps(mBucket)) {
                mDropSound.play();
                mRaindrops.removeIndex(mRaindrops.indexOf(raindrop, true));
            }
        }
        mBatch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mCamera.unproject(touchPos);
            mBucket.x = touchPos.x - BUCKET_WIDTH / 2;
            mBucket.y = touchPos.y - BUCKET_HEIGHT / 2;
        }


        mCamera.update();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, WIDTH - BUCKET_WIDTH);
        raindrop.y = HEIGHT;
        raindrop.width = BUCKET_WIDTH;
        raindrop.height = BUCKET_HEIGHT;
        mRaindrops.add(raindrop);
        mLastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        mDropImage.dispose();
        mBucketImage.dispose();
        mDropSound.dispose();
        mRainSound.dispose();
        mBatch.dispose();
    }
}
