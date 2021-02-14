package com.example.checkroot
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
class DragView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    //原始位置
    private val startPoint = PointF()
    //原始半径
    private var startPRadius  = 200F
    //拖拽点位置
    private val endPoint = PointF()
    //拖拽点半径
    private var endRadius = 200F
    //最大长度
    private val MAX = 1000
    //整个团的Path
    private val path = Path()
    //曲线端点1
    private val pathPoint1 = PointF()
    //曲线端点2
    private val pathPoint2 = PointF()
    //曲线端点3
    private val pathPoint3 = PointF()
    //曲线端点4
    private val pathPoint4 = PointF()
    //控制点
    private val controlPoint = PointF()
    private val paint:Paint = Paint().apply { color = Color.BLUE }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_MOVE ->{
                 endPoint.x = event.x
                 endPoint.y = event.y
                 postInvalidate()
            }
            MotionEvent.ACTION_DOWN ->{
                startPoint.x = event.x
                startPoint.y = event.y
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //计算结束点圆圈大小动态变化
        val distance = Math.sqrt((((startPoint.x - endPoint.x) * (startPoint.x - endPoint.x) + (startPoint.y - endPoint.y) * (startPoint.y - endPoint.y)).toDouble())).toFloat()
        startPRadius = endRadius - distance/MAX * endRadius
        if(startPRadius < 0){
            startPRadius = 0f
        }
        if(startPoint.x == 0f || startPoint.y == 0f){
            return
        }
        if(startPRadius == 0f){
            canvas?.drawCircle(endPoint.x,endPoint.y,endRadius,paint)
            return
        }
        //计算四个控制点 用来绘制贝塞尔曲线
        path.reset()
        var dx: Float = startPoint.x - endPoint.x
        val dy: Float = startPoint.y - endPoint.y
        if (dx == 0f) {
            dx = 0.001f
        }
        val tan = dy / dx
        val arc = Math.atan(tan.toDouble()).toFloat()
        pathPoint1.x = (startPoint.x + startPRadius * Math.sin(arc.toDouble())).toFloat()
        pathPoint1.y = (startPoint.y - startPRadius * Math.cos(arc.toDouble())).toFloat()
        pathPoint2.x = (endPoint.x + endRadius * Math.sin(arc.toDouble())).toFloat()
        pathPoint2.y = (endPoint.y - endRadius * Math.cos(arc.toDouble())).toFloat()
        pathPoint3.x = (endPoint.x - endRadius * Math.sin(arc.toDouble())).toFloat()
        pathPoint3.y = (endPoint.y + endRadius * Math.cos(arc.toDouble())).toFloat()
        pathPoint4.x = (startPoint.x - startPRadius * Math.sin(arc.toDouble())).toFloat()
        pathPoint4.y = (startPoint.y + startPRadius * Math.cos(arc.toDouble())).toFloat()
        // 求控制点 两个点的中心位置作为控制点
        controlPoint.x = (startPoint.x+endPoint.x)/2
        controlPoint.y = (startPoint.y+endPoint.y)/2
        //贝塞尔曲线路径
        path.moveTo(pathPoint1.x, pathPoint1.y)
        path.quadTo(controlPoint.x, controlPoint.y, pathPoint2.x, pathPoint2.y)
        path.lineTo(pathPoint3.x, pathPoint3.y)
        path.quadTo(controlPoint.x, controlPoint.y, pathPoint4.x, pathPoint4.y)
        path.close()
        canvas?.drawCircle(startPoint.x,startPoint.y,startPRadius,paint)
        canvas?.drawCircle(endPoint.x,endPoint.y,endRadius,paint)
        canvas?.drawPath(path,paint)
    }

    private fun drawTestPoint(canvas: Canvas?){
        /**
         *
         * Drawing auxiliary lines
         */
        //绘制控制点
        val paint = Paint().apply {
            color = Color.RED
            strokeWidth = 20f
        }
        canvas?.drawCircle(startPoint.x,startPoint.y,10f,paint)
        canvas?.drawCircle(endPoint.x,endPoint.y,10f,paint)
        canvas?.drawCircle(pathPoint1.x,pathPoint1.y,10f,paint)
        canvas?.drawCircle(pathPoint2.x,pathPoint2.y,10f,paint)
        canvas?.drawCircle(pathPoint3.x,pathPoint3.y,10f,paint)
        canvas?.drawCircle(pathPoint4.x,pathPoint4.y,10f,paint)
        canvas?.drawCircle(controlPoint.x,controlPoint.y,10f,paint)
        //分析线
        val paint1 = Paint().apply {
            color = Color.GRAY
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }

        //左上控制点的竖直辅助线
        canvas?.drawPath(Path().apply {
            moveTo(pathPoint1.x,0f)
            lineTo(pathPoint1.x,height.toFloat())
        },paint1)

        canvas?.drawPath(Path().apply {
            moveTo(0f,startPoint.y)
            lineTo(width.toFloat(),startPoint.y)
        },paint1)

        //左圆心的竖直辅助线
        canvas?.drawPath(Path().apply {
            moveTo(pathPoint1.x,0f)
            lineTo(pathPoint1.x,height.toFloat())
        },paint1)

        canvas?.drawPath(Path().apply {
            moveTo(startPoint.x,0f)
            lineTo(startPoint.x,height.toFloat())
        },paint1)
//        //三角分析线
        val paint2 = Paint().apply {
            color = Color.GREEN
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }
        //三角形计算夹角
        canvas?.drawPath(Path().apply {
            moveTo(startPoint.x,startPoint.y)
            lineTo(endPoint.x,endPoint.y)
            lineTo(endPoint.x,startPoint.y)
            close()
        },paint2)
    }
}