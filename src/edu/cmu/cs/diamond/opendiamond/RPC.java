package edu.cmu.cs.diamond.opendiamond;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;

class RPC implements Callable<MiniRPCReply> {
    final public static int DIAMOND_SUCCESS = 0;

    final public static int DIAMOND_FAILURE = 500;

    final public static int DIAMOND_FCACHEMISS = 501;

    final public static int DIAMOND_NOSTATSAVAIL = 502;

    final public static int DIAMOND_NOMEM = 503;

    final public static int DIAMOND_COOKIE_EXPIRED = 504;

    final private MiniRPCConnection connection;

    final private int cmd;

    final private byte[] data;

    final private String hostname;

    public RPC(MiniRPCConnection connection, String hostname, int cmd,
            byte[] data) {
        this.connection = connection;
        this.hostname = hostname;
        this.cmd = cmd;
        this.data = data;
    }

    @Override
    public MiniRPCReply call() throws Exception {
        return doRPC();
    }

    public MiniRPCReply doRPC() throws IOException {
        connection.sendRequest(cmd, ByteBuffer.wrap(data));
        return new MiniRPCReply(connection.receive(), connection, hostname);
    }

    public static String statusToString(int status) {
        switch (status) {
        case DIAMOND_SUCCESS:
            return "DIAMOND_SUCCESS";

        case DIAMOND_FAILURE:
            return "DIAMOND_FAILURE";

        case DIAMOND_FCACHEMISS:
            return "DIAMOND_FCACHEMISS";

        case DIAMOND_NOSTATSAVAIL:
            return "DIAMOND_NOSTATSAVAIL";

        case DIAMOND_NOMEM:
            return "DIAMOND_NOMEM";

        case DIAMOND_COOKIE_EXPIRED:
            return "DIAMOND_COOKIE_EXPIRED";

        default:
            return MiniRPCMessage.statusToString(status);
        }
    }
}