package palko.electric_meter_4.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Ім'я не може бути пустим")
    @Size(min = 2, max = 20, message = "Довжина ім'я повинна бути від 2 до 20 символів")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Прізвище не може бути пустим")
    @Size(min = 2, max = 20, message = "Довжина ім'я повинна бути від 2 до 20 символів")
    @Column(name = "second_name")
    private String secondName;
    @NotEmpty(message = "Поле телефон не повинно бути пустим")
    @Pattern(regexp = "\\W+380\\d{9}")
    @Column(name = "phone")
    private String phone;
    @NotEmpty(message = "Email повинен бути введений")
    @Email(message = "Не вірно введений Email")
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Address> addresses;
    @Column(name = "password")
    @NotEmpty(message = "Це поле не повинно бути порожнім")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "username")
    @Size(min = 2,max = 20,message = "Це поле повинно бути від 2 до 20 символів")
    @NotEmpty(message = "Це поле не повинно бути порожнім")
    private String username;


}
