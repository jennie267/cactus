package com.cactus.api.location.CactusLocationApi.api.footprints;

import com.cactus.api.location.CactusLocationApi.domain.footprints.FootPrints;
import com.cactus.api.location.CactusLocationApi.service.footprints.FootPrintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/location")
public class FootPrintsController {

    @Autowired
    FootPrintsService footPrintsService;

    @GetMapping(value = "/footprints/user/{id}")
    public Map findByUserId(@PathVariable Long id){

        Map<String, List<FootPrints>> footPrintsMap = new HashMap<>();
        footPrintsMap.put("footPrints", footPrintsService.findByUserId(id));


        return footPrintsMap;
    }
    @GetMapping(value = "/footprints/user/date/{userId}/{date}")
    public Map findByInsertTime(@PathVariable Long userId, @PathVariable String date){

        List<FootPrints> footPrints = footPrintsService.findByUserIdAndInsertTimeBetween(userId, date);
        Map<String, List<FootPrints>> footPrintsMap = new HashMap<>();
        footPrintsMap.put("footPrints", footPrints);

        return footPrintsMap;
    }

    @PostMapping(value = "/add")
    public Optional<FootPrints> newFootPrints(@RequestBody FootPrints footPrints){

        System.out.println("!!!1111:"+footPrints.getUserId());
        System.out.println("!!!1111:"+footPrints.getLatitude());
        System.out.println("!!!1111:"+footPrints.getLongitude());

        return footPrintsService.save(footPrints);
    }
    @PutMapping(value = "/update")
    public Optional<FootPrints> updateFootPrints(@RequestBody FootPrints footPrints){

//        FootPrints oldFootPrints = footPrintsService.findById(footPrints.get_id()).get();
//        oldFootPrints.setUserId(11L);
        return footPrintsService.save(footPrints);
    }

    @DeleteMapping("/del")
    public String deleteFootPrints(@RequestBody FootPrints footPrints){
        footPrintsService.delete(footPrints);
        return "SUCCESS";
    }
}