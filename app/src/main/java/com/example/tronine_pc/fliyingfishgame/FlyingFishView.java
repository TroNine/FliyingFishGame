package com.example.tronine_pc.fliyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View
{
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishspeed;

    private int canvasWidth, canvasHight;

    private int yellowX, yellowY, yellowSpeed = 35;
    private Paint yellowPaint = new Paint();

    private int yellowX2, yellowY2, yellowSpeed2 = 25;

    private int greenX, greenY, greenSpeed = 30;
    private  Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 40;
    private  Paint redPaint = new Paint();

    private int heartX, heartY, heartSpeed = 20;

    private int lifeCounterOfFish;
    private int score;

    private boolean touch = false;

    private Bitmap blackgroudImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[3];

    public FlyingFishView(Context context)
    {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        blackgroudImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        life[2] = Bitmap.createScaledBitmap(life[0],
                (int) (50),
                (int) (50),
                true);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHight = canvas.getHeight();

        canvas.drawBitmap(blackgroudImage,0,0,null);


        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHight - fish[0].getHeight() * 2;
        fishY = fishY + fishspeed;

        canvas.drawLine(0,
                maxFishY+ fish[0].getHeight(),
                 canvasWidth,
                maxFishY+fish[0].getHeight(),
                 redPaint);

        if(fishY < minFishY)
        {
            fishY = minFishY;
        }
        if(fishY > maxFishY)
        {
            fishY = maxFishY;
            Intent gameOverIntent = new Intent(getContext(), GameOverActicity.class);
            gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            gameOverIntent.putExtra("score",score);
            getContext().startActivity(gameOverIntent);
        }
        fishspeed = fishspeed + 2;


        if (touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }

        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY- minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        yellowX2 = yellowX2 - yellowSpeed2;

        if (hitBallChecker(yellowX2, yellowY2))
        {
            score = score + 10;
            yellowX2 = -100;
        }

        if (yellowX2 < 0)
        {
            yellowX2 = canvasWidth + 21;
            yellowY2 = (int) Math.floor(Math.random() * (maxFishY- minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX2, yellowY2, 25, yellowPaint);



        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX = -100;
        }

        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY- minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);

        if (lifeCounterOfFish <= 1) {
            heartX = heartX - heartSpeed;

            if (hitBallChecker(heartX, heartY)) {
                heartX = -100;
                if (lifeCounterOfFish < 3) {
                    lifeCounterOfFish++;
                }
            }

            if (heartX < 0) {
                heartX = canvasWidth + 21;
                heartY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
            }
            if (1 == (int) Math.floor(Math.random() * 10)) {
                canvas.drawBitmap(life[2], heartX, heartY, null);
            }
        }


        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfFish--;
            if(lifeCounterOfFish == 0)
            {

                Intent gameOverIntent = new Intent(getContext(), GameOverActicity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score",score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0)
        {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY- minFishY)) + minFishY;
        }
        if (!(redY == fishY + fish[0].getHeight()))
        {
            canvas.drawCircle(redX, redY, 25, redPaint);
        }


        canvas.drawText("Score : " + score, 20 , 60, scorePaint);

        for(int i = 0; i < 3; i++)
        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitBallChecker(int x , int y)
    {
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;

            fishspeed = -30;
        }
        return true;
    }
}