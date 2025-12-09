package project2.controller;

import java.security.Principal;
import java.util.Arrays; // 💡 추가
import java.util.Collections; // 💡 추가
import java.util.List;
import java.util.stream.Collectors; // 💡 추가

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project2.dto.PagingDto;
import project2.dto.RecipeDto;
import project2.service.RecipeService;
import project2.service.AppUserSecurityService;

@Controller
@RequestMapping("/recipe/*")
public class RecipeController {

    @Autowired 
    RecipeService recipeService;

    @Autowired
    AppUserSecurityService userService;

    // --- 1. READ (조회) ---
    // ... (list, detail, myList 메서드는 변경 없음) ...
	/*
	 * @GetMapping("/list") public String list(Model model) { List<RecipeDto>
	 * recipeList = recipeService.selectAll(); model.addAttribute("list",
	 * recipeList); return "/recipe/list"; }
	 */
 // ⭐️ [수정] 페이징 기능 적용 (Sboard1 방식)
    
    @GetMapping("/main")
	public String main(Model model,
	                   @RequestParam(value = "pstartno", defaultValue = "1") int pstartno) {
	        
	    int realTotalCount = recipeService.getTotalRecipeCount();
	   
	    int adjustedTotalCount = realTotalCount;
	    if (realTotalCount > 0 && realTotalCount %  8!= 0) {
	        adjustedTotalCount = ((realTotalCount / 8) + 1) * 8;
	    }

	    PagingDto paging = new PagingDto(adjustedTotalCount, pstartno);
	    model.addAttribute("paging", paging);

	    List<RecipeDto> recipeList = recipeService.selectRecipeListPaging(pstartno);
	    model.addAttribute("list", recipeList);
	    
	    return "/recipe/main";
	}
    
    @GetMapping("/list")
    public String list(
        Model model, 
        @RequestParam(value = "pstartno", defaultValue = "1") int pstartno // 현재 페이지 번호 받기 (기본값 1)
    ) { 
        // 1. Service를 통해 현재 페이지의 레시피 목록을 조회
        List<RecipeDto> recipeList = recipeService.selectRecipeListPaging(pstartno);
        model.addAttribute("list", recipeList);
        
        // 2. 전체 개수를 조회하여 PagingDto 생성 후 View로 전달
        int totalCount = recipeService.getTotalRecipeCount();
        model.addAttribute("paging", new PagingDto(totalCount, pstartno));
        
        return "/recipe/list"; 
    }
    
    @GetMapping("/list2")
    public String list2(
        Model model, 
        @RequestParam(value = "pstartno", defaultValue = "1") int pstartno // 현재 페이지 번호 받기 (기본값 1)
    ) { 
        // 1. Service를 통해 현재 페이지의 레시피 목록을 조회
        List<RecipeDto> recipeList = recipeService.selectRecipeListPaging(pstartno);
        model.addAttribute("list", recipeList);
        
        // 2. 전체 개수를 조회하여 PagingDto 생성 후 View로 전달
        int totalCount = recipeService.getTotalRecipeCount();
        model.addAttribute("paging", new PagingDto(totalCount, pstartno));
        
        return "/recipe/list2"; 
    }
    
    
    


    @GetMapping("/detail")
    public String detail(@RequestParam("recipeId") int recipeId,
                         Principal principal,
                         Model model,
                         RedirectAttributes rttr) {
        RecipeDto recipe = recipeService.selectRecipeDetail(recipeId);
        if (recipe == null) {
            rttr.addFlashAttribute("result", "레시피를 찾을 수 없습니다.");
            return "redirect:/recipe/list";
        }
        model.addAttribute("recipe", recipe);

        // 로그인 사용자 정보 추가
        if (principal != null) {
            String email = principal.getName();
            var user = userService.selectEmail(email);
            if (user != null) {
                model.addAttribute("loginUser", user);
            }
        }

        return "/recipe/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mylist")
    public String myList(Principal principal, Model model, RedirectAttributes rttr) {
        if (principal == null) {
            rttr.addFlashAttribute("result", "로그인 후 이용 가능합니다.");
            return "redirect:/login";
        }
        String email = principal.getName();
        var user = userService.selectEmail(email);
        if (user == null) {
            rttr.addFlashAttribute("result", "사용자 정보를 찾을 수 없습니다.");
            return "redirect:/recipe/list";
        }
        int appUserId = user.getAppUserId();
        List<RecipeDto> myList = recipeService.selectMyRecipes(appUserId);
        model.addAttribute("list", myList);
        return "/recipe/mylist";
    }

