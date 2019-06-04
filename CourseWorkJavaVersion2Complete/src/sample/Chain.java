package sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**Класс расчёта кинематики и визуализация
 * @author Александр Орлов
 * @version 1.0
 */
public class Chain {
    /**
     * Вводимые параметры
     */
    int cep1,cep2,xnum,ynum,znum,lead;
    double q1num,q2num;

    /**
     * Обратный расчёт кинматики с 3 переменными
     * @param x1 - координата точки на оси x
     * @param y1 - координата точки на оси y
     * @param z1 - координата точки на оси z
     * @return - углы поворота
     */
    public double[] back(double x1,double y1,double z1)
    {
        readfile();
        double[] result=new double[4];
        double u1,u2,u3;
        double B=Math.sqrt(x1*x1+y1*y1);
        u1=q1num-Math.acos((cep1*cep1+cep2*cep2-B*B)/(2*cep1*cep2));
        u2=q2num-(Math.acos((cep1*cep1+B*B-cep2*cep2)/(2*cep1*B))+Math.acos((x1*x1+B*B-y1*y1)/(2*B*x1)));
        double C=Math.sqrt((x1-xnum)*(x1-xnum)+(z1-znum)*(z1-znum));
        u3=Math.acos((xnum*xnum+x1*x1-C*C)/(2*xnum*x1));
        result[0]=u1;
        result[1]=u2;
        result[2]=u3;
        u1=Math.acos((cep1*cep1+cep2*cep2-B*B)/(2*cep1*cep2));
        result[3]=u1;
        return result;
    }

    /**
     * Обратный расчёт кинематики для 2 переменных
     * @param x - координата точки на оси x
     * @param y - координаты точки на оси y
     * @return - углы поворота
     */
    public double[] back(double x,double y)
    {
        readfile();
        double[] result=new double[3];
        double u1,u2;
        double B=Math.sqrt(x*x+y*y);
        u1=q1num-Math.acos((cep1*cep1+cep2*cep2-B*B)/(2*cep1*cep2));
        u2=q2num-(Math.acos((cep1*cep1+B*B-cep2*cep2)/(2*cep1*B))+Math.acos((x*x+B*B-y*y)/(2*B*x)));
        result[0]=u1;
        result[1]=u2;
        u1=Math.acos((cep1*cep1+cep2*cep2-B*B)/(2*cep1*cep2));
        result[2]=u1;
        return result;
    }

    /**
     * Расчет углов вводимых данных
     * @param x - координата точки на оси x
     * @param y - кооридната точки на оси y
     * @param l1 - длина 1 звена
     * @param l2 - длина 2 звена
     * @return - углы исходного положения звена
     */
    public double[] back1(double x,double y,int l1,int l2)
    {
        double[] result=new double[2];
        double u1,u2;
        double B=Math.sqrt(x*x+y*y);
        u1=Math.acos((l1*l1+l2*l2-B*B)/(2*l1*l2));
        u2=Math.acos((x*x+B*B-y*y)/(2*B*x))+Math.acos((l1*l1+B*B-l2*l2)/(2*l1*B));
        result[0]=u1;
        result[1]=u2;
        return result;
    }

    /**
     * Прямой расчет кинематики для 2 переменных
     * @param i0 - угол между платформой и 1 звеном
     * @param i2 - угол между 1 звеном и 2 звеном
     * @return - координаты точки положения
     */
    public int[] straight(double i0,double i2) {
        readfile();
        if (i0 < 0 && i2 < 0) {
            int[] result = new int[2];
            i0 = Math.abs(i0) * (Math.PI / 180);
            i2 = Math.abs(i2) * (Math.PI / 180);
            double rX = cep1 * Math.cos(i0) - cep2 * Math.cos(i2 + i0);
            double rY = cep1 * Math.sin(i0) - cep2 * Math.sin(i2 + i0);
            result[0] = (int) rX;
            result[1] = (int) rY;
            return result;
        } else {
        int[] result = new int[2];
        i0 = i0 * (Math.PI / 180);
        i2 = i2 * (Math.PI / 180);
        double rX = cep1 * Math.cos(i0) + cep2 * Math.cos(i2 - i0);
        double rY = cep1 * Math.sin(i0) + cep2 * Math.sin(i2 - i0);
        result[0] = (int) rX;
        result[1] = (int) rY;
        return result;
        }
    }

    /**
     * Прямой расчет кинематики в пространстве
     * @param i0 - угол между платформой и 1 звеном
     * @param i2 - угол между 1 звеном и 2 звеном
     * @param i3 - угол поворота на платформе
     * @return - координаты точки положения
     */
    public int[] straight(double i0,double i2,double i3)
    {
        readfile();
        int[] result=new int[3];
        i0=i0*(Math.PI/180);
        i2=i2*(Math.PI/180);
        double rX=cep1*Math.cos(i0)+cep2*Math.cos(i2-i0);
        double rY=cep1*Math.sin(i0)+cep2*Math.sin(i2-i0);
        double rZ=Math.sqrt(rX*rX-(rX-xnum)*(rX-xnum));
        result[0]=(int)rX;
        result[1]=(int)rY;
        result[2]=(int)rZ;
        return result;
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
        znum=Integer.parseInt(param.get(4));
        q1num=Double.parseDouble(param.get(5));
        q2num=Double.parseDouble(param.get(6));
        lead=Integer.parseInt(param.get(7));
    }
    /**
     * Проверка достижимости точки
     * Координаты точки не должны превышать суммы длины звеньев
     * @param u - координаты точки положения
     * @return - возвращает true - если проверка успешна или false - если координаты точки недостижимы
     */
    public boolean checkout(int[] u)
    {
        if(u.length==3) {
            if (lead < Math.abs(u[0])) {
                System.out.println("Недостижимая точка X");
                return false;
            } else {
                if (lead < Math.abs(u[1])||u[1]<0) {
                    System.out.println("Недостижимая точка Y");
                    return false;
                } else {
                    if (lead < Math.abs(u[2])) {
                        System.out.println("Недостижимая точка Z");
                        return false;
                    }
                }
            }
        }
        else {
            if (lead < Math.abs(u[0])) {
                System.out.println("Недостижимая точка X");
                return false;
            } else {
                if (lead < Math.abs(u[1])||u[1]<0) {
                    System.out.println("Недостижимая точка Y");
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkoutug(double[] u)
    {
        if(u.length==3) {
            if (Double.isNaN(u[0])) {
                return false;
            } else {
                if (Double.isNaN(u[1])) {
                    return false;
                } else {
                    if (Double.isNaN(u[2])) {
                        return false;
                    }
                }
            }
        }
        else {
            if (Double.isNaN(u[1])) {
                return false;
            } else {
                if (Double.isNaN(u[2])) {
                    return false;
                }
            }
        }
        return true;
    }
}
