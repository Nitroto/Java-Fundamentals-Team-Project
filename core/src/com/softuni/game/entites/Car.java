package com.softuni.game.entites;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

import static com.badlogic.gdx.Input.Keys;

public class Car extends InputAdapter {

    private Body chassis, leftWheel, rightWheel;
    private WheelJoint leftAxis, rightAxis;
    private float motorSpeed = 75;

    private TextureAtlas carAtlas;
    private Sprite carSprite;
    private Sprite leftWheelSprite, rightWheelSprite;

    public Car(World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        // chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.set(new float[]{-width / 2, -height / 2, width / 2, -height / 2, width / 2, height / 2, -width / 2, height / 2});

        chassisFixtureDef.shape = chassisShape;

        this.chassis = world.createBody(bodyDef);
        this.chassis.createFixture(chassisFixtureDef);


        // left wheel
        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height / 3.4f);

        wheelFixtureDef.shape = wheelShape;

        this.leftWheel = world.createBody(bodyDef);
        this.leftWheel.createFixture(wheelFixtureDef);

        // right wheel
        this.rightWheel = world.createBody(bodyDef);
        this.rightWheel.createFixture(wheelFixtureDef);

        this.carAtlas = new TextureAtlas("animations/car/car.pack");
        this.carSprite = this.carAtlas.createSprite("chassis");
        this.carSprite.setSize(width, height);
        this.carSprite.setOrigin(this.chassis.getLocalCenter().x + this.carSprite.getWidth() / 2, this.chassis.getLocalCenter().y + this.carSprite.getHeight() / 2);
        this.chassis.setUserData(this.carSprite);

        this.leftWheelSprite = this.carAtlas.createSprite("wheel-0001");
        this.leftWheelSprite.setSize(wheelShape.getRadius() * 2, wheelShape.getRadius() * 2);
        this.leftWheelSprite.setOrigin(this.leftWheel.getPosition().x + wheelShape.getRadius(), this.leftWheel.getLocalCenter().y + wheelShape.getRadius());
        this.leftWheel.setUserData(this.leftWheelSprite);

        this.rightWheelSprite = this.carAtlas.createSprite("wheel-0002");
        this.rightWheelSprite.setSize(wheelShape.getRadius() * 2, wheelShape.getRadius() * 2);
        this.rightWheelSprite.setOrigin(this.rightWheel.getLocalCenter().x + wheelShape.getRadius(), this.rightWheel.getLocalCenter().y + wheelShape.getRadius());
        this.rightWheel.setUserData(rightWheelSprite);


        // left axis
        WheelJointDef axisDef = new WheelJointDef();
        axisDef.bodyA = chassis;
        axisDef.bodyB = leftWheel;
        axisDef.localAnchorA.set(-width / 2 * .815f + wheelShape.getRadius(), -height / 2 * .75f);
        axisDef.frequencyHz = chassisFixtureDef.density;
        axisDef.localAxisA.set(Vector2.Y);
        axisDef.maxMotorTorque = chassisFixtureDef.density * 10;
        this.leftAxis = (WheelJoint) world.createJoint(axisDef);

        // right axis
        axisDef.bodyB = rightWheel;
        axisDef.localAnchorA.set(-width / 2 * .715f + wheelShape.getRadius(), -height / 2 * .75f);
        axisDef.localAnchorA.x *= -1;
        this.rightAxis = (WheelJoint) world.createJoint(axisDef);


    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                this.leftAxis.enableMotor(true);
                this.leftAxis.setMotorSpeed(-this.motorSpeed);
                break;
            case Keys.S:
                this.leftAxis.enableMotor(true);
                this.leftAxis.setMotorSpeed(this.motorSpeed);
                break;
            case Keys.SPACE:
                this.leftAxis.enableMotor(true);
                this.leftAxis.setMotorSpeed(0);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.S:
            case Keys.SPACE:
                this.leftAxis.enableMotor(false);
        }
        return true;
    }

    public Body getChassis() {
        return this.chassis;
    }

}
