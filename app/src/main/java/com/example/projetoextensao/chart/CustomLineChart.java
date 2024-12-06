package com.example.projetoextensao.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomLineChart extends View {

    private Paint linePaint, pointPaint, textPaint, gridPaint;
    private List<Float> dataPoints; // lista de pontos no eixo Y
    private List<String> labels;   // lista de rotulos no eixo X

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // linha
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        // pontos
        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);

        // texto
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);

        // grade
        gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(2f);

        // dados
        dataPoints = new ArrayList<>();
        labels = new ArrayList<>();
    }

    public void setData(List<Float> dataPoints, List<String> labels) {
        this.dataPoints = dataPoints;
        this.labels = labels;
        invalidate(); // Redesenha o gráfico
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataPoints == null || dataPoints.isEmpty() || labels == null || labels.size() != dataPoints.size()) {
            return; // nada pra desenhar
        }

        int width = getWidth();
        int height = getHeight();

        int padding = 50;
        int graphWidth = width - (2 * padding);
        int graphHeight = height - (2 * padding);

        // determina escalas
        float maxData = getMax(dataPoints);
        float xInterval = graphWidth / (float) (dataPoints.size() - 1);
        float yScale = graphHeight / maxData;

        // desenha grade horizontal
        for (int i = 0; i <= 5; i++) {
            float y = padding + i * (graphHeight / 5f);
            canvas.drawLine(padding, y, padding + graphWidth, y, gridPaint);
            canvas.drawText(String.format("%.1f", maxData - (i * maxData / 5)), 10, y + 10, textPaint);
        }

        // desenha grade vertical
        for (int i = 0; i < dataPoints.size(); i++) {
            float x = padding + i * xInterval;
            canvas.drawLine(x, padding, x, padding + graphHeight, gridPaint);
            if (i < labels.size()) {
                canvas.drawText(labels.get(i), x - 20, padding + graphHeight + 30, textPaint);
            }
        }

        // desenha linhas dos dados
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            float x1 = padding + i * xInterval;
            float y1 = padding + graphHeight - (dataPoints.get(i) * yScale);
            float x2 = padding + (i + 1) * xInterval;
            float y2 = padding + graphHeight - (dataPoints.get(i + 1) * yScale);

            canvas.drawLine(x1, y1, x2, y2, linePaint);
        }

        // desenha pontos dos dados
        for (int i = 0; i < dataPoints.size(); i++) {
            float x = padding + i * xInterval;
            float y = padding + graphHeight - (dataPoints.get(i) * yScale);
            canvas.drawCircle(x, y, 8f, pointPaint);
        }
    }

    private float getMax(List<Float> data) {
        float max = Float.MIN_VALUE;
        for (float value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
