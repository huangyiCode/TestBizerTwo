package com.example.checkroot
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
class CircleView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private val radius  = 500F

    private val pointF = PointF()

    var pice = 40

    var piceRad = 360/pice

    var path = Path()
    var pathAll = Path()

    var colors = listOf(Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.DKGRAY)


    private val paint:Paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        this.strokeWidth = 10f
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pointF.x = width/2f
        pointF.y = height/2f
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for(index in 1..pice){
            pathAll.reset()
            pathAll.moveTo(pointF.x,pointF.y)
                pathAll.quadTo((pointF.x+Math.sin(Math.toRadians(piceRad*index.toDouble()))*radius/2).toFloat()
                    ,(pointF.y-Math.cos(Math.toRadians(piceRad*index.toDouble()))*radius/2).toFloat(),
                    (pointF.x+Math.sin(Math.toRadians(piceRad*(index+1).toDouble()))*radius).toFloat(),
                    (pointF.y  - Math.cos(Math.toRadians(piceRad*(index+1).toDouble()))*radius).toFloat())
                canvas?.drawPath(pathAll,paint.apply { color = colors[index%colors.size] })
        }
    }

}