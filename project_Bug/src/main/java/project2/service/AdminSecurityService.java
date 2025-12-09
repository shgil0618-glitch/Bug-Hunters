package project2.service;

import java.util.List;
import project2.dto.AppUserDto;

public interface AdminSecurityService {
    List<AppUserDto> selectAll();
    AppUserDto select(int appUserId);
    int update(AppUserDto dto);
    int delete(AppUserDto dto);
   int updateNickname(int appUserId, String nickname);

}