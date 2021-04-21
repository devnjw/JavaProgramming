import java.net.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServer {
    public static void main(String[] args) {
        try{
            //서버소켓 생성
            ServerSocket server = new ServerSocket(10001); //서버 소켓 인스턴스 생성 소켓 넘버를 인자로 받고 클라이언트의 접속을 확인한다.
            System.out.println("Waiting connection...");
            HashMap hm = new HashMap(); // 해시맵 생성
            UserList userlist = new UserList();

            //소켓서버가 종료될때까지 반복
            while(true){
                //소켓 접속 요청이 올 때까지 대기
                Socket sock = server.accept(); // 클라이언트의 접속을 확인하고 동시에 소켓인스턴스를 생성한다.
                ChatThread chatthread = new ChatThread(sock, hm, userlist);
                chatthread.start(); // ChatThread 내의 run()함수 실행
            } // while
        }catch(Exception e){
            System.out.println(e);
        }
    } // main
}

class UserList {
    private ArrayList<String> userlist;
    public UserList(){
        userlist = new ArrayList<>();
    }

    public ArrayList<String> getUserlist() {
        return userlist;
    }

    public synchronized void addUserlist(String user) {
        userlist.add(user);
    }
    public synchronized void removeUserlist(String user) {
        for(String userToRemove : userlist){
            if(userToRemove.equals(user)){
                userlist.remove(user);
            }
        }
    }
}

class ChatThread extends Thread{
    private Socket sock;
    private String id;
    private BufferedReader br;
    private HashMap hm;
    private boolean initFlag = false;
    protected UserList userlist;
    public ChatThread(Socket sock, HashMap hm, UserList userlist){
        this.sock = sock;
        this.hm = hm;
        this.userlist = userlist;
        try{
            //클라이언트에게 데이터를 전달 해주는 출력 스트림
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            //데이터를 받을 입력스트림
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            //첫번째 배열이었던 id를 읽어와 저장한다.
            id = br.readLine();
            userlist.addUserlist(id);
            broadcast(id + " entered.");
            System.out.println("[Server] User (" + id + ") entered.");
            synchronized(hm){ //Thread 사이에 공통적으로 사용해야되는 것을 동기화해준다.
                    hm.put(this.id, pw);
            }
            initFlag = true;
        }catch(Exception ex){
            System.out.println(ex);
        }
    } // construcor
    public void run(){
        try{
            String line = null;
            Date today = new Date();
            SimpleDateFormat time = new SimpleDateFormat("a hh:mm"); //, new Locale("en", "US")
            while((line = br.readLine()) != null){ //문자열을 읽어온다.
                if(line.equals("/quit")){//사용자자가 /quit 입력하면 종료
                    break;
                }
                if(line.equals("/userlist"))//사용자가 /userlist 를 입력하면 현재 접속한 사용자들의 id 리스트와 총 사용자 수를 출력
                    send_userlist(id);
                else if(line.indexOf("/to ") == 0){ // 문자열의 첫번째 글자의 위치번호를 반환한다.
                    sendmsg(line);
                }else if(hasBadWords(line)) {
                    PrintWriter pw = (PrintWriter)hm.get(id);
                    pw.println("Hey! Watch your language!");
                    pw.flush();
                }else
                    broadcastExeptMe("["+time.format(today)+"]"+ id + " : " + line, id);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            synchronized(hm){
                hm.remove(id);
                userlist.removeUserlist(id);
            }
            broadcast(id + " exited.");
            try{
                if(sock != null)
                    sock.close();
            }catch(Exception ex){}
        }
    } // run
    public void sendmsg(String msg){
        int start = msg.indexOf(" ") +1;
        int end = msg.indexOf(" ", start);
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("a hh:mm"); //, new Locale("en", "US")
        if(end != -1){
            String to = msg.substring(start, end);
            String msg2 = msg.substring(end+1);
            //to에 해당하는 데이터, 즉 출력스트림의 참조값을 오브젝트 인스턴스에 저장
            Object obj = hm.get(to);
            if(obj != null){
                PrintWriter pw = (PrintWriter)obj; // 참조값을 pw에 저장
                pw.println("["+time.format(today)+"]"+ id + " whisphered. : " + msg2);
                pw.flush();
            } // if
        }
    } // sendmsg
    public void broadcast(String msg){
        synchronized(hm){
            Collection collection = hm.values();
            Iterator iter = collection.iterator();
            while(iter.hasNext()){ //데이터가 있다면
                // 참조변수를 pw에 저장
                PrintWriter pw = (PrintWriter)iter.next();
                pw.println(msg);
                pw.flush();
            }
        }
    }// broadcast
    public void broadcastExeptMe(String msg, String id){
        synchronized(hm){
            Collection collection = hm.values();
            Iterator iter = collection.iterator();
            while(iter.hasNext()){ //데이터가 있다면
                // 참조변수를 pw에 저장
                PrintWriter pw = (PrintWriter)iter.next();
                if(!pw.equals(hm.get(id)))
                    pw.println(msg);
                pw.flush();
            }
        }
    }// broadcast
    public void send_userlist(String id) {
        ArrayList<String> listToRead = userlist.getUserlist();
        PrintWriter pw = (PrintWriter)hm.get(id);
        for (String user : listToRead)
            pw.println(user);
        pw.println("Num of student : " + listToRead.size());
        pw.flush();
    }
    public boolean hasBadWords(String line){
        ArrayList<String> badWords = new ArrayList<>();
        badWords.add("fuck");
        badWords.add("shit");
        badWords.add("bitch");
        badWords.add("asshole");
        badWords.add("idiot");

        for(String badWord : badWords){
            if(line.contains(badWord))
                return true;
        }

        /*String[] data = line.split(" ");
        for(String word : data)
            if(badWords.contains(word))
                return true;*/
        return false;
    }
    public ArrayList<String> getBadWords(String file){

    }
}
