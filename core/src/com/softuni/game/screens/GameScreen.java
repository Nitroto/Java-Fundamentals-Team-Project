package com.softuni.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.softuni.game.entites.Car;

import static com.badlogic.gdx.Input.Keys;

public class GameScreen implements Screen {

    private final float TIMESTEP = 1 / 60f;
    private final int VELOSITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Car car;

    private Array<Body> bodies = new Array<Body>();

    @Override
    public void show() {

        this.world = new World(new Vector2(0, -9.81f), true);
        this.debugRenderer = new Box2DDebugRenderer();

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);

        //Car
        FixtureDef carFixture = new FixtureDef(), wheelFixture = new FixtureDef();
        carFixture.density = 5f;
        carFixture.friction = .4f;
        carFixture.restitution = .3f;

        wheelFixture.density = carFixture.density * 1.0f;
        wheelFixture.friction = 15;
        wheelFixture.restitution = .4f;

        this.car = new Car(this.world, carFixture, wheelFixture, 0, 3, 4, 1.13f);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Keys.ESCAPE:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
                        break;
                }
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }

        }, this.car));

        //Ground
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{
                        new Vector2(-10000, 0), new Vector2(100, 0)
                }
        );

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;
        groundFixture.friction = 5;
        groundFixture.restitution = 0f;

        this.world.createBody(groundDef).createFixture(groundFixture);

        groundShape.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.world.step(TIMESTEP, VELOSITYITERATIONS, POSITIONITERATIONS);

        this.camera.position.set(this.car.getChassis().getPosition().x, this.car.getChassis().getPosition().y, 0);
        this.camera.update();

        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.world.getBodies(this.bodies);
        for (Body body : this.bodies) {
            // tmp is Sprite for current body
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite tmp = (Sprite) body.getUserData();
                tmp.setPosition((body.getPosition().x - tmp.getWidth()/2), (body.getPosition().y - tmp.getHeight()/2));
                tmp.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                tmp.draw(batch);
            }
        }
        this.batch.end();

        this.debugRenderer.render(this.world, this.camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        this.camera.viewportWidth = width / 20;
        this.camera.viewportHeight = height / 20;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.debugRenderer.dispose();
        this.world.dispose();
    }
}
