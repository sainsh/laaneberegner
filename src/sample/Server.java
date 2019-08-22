package sample;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server implements Runnable {

    Controller controller;
    ServerSocket server;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Server(Controller control) {
        this.controller = control;


    }

    public void run() {

        try {
            server = new ServerSocket(1337);
            socket = server.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            controller.textArea.appendText("Client Connected: " + new Date().toString() + "\n");

            while (true) {

                Object object = in.readObject();
                if(object == null){
                    break;
                }
                if (object.getClass() == Loan.class) {
                    double totalPayment = ((Loan) object).getAmount() * Math.pow(1 + ((Loan) object).getRate() / 100, ((Loan) object).getYears());
                    double monthlyPayment = totalPayment / (12 * ((Loan) object).getYears());
                    ((Loan) object).setTotalPayment(totalPayment);
                    ((Loan) object).setMonthlyPayment(monthlyPayment);
                    controller.textArea.appendText("Annual interest rate: " + ((Loan) object).getRate() + "\n"
                            + "Number of Years: " + ((Loan) object).getYears() + "\n"
                            + "Loan Amount: " + ((Loan) object).getAmount() + "\n"
                            + "Monthly Payment: " + ((Loan) object).getMonthlyPayment() + "\n"
                            + "Total Payment: " + ((Loan) object).getTotalPayment());

                    out.writeObject(object);
                    out.flush();

                } else {
                    break;
                }
            }

            in.close();
            out.close();
            socket.close();
            server.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
