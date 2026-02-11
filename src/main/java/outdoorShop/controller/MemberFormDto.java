package outdoorShop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

   @NotEmpty(message="회원 이름은 필수 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

}
