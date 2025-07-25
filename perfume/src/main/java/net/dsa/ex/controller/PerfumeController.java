package net.dsa.ex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.ex.dto.PerfumeDTO;
import net.dsa.ex.service.PerfumeService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PerfumeController {
   
   private final PerfumeService ps;
   
   @GetMapping({"", "/"})
   public String perfumeForm() {
      return "main";
   }
   
   @PostMapping("save")
   public String save(PerfumeDTO perfume) {
      log.debug("perfume :{}",perfume);
      ps.save(perfume);
      return "redirect:/";
   }
   
   @GetMapping("result")
   public String result(Model model) {
      List<PerfumeDTO> perfumeList = ps.findAll();
      log.debug("> result : {}",perfumeList);
      int maleCount = ps.countByGender("남성");
      int femaleCount = ps.countByGender("여성");

      model.addAttribute("perfumeList", perfumeList);
      model.addAttribute("maleCount", maleCount);
      model.addAttribute("femaleCount", femaleCount);
      return "result";
   }
}
