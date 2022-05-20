package com.springframework.csscapstone.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

import static com.springframework.csscapstone.utils.id_generator_utils.CustomIdentifiedGenerator.PREFIX_VALUE;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.id.enhanced.SequenceStyleGenerator.INCREMENT_PARAM;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "my_generator")
    @GenericGenerator(
            name = "my_generator",
            strategy = "com.springframework.csscapstone.utils.id_generator_utils.CustomIdentifiedGenerator",
            parameters = {
                    @Parameter(name = PREFIX_VALUE, value = "ROLE_"),
                    @Parameter(name = INCREMENT_PARAM, value = "10")
            })
    private String id;

    private String name;

    @OneToMany(mappedBy = "role")
    private List<Account> account;

    public Role(String name) {
        this.name = name;
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
