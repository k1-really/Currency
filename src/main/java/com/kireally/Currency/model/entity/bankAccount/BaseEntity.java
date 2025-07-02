package com.kireally.Currency.model.entity.bankAccount;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле, используемое JPA для оптимистической блокировки
     * (чтобы избежать конфликтов параллельного обновления).
     * Необязательно, но часто полезно в enterprise-приложениях.
     */
    @Version
    private Long version;

    /**
     * Дата и время создания записи.
     * Можно заполнять автоматически с помощью персист-колбэков или слушателей (e.g. @PrePersist).
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи.
     * Можно обновлять автоматически в @PreUpdate.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
