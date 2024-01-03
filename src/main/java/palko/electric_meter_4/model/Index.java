package palko.electric_meter_4.model;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="index")
public class Index {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotNull(message = "Поле Показники не повинно бути пустим")
    @Column(name = "index")
    private int index;
    @Column(name="date")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "meter_id",referencedColumnName = "id")
    private Meter meter;

}
