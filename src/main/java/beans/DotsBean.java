package beans;

import lombok.Data;
import model.Dot;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tools.BaseDataSaver.save;
import static tools.ErrorPage.goToErrorPage;

@ManagedBean //our bean
@SessionScoped // (not view) use session
@Data // not to write getters and setters
public class DotsBean {
    private Dot newDot = new Dot();
    private String lastR = null;
    private String errorMessage;
    private String coordX;
    private String coordY;
    private String coordR;
    private String isFromGraphic="0";

    private List<Dot> dots = new ArrayList<Dot>();


    //When we send form from graphic (SVG)
    public void drawDot() throws SQLException, ClassNotFoundException {
        newDot.setXYR(coordX,coordY,coordR);
        checkResult(Integer.parseInt(coordX), Double.parseDouble(coordY), Integer.parseInt(coordR));
        dots.add(newDot);
        save(Integer.parseInt(coordX), Double.parseDouble(coordY), Integer.parseInt(coordR), newDot.getResult());
        newDot = new Dot();
    }


    //when we send form using submit button
    public void checkData() throws IOException, InterruptedException {
//        System.out.println("vrode 0 ? "+isFromGraphic);
        try {
            int x = Integer.parseInt(newDot.getX());
            double y = Double.parseDouble(newDot.getY().replace(',','.'));
            int r = Integer.parseInt(lastR);
            checkParameters();
        }
        catch (Exception e){
            errorMessage = "X, Y, R must be numbers!";
            clearAllFields();
            goToErrorPage(errorMessage);
        }

    }

    public void addDot() throws SQLException, ClassNotFoundException {
        newDot.setR(lastR);
        dots.add(newDot);
        save(Integer.parseInt(newDot.getX()), Double.parseDouble(newDot.getY().replace(',', '.')), Integer.parseInt(newDot.getR()), newDot.getResult());
        newDot = new Dot();
    }

    private void checkParameters() throws IOException {
        try {

            double y = Double.parseDouble(newDot.getY().replace(',', '.'));
            int x = Integer.parseInt(newDot.getX());
            int r = Integer.parseInt(lastR);

            if (!(y <= -5 || y >= 3)) {
                if (!(x < -3 || x > 3 || Double.parseDouble(newDot.getX()) % 1 != 0)) {
                    if (!(r < 1 || r > 5 || Double.parseDouble(lastR) % 1 != 0)) {
                        checkResult(x, y, r);
                        addDot();
                    } else {
                        errorMessage = "R must be integer in range [1;5]";
                        clearAllFields();
                        goToErrorPage(errorMessage);
                    }
                } else {
                    errorMessage = "X must be integer in range [-3;3]";
                    clearAllFields();
                    goToErrorPage(errorMessage);
                }
            } else {
                errorMessage = "Y must be in range (-5;3)";
                clearAllFields();
                goToErrorPage(errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            clearAllFields();
            goToErrorPage("SOMEerror");
        }

    }


    private void clearAllFields(){
        newDot.setX(null);
        newDot.setY(null);
        lastR=null;
        }


    public void toggle(ActionEvent event) {
        UIComponent component = event.getComponent();
        String value = (String) component.getAttributes().get("value");
        lastR = value;
    }


    private void checkResult(double x, double y, double r) {
        if (x >= 0) {
            if (y >= 0) {
                if (y <= (r/2 -x/2)) {
                    newDot.setResult("Yes!");
                }
            } else {
                if (y >= (-r/2) && x <= r) {
                    newDot.setResult("Yes!");
                }
            }
        } else {
            if (y >= 0) {
                if ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r / 2, 2)) {
                    newDot.setResult("Yes!");
                }
            }
        }
        if (newDot.getResult() == null) {
            newDot.setResult("No!");
        }
    }
}