    // --- 2. CREATE (등록) ---
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String registerForm() {
        return "/recipe/register";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(
        RecipeDto dto,
        @RequestParam(value = "recipeFiles", required = false) List<MultipartFile> files,
        Principal principal,
        RedirectAttributes rttr) {

        String resultMessage = "레시피 등록 실패";

        try {
            if (principal == null) {
                rttr.addFlashAttribute("result", "로그인 후 이용 가능합니다.");
                return "redirect:/login";
            }

            String email = principal.getName();
            var user = userService.selectEmail(email);
            if (user == null) {
                rttr.addFlashAttribute("result", "사용자 정보를 찾을 수 없습니다.");
                return "redirect:/recipe/list";
            }

            int appUserId = user.getAppUserId();
            dto.setAppUserId(appUserId);

            String nickname = userService.selectUserNickname(appUserId);
            dto.setNickname(nickname);

            if (recipeService.insert(dto, files) > 0) {
                resultMessage = "레시피 등록 성공";
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultMessage = "레시피 등록 중 서버 오류 발생";
        }

        rttr.addFlashAttribute("result", resultMessage);
        return "redirect:/recipe/list";
    }

    // --- 3. UPDATE (수정) ---
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modifyForm(@RequestParam("recipeId") int recipeId, Principal principal, Model model, RedirectAttributes rttr) {
        if (principal == null) {
            rttr.addFlashAttribute("result", "로그인 후 이용 가능합니다.");
            return "redirect:/login";
        }
        String email = principal.getName();
        var user = userService.selectEmail(email);
        if (user == null) {
            rttr.addFlashAttribute("result", "사용자 정보를 찾을 수 없습니다.");
            return "redirect:/recipe/list";
        }
        int currentUserId = user.getAppUserId();

        RecipeDto recipe = recipeService.selectRecipeDetail(recipeId);
        if (recipe == null) {
            rttr.addFlashAttribute("result", "수정 권한이 없거나 레시피를 찾을 수 없습니다.");
            return "redirect:/recipe/detail?recipeId=" + recipeId;
        }
        
        // 💡 핵심 수정: Instruction 문자열을 단계별 List로 분리
        if (recipe.getInstructions() != null && !recipe.getInstructions().isEmpty()) {
            String cleanInstructions = recipe.getInstructions().trim(); 
            
            // 줄바꿈 문자를 기준으로 분리 (\r?\n 은 Windows/Linux 줄바꿈 모두 처리)
            List<String> instructionSteps = Arrays.asList(cleanInstructions.split("\\r?\\n"));
            
            // 각 단계에서 '1. ', '2. ' 와 같은 단계 번호 접두사 제거 (JSP에서 다시 번호를 매길 것이므로)
            List<String> stepsWithoutNumber = instructionSteps.stream()
                .map(step -> step.replaceAll("^\\s*\\d+\\.\\s*", "")) // ex: "1. 텍스트" -> "텍스트"
                .collect(Collectors.toList());
            
            // modify.jsp에서 <c:forEach>로 사용할 변수명 instructionSteps로 모델에 담기
            model.addAttribute("instructionSteps", stepsWithoutNumber);
        } else {
            model.addAttribute("instructionSteps", Collections.emptyList());
        }

        model.addAttribute("recipe", recipe);
        return "/recipe/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(
        RecipeDto dto,
        @RequestParam(value = "recipeFiles", required = false) List<MultipartFile> files,
        Principal principal,
        RedirectAttributes rttr) {

        String resultMessage = "레시피 수정 실패";

        try {
            if (principal == null) {
                rttr.addFlashAttribute("result", "로그인 후 이용 가능합니다.");
                return "redirect:/login";
            }

            String email = principal.getName();
            var user = userService.selectEmail(email);
            if (user == null) {
                rttr.addFlashAttribute("result", "사용자 정보를 찾을 수 없습니다.");
                return "redirect:/recipe/list";
            }

            int currentUserId = user.getAppUserId();
            dto.setAppUserId(currentUserId);

            if (recipeService.update(dto, files) > 0) {
                resultMessage = "레시피 수정 성공";
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultMessage = "레시피 수정 중 오류 발생";
        }

        rttr.addFlashAttribute("result", resultMessage);
        return "redirect:/recipe/detail?recipeId=" + dto.getRecipeId();
    }


    // --- 4. DELETE (삭제) ---
    // ... (deleteRecipe 메서드는 변경 없음) ...
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String deleteRecipe(
        @RequestParam("recipeId") int recipeId,
        Principal principal,
        RedirectAttributes rttr) {

        String resultMessage = "레시피 삭제 실패";

        try {
            if (principal == null) {
                rttr.addFlashAttribute("result", "로그인 후 이용 가능합니다.");
                return "redirect:/login";
            }

            String email = principal.getName();
            var user = userService.selectEmail(email);
            if (user == null) {
                rttr.addFlashAttribute("result", "사용자 정보를 찾을 수 없습니다.");
                return "redirect:/recipe/list";
            }

            int currentUserId = user.getAppUserId();

            if (recipeService.delete(recipeId, currentUserId) > 0) {
                resultMessage = "레시피 삭제 성공";
            } else {
                resultMessage = "레시피 삭제 권한이 없습니다.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultMessage = "레시피 삭제 중 오류 발생";
        }

        rttr.addFlashAttribute("result", resultMessage);
        return "redirect:/recipe/list";
    }
    
	/*
	 * @RequestMapping("/selectSearchTitle") public List<RecipeDto>
	 * selectSearchTitle(@RequestParam("search") String search) { return
	 * recipeService.selectSearchTitle(search); }
	 * 
	 * @RequestMapping("/selectSearchCategory") public List<RecipeDto>
	 * selectSearchCategory(@RequestParam("search") String search) { return
	 * recipeService.selectSearchCategory(search); }
	 */
    
	/*
	 * @RequestMapping("/selectSearchTitle") public List<RecipeDto>
	 * selectSearchTitle(@RequestParam("search") String search) { return
	 * recipeService.selectSearchTitle(search); }
	 * 
	 * @RequestMapping("/selectSearchCategory") public List<RecipeDto>
	 * selectSearchCategory(@RequestParam("search") String search) { return
	 * recipeService.selectSearchCategory(search); }
	 */
}