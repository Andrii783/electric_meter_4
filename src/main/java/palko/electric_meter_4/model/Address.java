package palko.electric_meter_4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Поле 'Місто' не повинно бути пустим")
    @Size(min = 2, max = 20, message = "Довжина поля 'Місто' повинно бути від 2 до 20 символів")
    @Column(name = "city")
    private String city;
    @NotEmpty(message = "Поле 'Вулиця' не повинно бути пустим")
    @Size(min = 2, max = 20, message = "Довжина поля 'Вулиця' повинно бути від 2 до 20 символів")
    @Column(name = "street")
    private String street;
    @Min(value = 1, message = "Значення поля не повинно бути пустим")
    @Max(value = 1000, message = "Значення поля повинно бути не більше 1000")
    @Column(name = "house")
    private int house;
    @Column(name = "letter")
    private String letter;
    @Column(name = "entrance")
    private int entrance;
    @Column(name = "apartment")
    private int apartment;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;
    @OneToMany(mappedBy = "address",fetch = FetchType.EAGER)
    private List<Meter> meters;


}
