package project2.dao;

import java.util.List;

import project2.dto.MaterialDto;

@MyDao
public interface MaterialDao {
	public int insertMaterial(MaterialDto dto);
	public int updateMaterial (MaterialDto dto);
	public int deleteMaterial (MaterialDto dto);
	public List<MaterialDto> MaterialList();
	public MaterialDto selectMaterial(MaterialDto dto);

}
