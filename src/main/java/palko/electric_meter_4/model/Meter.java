package palko.electric_meter_4.model;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="meter")
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotNull(message = "Поле Номер не повинно бути пустим")
    @Column(name="number")
    private int number;
//    @NotNull(message = "Поле Показники не повинно бути пустим")
//
//    private int indexes;
//    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "meter",fetch = FetchType.EAGER)
    private List<Index> index;

    public Meter() {
    }

    public Meter(int id, int number, int indexes, Timestamp date) {
        this.id = id;
        this.number = number;
        //this.indexes = indexes;
        //this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public int getIndexes() {
//        return indexes;
//    }
//
//    public void setIndexes(int indexes) {
//        this.indexes = indexes;
//    }
//
//    public Timestamp getDate() {return date;}
//
//    public void setDate(Timestamp date) {
//        this.date = date;
//    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Index> getIndex() {
        return index;
    }

    public void setIndex(List<Index> index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nnumber: " + number;
                //"\nindexes: " + indexes +
                //"\ndate: " + date;
    }
}
