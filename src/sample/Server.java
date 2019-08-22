package sample;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    Controller controller;
    ServerSocket server;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Server(Controller control) {
        this.controller = control;



    }

    public void run(){

        try {
            server = new ServerSocket(1337);
            socket = server.accept();
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            controller.textArea.appendText("Client Connected: " + new Date().toString());



            Object object = in.readObject();
            if(object.getClass() == Loan.class){
                double totalPayment = ((Loan)object).getAmount()* Math.pow(1+((Loan)object).getRate()/100,((Loan)object).getYears());
                double monthlyPayment = totalPayment/(12*((Loan)object).getYears());
                ((Loan)object).setTotalPayment(totalPayment);
                ((Loan)object).setMonthlyPayment(monthlyPayment);
                controller.textArea.appendText("Annual interest rate: "+ ((Loan)object).getRate()+ "\n"
                                                +"Number of Years: "+ ((Loan)object).getYears()+"\n"
                                                + "Loan Amount: " + ((Loan)object).getAmount()+ "\n"
                                                + "Monthly Payment: " + ((Loan)object).getMonthlyPayment() + "\n"
                                                + "Total Payment: " + ((Loan)object).getTotalPayment());

                out.writeObject(object);

            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
