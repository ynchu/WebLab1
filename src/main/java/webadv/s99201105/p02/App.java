package webadv.s99201105.p02;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.Properties;

public class App {
    private static final String fileName = "account.properties";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("请输入账号和密码！！！");
            System.exit(0);
        }
        System.out.println(sha256hex(args[1]));

        Account account = readFromProperties();
        if (check(account, args[0], sha256hex(args[1]))) {
            System.out.println("登录成功");
        } else {
            System.out.println("账号或密码有误");
        }
    }

    public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }

    public static boolean check(Account account, String acc, String pwd) {
        if (account.getAccount().equals(acc) && account.getPassword().equals(pwd)) {
            return true;
        }
        return false;
    }

    public static boolean writeToProperties(Account account) {
        boolean flag = false;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("文件不存在，已创建");
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            properties.setProperty("account", account.getAccount());
            properties.setProperty("password", account.getPassword());
            properties.store(new FileOutputStream(file), "账号密码");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static Account readFromProperties() {
        Account result = new Account();
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                throw new IOException("文件不存在");
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            result.setAccount(properties.getProperty("account"));
            result.setPassword(properties.getProperty("password"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }
}
