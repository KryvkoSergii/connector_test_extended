package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by srg on 01.07.16.
 */
public class Connector2 {

    public static void main(String[] args) {

        ServerSocket ss = null;
        BufferedReader in = null;
        PrintWriter out= null;

        try {
            ss = new ServerSocket(42027);
            System.out.println("Waiting...");
            Socket clientSocket = ss.accept();
            System.out.println("Connected: " + clientSocket.getRemoteSocketAddress());
            in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);

            String inputMessage;
            StringBuilder sb;
            while ((inputMessage = in.readLine()) != null) {
                sb  = new StringBuilder();
                //convert to hex in string
                for (byte b:inputMessage.getBytes()) sb.append(String.format("%02x", b & 0xFF));
                String request = sb.toString();
                System.out.printf("Received message: %s"+'\n',request);
                //get InvokeID and convert to dec
                String InvokeIDString = getVariable(16,8,request);
                System.out.println("InvokeIDString = " + InvokeIDString);
                Integer InvokeIDInteger = Integer.parseInt(InvokeIDString,16);
                // preparing response
                String responseTemplate = "000000280000000400000004001c029600000000000000045746cc1e000000110009e4020001d00400001388e0020000";
                StringBuilder responseSB = new StringBuilder();
                responseSB.append(getVariable(0,15,responseTemplate));
                responseSB.append(String.format("%08x", ++InvokeIDInteger));
                responseSB.append(getVariable(24,24,responseTemplate));
                responseSB.append(String.format("%08x", (int) System.currentTimeMillis()/1000));
                responseSB.append(responseTemplate.substring(56,responseTemplate.length()-1));
                // get response string in hex representation
                String response = responseSB.toString();
                // convert to string
                StringBuilder sb1 = new StringBuilder();
                for (byte b : hexStringToByteArray(response)){
                    sb1.append((char) b);
                }
                out.print(response);
                out.flush();

                System.out.printf("Response message: %s"+'\n',responseSB.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * get variable in hex representation
     * @param position
     * @param length
     * @param message
     * @return
     */
    private static String getVariable(int position,int length, String message){
        String result = message.substring(position,position+length);
        return result;
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
