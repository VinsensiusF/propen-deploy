package com.unimate.unimate.dto.mapper;

import com.unimate.unimate.dto.CleanAccountDTO;
import com.unimate.unimate.dto.UpdateAccountDTO;
import com.unimate.unimate.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "password", ignore = true)
    CleanAccountDTO accountToCleanAccountDTO(Account account);

//    Account updateAccountDTOToAccount(UpdateAccountDTO updateAccountDTO);
}
