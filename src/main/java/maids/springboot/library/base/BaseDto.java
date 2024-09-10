package maids.springboot.library.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseDto<ID> {

    private ID id;
}
