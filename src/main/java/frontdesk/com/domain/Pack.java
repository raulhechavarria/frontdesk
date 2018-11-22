package frontdesk.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Pack.
 */
@Entity
@Table(name = "pack")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_front_desk_receive")
    private String nameFrontDeskReceive;

    @Column(name = "name_front_desk_delivery")
    private String nameFrontDeskDelivery;

    @Column(name = "name_pickup")
    private String namePickup;

    @Column(name = "date_received")
    private LocalDate dateReceived;

    @Column(name = "date_pickup")
    private LocalDate datePickup;

    @Lob
    @Column(name = "pixel")
    private byte[] pixel;

    @Column(name = "pixel_content_type")
    private String pixelContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pack name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameFrontDeskReceive() {
        return nameFrontDeskReceive;
    }

    public Pack nameFrontDeskReceive(String nameFrontDeskReceive) {
        this.nameFrontDeskReceive = nameFrontDeskReceive;
        return this;
    }

    public void setNameFrontDeskReceive(String nameFrontDeskReceive) {
        this.nameFrontDeskReceive = nameFrontDeskReceive;
    }

    public String getNameFrontDeskDelivery() {
        return nameFrontDeskDelivery;
    }

    public Pack nameFrontDeskDelivery(String nameFrontDeskDelivery) {
        this.nameFrontDeskDelivery = nameFrontDeskDelivery;
        return this;
    }

    public void setNameFrontDeskDelivery(String nameFrontDeskDelivery) {
        this.nameFrontDeskDelivery = nameFrontDeskDelivery;
    }

    public String getNamePickup() {
        return namePickup;
    }

    public Pack namePickup(String namePickup) {
        this.namePickup = namePickup;
        return this;
    }

    public void setNamePickup(String namePickup) {
        this.namePickup = namePickup;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public Pack dateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
        return this;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public LocalDate getDatePickup() {
        return datePickup;
    }

    public Pack datePickup(LocalDate datePickup) {
        this.datePickup = datePickup;
        return this;
    }

    public void setDatePickup(LocalDate datePickup) {
        this.datePickup = datePickup;
    }

    public byte[] getPixel() {
        return pixel;
    }

    public Pack pixel(byte[] pixel) {
        this.pixel = pixel;
        return this;
    }

    public void setPixel(byte[] pixel) {
        this.pixel = pixel;
    }

    public String getPixelContentType() {
        return pixelContentType;
    }

    public Pack pixelContentType(String pixelContentType) {
        this.pixelContentType = pixelContentType;
        return this;
    }

    public void setPixelContentType(String pixelContentType) {
        this.pixelContentType = pixelContentType;
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
        Pack pack = (Pack) o;
        if (pack.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pack.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pack{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameFrontDeskReceive='" + getNameFrontDeskReceive() + "'" +
            ", nameFrontDeskDelivery='" + getNameFrontDeskDelivery() + "'" +
            ", namePickup='" + getNamePickup() + "'" +
            ", dateReceived='" + getDateReceived() + "'" +
            ", datePickup='" + getDatePickup() + "'" +
            ", pixel='" + getPixel() + "'" +
            ", pixelContentType='" + getPixelContentType() + "'" +
            "}";
    }
}
