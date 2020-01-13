package chapter2.part6;

public class Boss {

    public void command(String command,Leader leader) {
        leader.doing(command);
    }
    
    public static void main(String[] args) {
        Boss boss = new Boss();
        Leader leader = new Leader();
        boss.command("登陆",leader);
    }
}
