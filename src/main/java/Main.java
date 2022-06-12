
public class Main {
    public static void main(String[] args) {

        String username = "";//input username
        String password = "";//input password
        String profileName = "";//input target username
        
        App app = new App(username, password);
        app.runBot(profileName);
    }
}
