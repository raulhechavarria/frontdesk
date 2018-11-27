package frontdesk.com.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Pack entity. This class is used in PackResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /packs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PackCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter nameFrontDeskReceive;

    private StringFilter nameFrontDeskDelivery;

    private StringFilter namePickup;

    private LocalDateFilter dateReceived;

    private LocalDateFilter datePickup;

    public PackCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNameFrontDeskReceive() {
        return nameFrontDeskReceive;
    }

    public void setNameFrontDeskReceive(StringFilter nameFrontDeskReceive) {
        this.nameFrontDeskReceive = nameFrontDeskReceive;
    }

    public StringFilter getNameFrontDeskDelivery() {
        return nameFrontDeskDelivery;
    }

    public void setNameFrontDeskDelivery(StringFilter nameFrontDeskDelivery) {
        this.nameFrontDeskDelivery = nameFrontDeskDelivery;
    }

    public StringFilter getNamePickup() {
        return namePickup;
    }

    public void setNamePickup(StringFilter namePickup) {
        this.namePickup = namePickup;
    }

    public LocalDateFilter getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDateFilter dateReceived) {
        this.dateReceived = dateReceived;
    }

    public LocalDateFilter getDatePickup() {
        return datePickup;
    }

    public void setDatePickup(LocalDateFilter datePickup) {
        this.datePickup = datePickup;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PackCriteria that = (PackCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nameFrontDeskReceive, that.nameFrontDeskReceive) &&
            Objects.equals(nameFrontDeskDelivery, that.nameFrontDeskDelivery) &&
            Objects.equals(namePickup, that.namePickup) &&
            Objects.equals(dateReceived, that.dateReceived) &&
            Objects.equals(datePickup, that.datePickup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        nameFrontDeskReceive,
        nameFrontDeskDelivery,
        namePickup,
        dateReceived,
        datePickup
        );
    }

    @Override
    public String toString() {
        return "PackCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (nameFrontDeskReceive != null ? "nameFrontDeskReceive=" + nameFrontDeskReceive + ", " : "") +
                (nameFrontDeskDelivery != null ? "nameFrontDeskDelivery=" + nameFrontDeskDelivery + ", " : "") +
                (namePickup != null ? "namePickup=" + namePickup + ", " : "") +
                (dateReceived != null ? "dateReceived=" + dateReceived + ", " : "") +
                (datePickup != null ? "datePickup=" + datePickup + ", " : "") +
            "}";
    }

}
