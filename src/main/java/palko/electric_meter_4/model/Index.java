package palko.electric_meter_4.model;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
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

    public Index() {
    }

    public Index(int id, int indexes, LocalDateTime date) {
        this.id = id;
        this.index = indexes;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int indexes) {
        this.index = indexes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }
}
