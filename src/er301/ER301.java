//Aquí van los comentarios
//Marcel Palacios
//Antony García González
//Eduardo Beckford
package er301;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import panamahitek.PanamaHitek_Arduino;

//yo soy Monica De Puy
public class ER301 implements Runnable {

    PanamaHitek_Arduino serial = new PanamaHitek_Arduino();
    Thread detectingSequence;
    private volatile boolean running = false;

    private int index = 0;
    private String inputData = "";
    private String code = "";

    private String startSequence1Answer = "AA BB 6 0 FF FF 1 1 0 0 ";
    private String startSequence2Answer = "AA BB 11 0 FF FF 4 1 0 45 52 33 30 31 2D 56 31 2E 30 0 74 ";
    private String snAnswer = "AA BB 6 0 FF FF 7 1 0 6 ";
    private String pulseAnswer = "AA BB 6 0 FF FF 1 2 14 17 ";
    private String cardDetected = "AA BB 8 0 FF FF 1 2 0 4 0 7 ";
    private String preCode = "AA BB A 0 FF FF 2 2 0 ";
    private String afterCodeAnswer1 = "AA BB 6 0 FF FF 7 1 0 6 ";
    private String afterCodeAnswer2 = "AA BB 6 0 FF FF 6 1 0 7 ";
    private static ArrayList listeners;

    SerialPortEventListener evento = new SerialPortEventListener() {

        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {

                inputData += Integer.toHexString(serial.receiveData()).toUpperCase() + " ";
                if (inputData.equals(startSequence1Answer)) {
                    inputData = "";
                    startSequence2();
                }
                if (inputData.equals(startSequence2Answer)) {
                    inputData = "";
                    JOptionPane.showMessageDialog(null, "Dispositivo ER301 conectado!");
                }

            } catch (Exception ex) {
                Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    public ER301(String COMPORT) {
        try {
            serial.arduinoRXTX(COMPORT, 105200, evento);
            serial.setTimeOut(2000);
            detectingSequence = new Thread(this);
            detectingSequence.start();

            listeners = new ArrayList();
        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEventListener(rfidListener listener) {
        listeners.add(listener);
    }

    private void triggerRfidevent() {
        ListIterator li = listeners.listIterator();
        while (li.hasNext()) {
            rfidListener listener = (rfidListener) li.next();
            changeEvent readerEvObj = new changeEvent(this, this);
            (listener).onRfidDetect(readerEvObj);
        }
    }

    public void connectDevice() {
        startSequence1();
    }

    private void startSequence1() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(1);
            serial.sendByte(1);
            serial.sendByte(7);
            serial.sendByte(7);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startSequence2() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(5);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(4);
            serial.sendByte(1);
            serial.sendByte(5);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getInputData() {
        return inputData;
    }

    private void clearInputData() {
        inputData = "";
    }

   public String getCode() {
        return code;
    }

    private void setCode(String input) {
        code = input;
    }

    public void startDetection() {
        index = 1;
        running = true;
    }

    private void startThread() {

    }

    private void startSN() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(7);
            serial.sendByte(1);
            serial.sendByte(2);
            serial.sendByte(4);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pulse() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(1);
            serial.sendByte(2);
            serial.sendByte(38);
            serial.sendByte(37);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCardDetected() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(2);
            serial.sendByte(2);
            serial.sendByte(4);
            serial.sendByte(4);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCode1() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(7);
            serial.sendByte(1);
            serial.sendByte(0);
            serial.sendByte(6);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCode2() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(7);
            serial.sendByte(1);
            serial.sendByte(2);
            serial.sendByte(4);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCode3() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(7);
            serial.sendByte(1);
            serial.sendByte(0);
            serial.sendByte(6);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCode4() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(7);
            serial.sendByte(1);
            serial.sendByte(1);
            serial.sendByte(7);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afterCode5() {

        try {
            serial.sendByte(170);
            serial.sendByte(187);
            serial.sendByte(6);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(0);
            serial.sendByte(6);
            serial.sendByte(1);
            serial.sendByte(32);
            serial.sendByte(39);

        } catch (Exception ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ER301.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        Thread ct = Thread.currentThread();
        while (ct == detectingSequence) {

            while (running) {
                switch (index) {
                    case 0:

                        break;
                    case 1:

                        clearInputData();
                        startSN();
                        index = 2;
                        break;
                    case 2:
                        if (getInputData().equals(snAnswer)) {
                            index = 3;
                        }
                        break;

                    case 3:
                        pulse();
                        clearInputData();
                        index = 4;
                        break;

                    case 4:
                        if (getInputData().equals(pulseAnswer)) {
                            clearInputData();
                            index = 3;

                        } else if (getInputData().equals(cardDetected)) {
                            clearInputData();
                            index = 5;
                        }
                        break;

                    case 5:
                        afterCardDetected();
                        index = 6;
                        break;

                    case 6:
                        if (getInputData().length() > 20) {
                            clearInputData();
                            index = 7;
                        }

                        break;

                    case 7:
                        if (getInputData().length() > 10) {
                            setCode(getInputData().replace(" ", ""));
                        
                            index = 8;

                        }
                        break;
                    case 8:
                      
                        afterCode1();
                        clearInputData();
                        index = 9;
                        break;
                    case 9:
                      
                        if (getInputData().contains(afterCodeAnswer1)) {
                            index = 10;
                        }
                        break;

                    case 10:
                        clearInputData();
                        afterCode2();
                        index = 11;
                        break;

                    case 11:

                        if (getInputData().equals(afterCodeAnswer1)) {
                            index = 12;
                        }
                        break;
                    case 12:
                        clearInputData();
                        afterCode3();
                        index = 13;
                        break;

                    case 13:

                        if (getInputData().equals(afterCodeAnswer1)) {
                            index = 14;
                        }
                        break;

                    case 14:
                        clearInputData();
                        afterCode4();
                        index = 15;
                        break;

                    case 15:
                        if (getInputData().equals(afterCodeAnswer1)) {
                            index = 16;
                        }
                        break;

                    case 16:

                        clearInputData();
                        afterCode5();
                        index = 17;
                        break;

                    case 17:

                        if (getInputData().equals(afterCodeAnswer2)) {
                            index = 18;
                        }
                        break;

                    case 18:
                        this.triggerRfidevent();
                        running = false;
                        break;

                }

            }
        }
    }

}
