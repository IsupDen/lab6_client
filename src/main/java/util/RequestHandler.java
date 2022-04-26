package util;

import data.LabWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import static util.TextFormat.errText;
import static util.TextFormat.successText;

public class RequestHandler {

    private static RequestHandler instance;
    private InetSocketAddress socketAddress;
    private boolean socketStatus;


    public static RequestHandler getInstance() {
        if (instance == null) instance = new RequestHandler();
        return instance;
    }

    public String send(Request request) {
        try {
            SocketWorker socketWorker = new SocketWorker(socketAddress);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(request);
            return socketWorker.sendRequest(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return errText("Request can't be serialized, call programmer!");
        }
    }

    public String send(Request request, LabWork labWork) {
        if (labWork != null) {
            request.addLabWork(labWork);
            return send(request);
        } else return errText("LabWork isn't exist, try again!");
    }

    public void setRemoteHostSocketAddress(InetSocketAddress socketAddress) {this.socketAddress = socketAddress;}

    public String getInformation() {

        return successText("\nCONNECTION STATUS:\n") +
                successText("--------------------------------------------------\n") +
                "Remote host address: " + successText(String.valueOf(socketAddress.getAddress())) + "\n" +
                "Remote host port:    " + successText(String.valueOf(socketAddress.getPort())) + "\n" +
                successText("--------------------------------------------------\n");
    }

    public void setSocketStatus(boolean socketStatus) {
        this.socketStatus = socketStatus;
    }

    public boolean getSocketStatus() {
        return socketStatus;
    }
}
