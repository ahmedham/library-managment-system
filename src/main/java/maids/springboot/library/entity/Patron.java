package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maids.springboot.library.base.BaseDto;
import maids.springboot.library.base.BaseEntity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patron  extends BaseEntity<Long> {

    private String name;

    private String email;

    private String phone;

}
