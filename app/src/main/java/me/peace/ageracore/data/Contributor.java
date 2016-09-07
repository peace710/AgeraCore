package me.peace.ageracore.data;

/**
 * Created by User_Kira on 2016/8/31.
 */
public class Contributor {
    public String login;
    public int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public int getContributions() {
        return contributions;
    }
}
