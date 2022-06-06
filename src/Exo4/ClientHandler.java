package Exo4;

import java.io.*;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.concurrent.ExecutionException;

public class ClientHandler implements Runnable{

    public static ArrayList <ClientHandler> clientHandlers = new ArrayList<>(); //On va garder tout les clients dans notre liste
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private int nbe_client_connecter;
    private String clientPassword;

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine(); //Lorsqu'on va demadner le nom d'utilisateur dans client il va lire ce qui a été envoyé
            clientHandlers.add(this);
            broadcastMessage("SERVER : " + clientUsername + "Has entered the chat !"); // ENvoie le username de la personne qui vinet de rentrer
        }catch(IOException e){
            closeEverything(socket , bufferedReader , bufferedWriter);
        }
    }

    @Override
    public void run(){
        String messageFromClient;
        if(verify_authentification(this.clientUsername)){
            while(socket.isConnected()){
                try{
                    messageFromClient = bufferedReader.readLine(); //il va lire les message envoyer par le client à chaque fois
                    broadcastMessage(messageFromClient); // il va écrire dans le buffer de chaque client qu'ils vont ensuite lire
                }catch (IOException e){
                    closeEverything(socket , bufferedReader , bufferedWriter);
                    break;
                }
            }
        }
        else{
            for(ClientHandler clientHandler :clientHandlers){
                try{
                    if(clientHandler.clientUsername.equals(clientUsername)){
                        clientHandler.bufferedWriter.write("Erreur d'authentification il n'y a pas de username sous ce nom");
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                }
                catch (IOException e){
                    closeEverything(socket,bufferedReader, bufferedWriter);
                }
            }
        }

    }

    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler :clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch (IOException e){
                closeEverything(socket,bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + "Has Left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader , BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket!= null){
                socket.close();
            }
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }
    public static boolean verify_authentification(String username){
        String thisLine;
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:/EFREI 2021-2022/Semestre 6/TP JAVA/TP4_Thread/src/Exo4/authentification.txt"));
            while ((thisLine = br.readLine()) != null) {

                if(thisLine.equals(username)){
                    System.out.println("CONNECTION ACCORDER");
                    return true;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


}