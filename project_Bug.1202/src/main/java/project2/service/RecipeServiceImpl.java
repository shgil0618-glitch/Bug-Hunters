package project2.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.AppUserMapper;
import project2.dao.RecipeDao;
import project2.dto.RecipeDto;
import project2.dto.RecipeImage;
import project2.dto.RecipeIngre;
import project2.dto.RecipeIngreMap;

@Service
public class RecipeServiceImpl implements RecipeService {
	@Autowired PasswordEncoder pwencoder;
    @Autowired
    RecipeDao dao;

 // 파일 저장 경로 (Windows 기준)
    private static final String UPLOAD_PATH = "C:/file/";

    @Override
    public int insert(RecipeDto dto, List<MultipartFile> files) {
        int result = 0;
        
        // 1. UPLOAD_PATH 폴더가 없을 경우 생성 (안전 장치)
        File dir = new File(UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 2. 레시피 기본 정보 저장 (mapper의 selectKey로 recipeId 채워짐)
        result += dao.insert(dto);
        int recipeId = dto.getRecipeId();

        // 3. instruction 텍스트 처리
        List<String> instructionTexts = dto.getInstructionTexts();

        // 4. 이미지 처리 — instruction step 순서에 맞춰 files와 매핑하여 저장
        if (files != null && !files.isEmpty()) {
            int stepCount = (instructionTexts != null) ? instructionTexts.size() : 0;

            if (stepCount > 0) {
                for (int i = 0; i < stepCount; i++) {
                    MultipartFile f = (files.size() > i) ? files.get(i) : null;
                    if (f != null && !f.isEmpty()) {
                        String original = f.getOriginalFilename();
                        String saveName = UUID.randomUUID().toString() + "_" + original;
                        File dest = new File(UPLOAD_PATH + saveName);
                        try {
                            f.transferTo(dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("파일 저장 중 오류 발생", e);
                        }

                        // 🚨 수정: DB에 파일명만 저장 (경로 중복 문제 해결)
                        String dbUrl = saveName; 

                        RecipeImage imageDto = new RecipeImage();
                        imageDto.setRecipeId(recipeId);
                        imageDto.setRurl(dbUrl);

                        result += dao.insertRecipeImage(imageDto);
                    }
                }
            } else {
                // instructionTexts 가 없는데 files 만 있는 경우
                for (MultipartFile f : files) {
                    if (f != null && !f.isEmpty()) {
                        String original = f.getOriginalFilename();
                        String saveName = UUID.randomUUID().toString() + "_" + original;
                        File dest = new File(UPLOAD_PATH + saveName);
                        try {
                            f.transferTo(dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("파일 저장 중 오류 발생", e);
                        }

                        // 🚨 수정: DB에 파일명만 저장 (경로 중복 문제 해결)
                        String dbUrl = saveName;
                        
                        RecipeImage imageDto = new RecipeImage();
                        imageDto.setRecipeId(recipeId);
                        imageDto.setRurl(dbUrl);

                        result += dao.insertRecipeImage(imageDto);
                    }
                }
            }
        }

        // 5. 재료 삽입
        List<RecipeIngre> ingredients = dto.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            RecipeIngreMap mapDto = new RecipeIngreMap();
            mapDto.setRecipeId(recipeId);
            dao.insertIngredientMap(mapDto); 
            int ingreMapId = mapDto.getIngreMapId();

            for (RecipeIngre ingre : ingredients) {
                ingre.setIngreMapId(ingreMapId);
                result += dao.insertIngredientDetail(ingre);
            }
        }

        return result;
    }



    // --- R E A D (조회) ---
    @Override
    public List<RecipeDto> selectAll() {
        // 매퍼에서 이미 categoryName, nickname을 가져오므로 그대로 반환
        return dao.selectAll();
    }

    
    @Override
    public List<RecipeDto> selectMyRecipes(int appUserId) {
        return dao.selectMyRecipes(appUserId);
    }

    @Autowired
    AppUserSecurityService userService;  // 사용자 서비스

    @Override
    @Transactional
    public RecipeDto selectRecipeDetail(int recipeId) {
        // 1. 조회수 증가
        dao.incrementRecipeViews(recipeId);

        // 2. 레시피 기본 정보 조회
        RecipeDto dto = dao.select(recipeId);

        if (dto != null) {
            // 카테고리 이름 조회
            String categoryName = dao.selectCategoryNameById(dto.getCategory());
            dto.setCategoryName(categoryName);  // 카테고리 이름 설정

            // 이미지 목록 조회
            List<RecipeImage> images = dao.selectRecipeImages(recipeId);
            dto.setImages(images);

            // 재료 목록 조회
            List<RecipeIngre> ingredients = dao.selectRecipeIngredients(recipeId);
            dto.setIngredients(ingredients);
        }

        return dto;
    }


    // --- U P D A T E (수정) ---
    @Override
    @Transactional
    public int update(RecipeDto dto, List<MultipartFile> files) {
        int result = 0;
        int recipeId = dto.getRecipeId();
        
        // 1. UPLOAD_PATH 폴더가 없을 경우 생성 (안전 장치)
        File dir = new File(UPLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 2. 기본 정보 업데이트
        result += dao.update(dto);

        // 3. 이미지: 기존 이미지 삭제 후 새로 등록
        dao.deleteRecipeImages(recipeId);

        List<String> instructionTexts = dto.getInstructionTexts();

        if (files != null && !files.isEmpty()) {
            int stepCount = (instructionTexts != null) ? instructionTexts.size() : 0;

            if (stepCount > 0) {
                for (int i = 0; i < stepCount; i++) {
                    MultipartFile f = (files.size() > i) ? files.get(i) : null;
                    if (f != null && !f.isEmpty()) {
                        String original = f.getOriginalFilename();
                        String saveName = UUID.randomUUID().toString() + "_" + original;
                        File dest = new File(UPLOAD_PATH + saveName);
                        try {
                            f.transferTo(dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("파일 저장 중 오류 발생", e);
                        }

                        // 🚨 수정: DB에 파일명만 저장 (경로 중복 문제 해결)
                        String dbUrl = saveName;
                        
                        RecipeImage imageDto = new RecipeImage();
                        imageDto.setRecipeId(recipeId);
                        imageDto.setRurl(dbUrl);
                        result += dao.insertRecipeImage(imageDto);
                    }
                }
            } else {
                for (MultipartFile f : files) {
                    if (f != null && !f.isEmpty()) {
                        String original = f.getOriginalFilename();
                        String saveName = UUID.randomUUID().toString() + "_" + original;
                        File dest = new File(UPLOAD_PATH + saveName);
                        try {
                            f.transferTo(dest);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("파일 저장 중 오류 발생", e);
                        }

                        // 🚨 수정: DB에 파일명만 저장 (경로 중복 문제 해결)
                        String dbUrl = saveName;
                        
                        RecipeImage imageDto = new RecipeImage();
                        imageDto.setRecipeId(recipeId);
                        imageDto.setRurl(dbUrl);
                        result += dao.insertRecipeImage(imageDto);
                    }
                }
            }
        }

        // 4. 재료는 기존 map 삭제 후 재생성
        dao.deleteIngredientMaps(recipeId);

        List<RecipeIngre> ingredients = dto.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            RecipeIngreMap mapDto = new RecipeIngreMap();
            mapDto.setRecipeId(recipeId);
            dao.insertIngredientMap(mapDto);
            int ingreMapId = mapDto.getIngreMapId();

            for (RecipeIngre ingre : ingredients) {
                ingre.setIngreMapId(ingreMapId);
                result += dao.insertIngredientDetail(ingre);
            }
        }

        return result;
    }


    // --- D E L E T E (삭제) ---
    @Override
    public int delete(int recipeId, int appUserId) {
        // 1. 권한 확인 (선택 사항: 실제 구현 시 권한 확인 로직을 추가해야 함)
        // 레시피를 조회하여 작성자 ID와 appUserId가 일치하는지 확인
        // ...

        // 2. RECIPES 테이블 삭제 (CASCADE로 자식 테이블 자동 삭제)
        return dao.delete(recipeId);
    }

    /**
     * 레시피 조회수를 증가시킵니다.
     * @param recipeId 레시피 ID
     * @return 조회수가 증가한 레시피의 수
     */
    @Override
    public int incrementRecipeViews(int recipeId) {
        // DAO에서 조회수 증가 메서드 호출
        return dao.incrementRecipeViews(recipeId);
    }
}
