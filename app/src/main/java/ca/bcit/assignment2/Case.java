package ca.bcit.assignment2;

public class Case {
    private String Age_Group;
    private String Classification_Reported;
    private String HA;
    private String Reported_Date;
    private String Sex;

    public Case() {
    }

    public Case(String age_group, String classification_Reported,
                String HA, String reported_Date, String sex) {
        this.Age_Group = age_group;
        this.Classification_Reported = classification_Reported;
        this.HA = HA;
        this.Reported_Date = reported_Date;
        this.Sex = sex;
    }

    public String getAge_group() {
        return Age_Group;
    }

    public String getClassification_Reported() {
        return Classification_Reported;
    }

    public String getReported_Date() {
        return Reported_Date;
    }

    public String getSex() {
        return Sex;
    }

    public void setAge_group(String age_group) {
        this.Age_Group = age_group;
    }

    public void setClassification_Reported(String classification_Reported) {
        this.Classification_Reported = classification_Reported;
    }

    public void setReported_Date(String reported_Date) {
        this.Reported_Date = reported_Date;
    }

    public void setSex(String sex) {
        this.Sex = sex;
    }

    public String getHA() {
        return HA;
    }

    public void setHA(String HA) {
        this.HA = HA;
    }
}
