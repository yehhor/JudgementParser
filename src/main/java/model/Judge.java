package model;

/**
 * Created by yehor on 01.07.2016.
 */
public class Judge {
    private String name;

    private String adress;

    private String phone;

    private String email;

    private String url;

    private String schedule_MON_TH;

    private String schedule_FR;

    private String schedule_BREAK;

    public String getSchedule_MON_TH() {
        return schedule_MON_TH;
    }

    public void setSchedule_MON_TH(String schedule_MON_TH) {
        this.schedule_MON_TH = schedule_MON_TH;
    }

    public String getSchedule_FR() {
        return schedule_FR;
    }

    public void setSchedule_FR(String schedule_FR) {
        this.schedule_FR = schedule_FR;
    }

    public String getSchedule_BREAK() {
        return schedule_BREAK;
    }

    public void setSchedule_BREAK(String schedule_BREAK) {
        this.schedule_BREAK = schedule_BREAK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name.replace('\'', '`');
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress.replace('\'', '`');
    }

    public String getPhone() {
        return phone.replace('\'', '`');
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Judge{" +
                "name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
