package palko.electric_meter_4.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotEmpty(message = "Ім'я не може бути пустим")
    @Size(min = 2, max = 20, message = "Довжина ім'я повинна бути від 2 до 20 символів")
    @Column(name="first_name")
    private String firstName;
    @NotEmpty(message = "Прізвище не може бути пустим")
    @Size(min = 2, max = 20, message = "Довжина ім'я повинна бути від 2 до 20 символів")
    @Column(name="second_name")
    private String secondName;
    @NotEmpty(message = "Поле телефон не повинно бути пустим")
    @Pattern(regexp = "\\W+380\\d{9}")
    @Column(name="phone")
    private String phone;
    @NotEmpty(message = "Email повинен бути введений")
    @Email(message = "Не вірно введений Email")
    @Column(name="email")
    private String email;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Address> addresses;


    public Person() {
    }

    public Person(String firstName, String secondName, String phone, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
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

    @Override
    public String toString() {
        return "id: " + id +
                "\nfirstName: " + firstName +
                "\nsecondName: " + secondName +
                "\nphone: " + phone +
                "\nemail: " + email;
    }
}
