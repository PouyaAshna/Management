package ir.core.lib.management.domain.entity;

import ir.core.lib.management.domain.enumoration.ActionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(name = "action_date")
    @LastModifiedDate
    private ZonedDateTime actionDate;

    @Column(name = "action_by")
    @LastModifiedBy
    private String actionBy;

    @PrePersist
    public void onPrePersist() {
        setActionType(ActionType.PERSIST);
    }

    @PreUpdate
    public void onPreUpdate() {
        setActionType(ActionType.UPDATE);
    }

    @PreRemove
    public void onPreRemove() {
        setActionType(ActionType.REMOVE);
    }

}
