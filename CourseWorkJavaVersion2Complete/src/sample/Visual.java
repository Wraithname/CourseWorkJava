package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Visual {
    int cep1,cep2,xnum,ynum,lead;
    double q1num,q2num;

    /**
     * Отрисовка начального графика
     * @param cnv1 - элемент формы Canvas
     * @param error - элемент формы Label
     */
    public  void paint(Canvas cnv1, Label error){
        readfile();
        int x,y;
        int pol = 2*lead+20;
        if(lead>250||lead<50) {
            error.setText("Визуализация с такими данными невозможна");
        } else {
            int n_point = pol / 2;
            int x_lp1 = 10;
            int x_pl2 = pol - 10;
            int y_pl = pol - 10;
            int x_n = n_point;
            int y_n = pol - 10;
            int x_k = x_n + xnum;
            int y_k = y_n - ynum;
            int y_r = y_k + 4;
            x = (int) (x_n + (cep1 * Math.cos(q1num)));
            y = (int) (y_n - (cep1 * Math.sin(q1num)));
            cnv1.setHeight(pol);
            cnv1.setWidth(pol);
            GraphicsContext gc = cnv1.getGraphicsContext2D();
            gc.setLineWidth(2.0);
            gc.setStroke(Color.RED);
            gc.strokeLine(x_lp1, y_pl, x_pl2, y_pl);
            gc.setStroke(Color.BLUE);
            gc.strokeLine(x_n, y_n, x, y);
            gc.strokeLine(x, y, x_k, y_k);
            gc.strokeLine(x_k, y_k, x_k, y_r);
        }
    }
    /**
     * Чтение файла с исходными данными
     * Файл находится в пакете с название "file.txt"
     * В случае его отсутсвтия, пограмма его создает
     */
    public void readfile(){
        String text="";
        try{
            FileInputStream fstream = new FileInputStream("file.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                text=strLine;
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }
        List<String> param=new ArrayList<>();
        for(String txt:text.split(" ")){
            param.add(txt);
        }
        cep1=Integer.parseInt(param.get(0));
        cep2=Integer.parseInt(param.get(1));
        xnum=Integer.parseInt(param.get(2));
        ynum=Integer.parseInt(param.get(3));
        q1num=Double.parseDouble(param.get(5));
        q2num=Double.parseDouble(param.get(6));
        lead=Integer.parseInt(param.get(7));
    }

    /**
     * Отрисовка графика результатов
     * @param rx - координата x
     * @param ry - координата y
     * @param yg - угл поворта
     * @param cnv2 - Элемент формы Canvas
     * @param error - Элемент формы Label
     */
    public  void paint1(int rx,int ry,double yg,Canvas cnv2,Label error){
        readfile();
        double x,y;
        int pol = 2*lead+20;
        if(lead>250||lead<50){
            error.setText("Визуализация с такими данными невозможна");
        } else {
            int n_point = pol / 2;
            int x_lp1 = 10;
            int x_pl2 = pol - 10;
            int y_pl = pol - 10;
            int x_n = n_point;
            int y_n = pol - 10;
            int x_k = x_n + rx;
            int y_k = y_n - ry;
            int y_r = y_k + 4;
            x = (x_n + (cep1 * Math.cos(yg)));
            y = (y_n - (cep1 * Math.sin(yg)));
            GraphicsContext gc = cnv2.getGraphicsContext2D();
            gc.clearRect(0,0,cnv2.getWidth(),cnv2.getHeight());
            cnv2.setHeight(pol);
            cnv2.setWidth(pol);
            gc.setLineWidth(2.0);
            gc.setStroke(Color.BLUE);
            gc.strokeLine(x_lp1, y_pl, x_pl2, y_pl);
            gc.setStroke(Color.RED);
            gc.strokeLine(x_n, y_n, x, y);
            gc.strokeLine(x, y, x_k, y_k);
            gc.strokeLine(x_k, y_k, x_k, y_r);
        }
    }
}
