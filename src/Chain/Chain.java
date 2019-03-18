package Chain;
import java.util.Scanner;
public class Chain {
    public static void main(String[] args) {
        printMenu();
    }
    public static void printMenu(){
        Scanner in=new Scanner(System.in);
        System.out.println("Выберите способ расчета:");
        System.out.println("Для прямого расчета введите straight");
        System.out.println("Для обратного расчета введите back");
        System.out.println("Для выхода введите exit");
        String menu= in.nextLine().toLowerCase();
        while(menu!="exit"){
        switch (menu){
            case "straight":{
                System.out.println("Введите 1 сторону");
                double L1=in.nextDouble();
                System.out.println("Введите 2 сторону");
                double L2=in.nextDouble();
                System.out.println("Введите 1 угол");
                double Q1=in.nextInt();
                System.out.println("Введите 2 угол");
                double Q2=in.nextInt();
                straight(Q1,Q2,L1,L2);
                break;
            }
            case "back":{
                System.out.println("Введите 1 сторону");
                double L1=in.nextDouble();
                System.out.println("Введите 2 сторону");
                double L2=in.nextDouble();
                System.out.println("Введите координату X");
                double X=in.nextInt();
                System.out.println("Введите координату Y");
                double Y=in.nextInt();
                back(X,Y,L1,L2);
                break;
            }
        }
        }
    }
    public static void back(double X,double Y,double L1, double L2){
double B=Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));
        double Q1=Math.acos(X/B)-Math.acos(L1*L1-L2*L2+B*B/(2*B*L1));
        double Q2=Math.PI-Math.acos(L1*L1+L2*L2+B*B/(2*L2*L1));
        System.out.println("Углы поворота:");
        System.out.println("Поворот 1 угла на "+Q1);
        System.out.println("Поворот 2 угла на "+Q2);
    }
    public static void straight(double Q1,double Q2,double L1, double L2){
        double X=L1*Math.cos(Q1)+L2*Math.cos(Q1+Q2);
        double Y=L1*Math.sin(Q1)+L2*Math.sin(Q1+Q2);
        System.out.println("Координаты точки:"+X+":"+Y);

    }
}
