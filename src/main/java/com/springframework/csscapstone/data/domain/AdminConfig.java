package com.springframework.csscapstone.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admin_config")
public class AdminConfig {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Double feeAdmin;

    public AdminConfig(Double feeAdmin) {
        this.feeAdmin = feeAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminConfig)) return false;

        AdminConfig that = (AdminConfig) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AdminConfig{" +
                "id=" + id +
                ", feeAdmin=" + feeAdmin +
                '}';
    }
}
