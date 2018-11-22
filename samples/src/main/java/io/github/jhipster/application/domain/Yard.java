package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Yard.
 */
@Entity
@Table(name = "yard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Yard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "streetandnumber")
    private String streetandnumber;

    @Column(name = "city")
    private String city;

    @Column(name = "frequence_summer")
    private Integer frequenceSummer;

    @Column(name = "frequence_winter")
    private Integer frequenceWinter;

    @Column(name = "date_done")
    private ZonedDateTime dateDone;

    @ManyToOne
    @JsonIgnoreProperties("yards")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetandnumber() {
        return streetandnumber;
    }

    public Yard streetandnumber(String streetandnumber) {
        this.streetandnumber = streetandnumber;
        return this;
    }

    public void setStreetandnumber(String streetandnumber) {
        this.streetandnumber = streetandnumber;
    }

    public String getCity() {
        return city;
    }

    public Yard city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getFrequenceSummer() {
        return frequenceSummer;
    }

    public Yard frequenceSummer(Integer frequenceSummer) {
        this.frequenceSummer = frequenceSummer;
        return this;
    }

    public void setFrequenceSummer(Integer frequenceSummer) {
        this.frequenceSummer = frequenceSummer;
    }

    public Integer getFrequenceWinter() {
        return frequenceWinter;
    }

    public Yard frequenceWinter(Integer frequenceWinter) {
        this.frequenceWinter = frequenceWinter;
        return this;
    }

    public void setFrequenceWinter(Integer frequenceWinter) {
        this.frequenceWinter = frequenceWinter;
    }

    public ZonedDateTime getDateDone() {
        return dateDone;
    }

    public Yard dateDone(ZonedDateTime dateDone) {
        this.dateDone = dateDone;
        return this;
    }

    public void setDateDone(ZonedDateTime dateDone) {
        this.dateDone = dateDone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Yard customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Yard yard = (Yard) o;
        if (yard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), yard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Yard{" +
            "id=" + getId() +
            ", streetandnumber='" + getStreetandnumber() + "'" +
            ", city='" + getCity() + "'" +
            ", frequenceSummer=" + getFrequenceSummer() +
            ", frequenceWinter=" + getFrequenceWinter() +
            ", dateDone='" + getDateDone() + "'" +
            "}";
    }
}
