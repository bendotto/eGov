package org.egov.pgr.entity;

import org.egov.infra.persistence.AbstractPersistable;
import org.egov.lib.rjbac.dept.DepartmentImpl;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pgr_complainttype", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class ComplaintType extends AbstractPersistable<Long> {
    private static final long serialVersionUID = 8904645810221559541L;

    @NotBlank
    @SafeHtml
    @Column(name = "name")
    @Length(max = 150)
    private String name;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private DepartmentImpl department;

    @Column(name = "location_required")
    private boolean locationRequired;

    @Column(name = "days_to_resolve")
    @Range(max = 99)
    private int daysToResolve;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public DepartmentImpl getDepartment() {
        return department;
    }

    public void setDepartment(final DepartmentImpl department) {
        this.department = department;
    }

    public boolean isLocationRequired() {
        return locationRequired;
    }

    public void setLocationRequired(boolean locationRequired) {
        this.locationRequired = locationRequired;
    }

    public int getDaysToResolve() {
        return daysToResolve;
    }

    public void setDaysToResolve(int daysToResolve) {
        this.daysToResolve = daysToResolve;
    }
}
