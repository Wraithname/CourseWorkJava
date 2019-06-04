package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Классы работы со 2 формой
 */
public class Controller1 {
    /**
     * Переменные для хранения вводимых данных
     */
    double cep1,cep2,xnum,ynum,znum,q1num,q2num,lead;
    boolean flag=true;
    /**
     * Объявление класса Chain для использования методов расчетов
     */
    Chain ch=new Chain();
    Visual vs=new Visual();
    /**
     * Элементы 2 формы
     */
    @FXML
    private Canvas cnv1;
    @FXML
    private Canvas cnv2;
    @FXML
    private Label error;
    @FXML
    private AnchorPane panestraiht;
    @FXML
    private AnchorPane paneback;
    @FXML
    private TextField txt3back;

    @FXML
    private TextField resulttxt1back;

    @FXML
    private ComboBox<String> choose;

    @FXML
    private TextField txt2straitht;

    @FXML
    private TextField txt3straitht;

    @FXML
    private TextField resulttxt1staiht;

    @FXML
    private TextField txt1back;

    @FXML
    private TextField resulttxt3back;

    @FXML
    private TextField resulttxt2staiht;

    @FXML
    private TextField txt1straitht;

    @FXML
    private TextField txt2back;

    @FXML
    private TextField resulttxt2back;

    @FXML
    private TextField resulttxt3staiht;

    /**
     * Выполнение действий при отображении компонента ComboBox на форме
     * При отображении компонента происходит считывание исходных данных из файла,
     * для дальнейшего расчета, а так же происходит заполнение компонента данными для их выбора
     * @param event - событие отображения
     * @throws IOException - используется для вывода ошибки при чтении файла
     */
    public void choose_show(Event event) throws IOException {
        String text="";
        try{
            FileInputStream fstream = new FileInputStream("file.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                text=strLine;
            }
        }catch (IOException e){
            error.setText("Ошибка открытия файла");
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
        List<String> list=new ArrayList<>();
        list.add("Прямой расчёт");
        list.add("Обратный расчёт");
        ObservableList<String> observableList = FXCollections.observableList(list);
        choose.setItems(observableList);
        vs.paint(cnv1,error);
    }


    /**
     * Функция нажатия кнопки "Рассчитать"
     * По выбранному методу расчета происходит вызов методов класса Chain для расчета
     * @param actionEvent - событие нажатия кнопки
     */
    public void btn2_Click(ActionEvent actionEvent) {
        error.setText("");
        if(choose.getValue()=="Прямой расчёт"){
            int[] result;
            String txt3=txt3straitht.getText();
            if(txt3.isEmpty()) {
                double u1=0,u2=0;
                try {
                    u1 = Double.parseDouble(txt1straitht.getText());
                    u2 = Double.parseDouble(txt2straitht.getText());
                } catch (Exception ex) {
                    flag=false;
                    error.setText("Заполните хотябы две графы  только числовыми данными");
                }
                if(flag==true) {
                    result = ch.straight(u1, u2);
                    if (ch.checkout(result)) {
                        resulttxt1staiht.setText(String.valueOf(result[0]));
                        resulttxt2staiht.setText(String.valueOf(result[1]));
                        resulttxt3staiht.setText("");
                        u1 = u1 * (Math.PI / 180);
                        vs.paint1(result[0], result[1], u1, cnv2, error);
                    } else {
                        error.setText("Недостижимая точка");
                    }
                }
            }
            else {
                double u1=0,u2=0,u3=0;
                try {
                    u1 = Double.parseDouble(txt1straitht.getText());
                    u2 = Double.parseDouble(txt2straitht.getText());
                    u3 = Double.parseDouble(txt3straitht.getText());
                } catch (Exception ex) {
                    flag=false;
                    error.setText("Заполните хотябы две графы  только числовыми данными");
                }
                if(flag==true) {
                    result = ch.straight(u1, u2, u3);
                    if (ch.checkout(result)) {
                        resulttxt1staiht.setText(String.valueOf(result[0]));
                        resulttxt2staiht.setText(String.valueOf(result[1]));
                        resulttxt3staiht.setText(String.valueOf(result[2]));
                        u1 = u1 * (Math.PI / 180);
                        vs.paint1(result[0], result[1], u1, cnv2, error);
                    } else {
                        error.setText("Недостижимая точка");
                    }
                }
            }
        }
        if(choose.getValue()=="Обратный расчёт"){
            double[] result;
            String txt3=txt3back.getText();
            if(txt3.isEmpty()) {
                int x = 0, y = 0;
                double ugl;
                try {
                    x = Integer.parseInt(txt1back.getText());
                    y = Integer.parseInt(txt2back.getText());
                } catch (Exception ex) {
                    flag = false;
                    error.setText("Заполните хотябы две графы  только числовыми данными");
                }
                if (flag == true) {
                    result = ch.back(x, y);
                    if (ch.checkoutug(result)) {
                        resulttxt1back.setText(String.valueOf(result[0]));
                        resulttxt2back.setText(String.valueOf(result[1]));
                        resulttxt3back.setText("");
                        ugl = result[2];
                        vs.paint1(x, y, ugl, cnv2, error);
                    } else {
                        error.setText("Недостижимая точка");
                    }
                }

                } else {
                    int x = 0, y = 0, z = 0;
                    try {
                        x = Integer.parseInt(txt1back.getText());
                        y = Integer.parseInt(txt2back.getText());
                        z = Integer.parseInt(txt3back.getText());
                    } catch (Exception ex) {
                        flag=false;
                        error.setText("Заполните хотябы две графы  только числовыми данными");
                    }
                    if(flag==true){
                    result = ch.back(x, y, z);
                    if (ch.checkoutug(result)) {
                        resulttxt1back.setText(String.valueOf(result[0]));
                        resulttxt2back.setText(String.valueOf(result[1]));
                        resulttxt3back.setText(String.valueOf(result[2]));
                        double rx;
                        rx = result[3];
                        vs.paint1(x, y, rx, cnv2, error);
                    } else {
                        error.setText("Недостижимая точка");
                    }
                }
            }
        }
    }
    /**
     * Функция выбора метода расчета
     * При выборе метода отображается соответствующее поле для ввода данных и расчета
     * @param actionEvent
     */
    public void choose_click(ActionEvent actionEvent) {
        if(choose.getValue()=="Прямой расчёт"){
            panestraiht.setVisible(true);
            paneback.setVisible(false);
        }
        if(choose.getValue()=="Обратный расчёт"){
            paneback.setVisible(true);
            panestraiht.setVisible(false);
        }
    }
}
