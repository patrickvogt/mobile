package net.patrickvogt.frontend.android;

import java.util.Timer;
import java.util.TimerTask;

import net.patrickvogt.snake.backend.Food;
import net.patrickvogt.snake.backend.IPainter;
import net.patrickvogt.snake.backend.Snake;
import net.patrickvogt.snake.backend.SnakeElem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private Activity ctx = null;
	private IPainter painter = null;

	public MainGamePanel(Activity context) {
		super(context);
		
		ctx = context;
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		setWillNotDraw(false);
		
		_timer = new Timer();

		_timer.scheduleAtFixedRate( new TimerTask() {
                public void run() {
                	ctx.runOnUiThread(new Runnable() {
            			
            			@Override
            			public void run() {
            				MainGamePanel.this.invalidate();
            				if (!TestGameOver())
           			        {
            			            s.pullTail();
            			            s.move();
         			        }
            	
            			    if (s.touches(h))
          			        {
            			            h.reset(0, 0, 1920, 1080);
            			            s.grow();
            			    }
            			}
            		});
                }
        }, 0, TIMER_INTERVAL);
		
		s = new Snake(10*SnakeElem.WIDTH+Snake.PADDING, 10*SnakeElem.HEIGHT+Snake.PADDING);
		h = new Food();
		h.reset(0, 0, 1920, 1080);


		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
	
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getY() > getHeight() - 50) {
				((Activity) getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//TODO understand canvas
		//and the modification
		//if(null==painter)
		{
			WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			
			this.painter = new CanvasPainter(canvas, size);
			this.painter.reset();
			h.paint(painter);
			s.paint(painter);
			
		}
	
	}
	
	@Override
	public boolean onKeyDown(final int keyCode, KeyEvent event){
		 boolean handled = false;
		 
		 switch(keyCode){
	        case KeyEvent.KEYCODE_DPAD_DOWN:
	            //You now have the key pressed and the player # that pressed it
	            //doSomethingWithKey();
	        	s.down();
	            handled = true;
	            break;
	        case KeyEvent.KEYCODE_DPAD_UP:
	            //You now have the key pressed and the player # that pressed it
	            //doSomethingWithKey();
	        	s.up();
	            handled = true;
	            break;
	        case KeyEvent.KEYCODE_DPAD_LEFT:
	            //You now have the key pressed and the player # that pressed it
	            //doSomethingWithKey();
	        	s.left();
	            handled = true;
	            break;
	        case KeyEvent.KEYCODE_DPAD_RIGHT:
	            //You now have the key pressed and the player # that pressed it
	            //doSomethingWithKey();
	        	s.right();
	            handled = true;
	            break;
	    }
		 
		 return handled || super.onKeyDown(keyCode, event);
	}
	
	private Timer _timer = null;
	
    /// <summary>
    /// the interval of the ticks
    /// </summary>
	private final int TIMER_INTERVAL = 300;
	
	 /// <summary>
    /// the snake
    /// </summary>
    private Snake s = null;

    /// <summary>
    /// the food for the snake
    /// </summary>
    private Food h = null;

    /// <summary>
    /// the painter /visitor which should draw the element onto the canvas
    /// </summary>
    IPainter _painter = null;
	
    /// <summary>
    /// tests, if the game is over
    /// </summary>
    public Boolean TestGameOver()
    {
        if (s.testGameOverSituation(1920, 1080))
        {
        	Toast.makeText(ctx, "Game Over", Toast.LENGTH_SHORT).show();
        	this._timer.cancel();

            return true;
        }

        return false;
    }

    /// <summary>
    /// resets the game to its initial state
    /// </summary>
    private void reset()
    {
        s.reset();
        h.reset(0, 0, 1920, 1080);
    }
}
