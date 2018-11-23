package entity;
public class Person {

    private String PersonID;
    private String ID_type;
    private String ID_number;
    private String name;
    private String sex;
    private String ethnicity;
    private String birthday;

    @Override
    public String toString() {
        return PersonID + "£¬"
                + ID_type + "£¬"
                + ID_number + "£¬"
                + name + "£¬"
                + sex + "£¬"
                + ethnicity + "£¬"
                + birthday;
     }

    public Person(String personID, String ID_type, String ID_number, String name, String sex, String ethnicity, String birthday) {
        this.PersonID = personID;
        this.ID_type = ID_type;
        this.ID_number = ID_number;
        this.name = name;
        this.sex = sex;
        this.ethnicity = ethnicity;
        this.birthday = birthday;
    }
    public Person() {
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public String getID_type() {
        return ID_type;
    }

    public void setID_type(String ID_type) {
        this.ID_type = ID_type;
    }

    public String getID_number() {
        return ID_number;
    }

    public void setID_number(String ID_number) {
        this.ID_number = ID_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

