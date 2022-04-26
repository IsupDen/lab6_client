package util;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static util.TextFormat.errText;

public class SocketWorker {
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private final ResponseHandler responseHandler;

    public SocketWorker(SocketAddress socketAddress) {
        responseHandler = ResponseHandler.getInstance();
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            this.socketAddress = socketAddress;
        } catch (IOException e) {
            Console.getInstance().println(errText("Some problem's with network!"));
        }
    }

    public String sendRequest(byte[] dataToSend) {

        try {
            ByteBuffer buf = ByteBuffer.wrap(dataToSend);
            do {
                datagramChannel.send(buf, socketAddress);
            } while (buf.hasRemaining());
            return responseHandler.receive(receiveAnswer());
        } catch (IOException exception) {
            RequestHandler.getInstance().setSocketStatus(false);
            return errText("Command didn't send, try again!");
        }
    }

    public String receiveAnswer() {

        long timeStart = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(32000);
        while (true) {
            if ((System.currentTimeMillis() - timeStart) < 5000) {
                try {
                    datagramChannel.receive(buffer);
                    if (buffer.position() != 0) {
                        return ResponseHandler.getInstance().receive(buffer);
                    }
                } catch (IOException ignored) {}
            } else {
                RequestHandler.getInstance().setSocketStatus(false);
                return errText("Server isn't available at the moment!" +
                        "Please, select another remote host!");
            }
        }
    }
}
