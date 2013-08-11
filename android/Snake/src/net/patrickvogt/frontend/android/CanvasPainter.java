package net.patrickvogt.frontend.android;

import net.patrickvogt.snake.backend.Barrier;
import net.patrickvogt.snake.backend.FigureCollection;
import net.patrickvogt.snake.backend.Food;
import net.patrickvogt.snake.backend.IPainter;
import net.patrickvogt.snake.backend.Rectangle;
import net.patrickvogt.snake.backend.Snake;
import net.patrickvogt.snake.backend.SnakeElem;
import net.patrickvogt.snake.backend.SnakeHead;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class CanvasPainter implements IPainter {
	
	private Canvas canvas = null;
	private Paint snakeHeadPainter = null;
	private Paint snakeElemPainter = null;
	private Paint foodPainter = null;
	private Paint backPaint = null;
	private Point gameDim = null;

	public CanvasPainter(Canvas c, Point size)
	{
		this.canvas = c;
		
		this.gameDim = size;
		
		this.snakeHeadPainter = new Paint();
		this.snakeHeadPainter.setColor(Color.GREEN);
		
		this.snakeElemPainter = new Paint();
		this.snakeElemPainter.setColor(Color.WHITE);
		
		this.foodPainter = new Paint();
		this.foodPainter.setColor(Color.RED);
		
		backPaint = new Paint();
		backPaint.setColor(Color.BLUE);
	}
	
	private void drawRectangle(Rectangle r, Paint p)
	{
		this.canvas.drawRect(r.getFoot().getX(), r.getFoot().getY(),
				r.getFoot().getX()+r.getDimension().getX(), r.getFoot().getY()+r.getDimension().getY(), p);
	}

	@Override
	public void paint(Barrier b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(FigureCollection fc) {
		 for (int i = 0; i < fc.size(); i++)
	        {
	            fc.elementAt(i).paint(this);
	        }
	}

	@Override
	public void paint(Food f) {
		this.drawRectangle(f, this.foodPainter);
	}

	@Override
	public void paint(Rectangle r) {
		this.drawRectangle(r, this.snakeElemPainter);
	}

	@Override
	public void paint(Snake s) {
		this.paint((FigureCollection)s);
	}

	@Override
	public void paint(SnakeElem e) {
		this.paint((Rectangle)e);
	}

	@Override
	public void paint(SnakeHead h) {
		this.drawRectangle(h, this.snakeHeadPainter);
		
	}

	@Override
	public void reset() {
		canvas.drawRect(0, 0, gameDim.x, gameDim.y, backPaint);
	}
}
