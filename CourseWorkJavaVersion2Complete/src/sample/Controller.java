package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Классы работы с 1 формой
 */
public class Controller {
    /**
     * Элементы 1 формы
     */
    @FXML
    private TextField l1;
    @FXML
    private Label error;
    
    @FXML
    private TextField l2;

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private TextField z;
    /**
     * Переменные для хранения вводимых данных
     */
    public int cep1, cep2, xnum, ynum, znum, lead;
    public double q1num, q2num;
    boolean flag =true;
    Chain ch = new Chain();
    /**
     * Функция по нажатию кнопки "Ввод"
     * Происходит считывание всех вводимых данных, их проверка на достижимость
     * и запись в файл для переноса между формами
     * @param actionEvent - событие нажатия кнопки
     */
    public void btn1_Click(ActionEvent actionEvent) {
        error.setText("");
        try {
            cep1 = Integer.parseInt(l1.getText());
            cep2 = Integer.parseInt(l2.getText());
            xnum = Integer.parseInt(x.getText());
            ynum = Integer.parseInt(y.getText());
            znum = Integer.parseInt(z.getText());
        } catch (Exception ex) {
            flag = false;
            error.setText("Заполните все графы только числовыми данными");
        }
        if (flag == true) {
            double[] result = ch.back1(xnum, ynum, cep1, cep2);
            q1num = result[0];
            q2num = result[1];
            lead = cep1 + cep2;
            if (lead < xnum) {
                error.setText("Недостижимая точка X");
            } else {
                if (lead < ynum) {
                    error.setText("Недостижимая точка Y");
                } else {
                    if (lead < znum) {
                        error.setText("Недостижимая точка Z");
                    } else {
                        String text = cep1 + " " + cep2 + " " + xnum + " " + ynum + " " + znum + " " + q1num + " " + q2num + " " + lead;
                        File file = new File("file.txt");
                        try {
                            file.createNewFile();
                        } catch (IOException ex) {
                            error.setText(ex.getMessage());
                        }
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            byte[] buffer = text.getBytes();
                            fos.write(buffer, 0, buffer.length);
                        } catch (IOException ex) {

                            error.setText(ex.getMessage());
                        }
                    }
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample1.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception e) {
                        error.setText("Невозможно запустить форму");
                    }
                }
            }
        }
    }
    }

