import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient {
    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;
    private boolean endflag;
    String username;
    String ip;

    public static void main(String[] args) {
        /*if(args.length != 2){ //입력 값이 2개가 아니면 Error 출력 후 프로그램 종료.
            System.out.println("Usage : java ChatClient <username> <server-ip>");
            System.exit(1);
        }*/
        Scanner sss = new Scanner(System.in);
        System.out.println("your name >> ");
        String username = sss.next();
        System.out.println("server ip >> ");
        String ip = sss.next();

        ChatClient chatClient = new ChatClient(username, ip);
        chatClient.start();

    } // main

    public ChatClient(String username, String ip) {
        this.username = username;
        this.ip = ip;
        sock = null;
        br = null;
        pw = null;
        endflag = false;
    }

    public void start(){
        try{
            // 소켓을 생성한다. 서버 ip를 소켓에 전달
            sock = new Socket(ip, 10001);
            //서버에 데이터를 전달할 수 있는 스트림을 생성한다.
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            //서버로부터 데이터를 받아 올 수 있는 스트림을 생성한다.
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            // 사용자로부터 스트림을 입력받아서 버퍼에 래핑한다.
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            // send username.
            pw.println(username);
            pw.flush();
            InputThread it = new InputThread(sock, br);//클라이언트의 스레드. 데이터를 받으면서 동시에 문자를 입력
            it.start(); // InputThread 내의 run() 함수 실행
            String line = null;
            while((line = keyboard.readLine()) != null){ //사용자가 입력한 문자열을 입력받아 서버로 전송
                pw.println(line);
                pw.flush();
                if(line.equals("/quit")){ // /quit 입력 시 종료
                    endflag = true;
                    break;
                }
                //if(line.equals("/userlist"))
            }
            System.out.println("Connection closed.");
        }catch(Exception ex){
            if(!endflag)
                System.out.println(ex);
        }finally{
            try{
                if(pw != null)
                    pw.close();
            }catch(Exception ex){}
            try{
                if(br != null)
                    br.close();
            }catch(Exception ex){}
            try{
                if(sock != null)
                    sock.close();
            }catch(Exception ex){}
        } // finally
    }
} // class

class InputThread extends Thread{
    private Socket sock = null;
    private BufferedReader br = null;
    //생성과 동시에 소켓 넘버와 서버로부터 데이터를 읽어오는 입력 스트림의 주소를 받음
    public InputThread(Socket sock, BufferedReader br){
        this.sock = sock;
        this.br = br;
    }
    public void run(){
        try{
            String line = null;
            //서버로부터 데이터를 읽으면서 동시에 화면에 출력
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        }catch(Exception ex){
        }finally{
            try{
                if(br != null)
                    br.close();
            }catch(Exception ex){}
            try{
                if(sock != null)
                    sock.close();
            }catch(Exception ex){}
        }
    } // InputThread
}
