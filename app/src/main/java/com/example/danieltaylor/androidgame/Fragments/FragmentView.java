package com.example.danieltaylor.androidgame.Fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Dimension;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.danieltaylor.androidgame.GameElements.Character;

/**
 * View for fragments used on the character selectien screen
 */
public class FragmentView extends SurfaceView{

    private Character character;
    private Paint paint;
    private SurfaceHolder surfaceHolder;


    /**
     *
     * @param context application context
     * @param character the fragments character
     * constructor for the view
     */
    public FragmentView(Context context, Character character) {
        super(context);
        this.character = character;
        paint = new Paint();
        surfaceHolder = getHolder();
    }

    /**
     * Draws character information in the view (speed, attack, defense and appearance)
     */
    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {

            //lock the canvas to draw
            Canvas canvas = surfaceHolder.lockCanvas();

            //setFragmentbackgroundColor
            canvas.drawColor(Color.WHITE);
            int width = FragmentView.this.getWidth();
            int height = FragmentView.this.getHeight();

            //draw character bitmap
            //TODO test display
            canvas.drawBitmap(character.getCharacterSelectionappearance(), width/2
                    ,height/2, paint);

            //provide attack information
            //TODO test display
            paint.setColor(Color.BLACK);
            canvas.drawText("Attack: " + Integer.toString(character.getAttack()), width/2,
                    height/2 + character.getCharacterSelectionappearance().getHeight(),paint);

            paint.setStrokeWidth(5);
            paint.setColor(Color.RED);
            canvas.drawLine(10, height/2 + character.getCharacterSelectionappearance().getHeight() + 10,
                    10 + character.getAttack(), height/2 + character.getCharacterSelectionappearance().getHeight() + 10, paint);


            //provide defense information
            //TODO test display
            paint.setColor(Color.BLACK);
            canvas.drawText("Defense: " + Integer.toString(character.getDefense()), width/2,
                    height/2 + character.getCharacterSelectionappearance().getHeight(),paint);
            paint.setColor(Color.BLUE);
            canvas.drawLine(10, height/2 + character.getCharacterSelectionappearance().getHeight() + 20,
                    10 + character.getDefense(), height/2 + character.getCharacterSelectionappearance().getHeight() + 20, paint);

            //provide speed information
            //TODO test display
            paint.setColor(Color.BLACK);
            canvas.drawText("Speed: " + Integer.toString(character.getSpeed()), width/2,
                    height/2 + character.getCharacterSelectionappearance().getHeight(),paint);
            paint.setColor(Color.GRAY);
            canvas.drawLine(10, height/2 + character.getCharacterSelectionappearance().getHeight() + 30,
                    10 + character.getSpeed(),
                    height/2 + character.getCharacterSelectionappearance().getHeight() + 30, paint);


            //unlock the canvas and display modifications
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

}
