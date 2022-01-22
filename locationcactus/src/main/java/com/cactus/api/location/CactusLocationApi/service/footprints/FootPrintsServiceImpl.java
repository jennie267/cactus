package com.cactus.api.location.CactusLocationApi.service.footprints;

import com.cactus.api.location.CactusLocationApi.domain.footprints.FootPrints;
import com.cactus.api.location.CactusLocationApi.domain.footprints.FootPrintsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FootPrintsServiceImpl implements FootPrintsService{

    @Autowired
    FootPrintsRepository footPrintsRepository;


    @Override
    public Optional<FootPrints> findById(Long id) {
        return footPrintsRepository.findById(id);
    }

    @Override
    public List<FootPrints> findByUserId(Long id) {
        return footPrintsRepository.findByUserIdOrderBySeq(id);
    }

    @Override
    public List<FootPrints> findByUserIdAndInsertTimeBetween(Long userId, String date) {
        LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(4,6)),
                Integer.parseInt(date.substring(6,8)),
                0,0,0);

        LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(4,6)),
                Integer.parseInt(date.substring(6,8)),
                23,59,59);


        return footPrintsRepository.findByUserIdAndInsertTimeBetweenOrderBySeqDesc(userId,startDate,endDate);
    }

    @Override
    public Optional<FootPrints> findFirstByUserIdAndInsertTimeBetweenOrderBySeqDesc(Long userId, String date) {
        LocalDateTime startDate = LocalDateTime.of(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(4,6)),
                Integer.parseInt(date.substring(6,8)),
                0,0,0);

        LocalDateTime endDate = LocalDateTime.of(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(4,6)),
                Integer.parseInt(date.substring(6,8)),
                23,59,59);

        return footPrintsRepository.findFirstByUserIdAndInsertTimeBetweenOrderBySeqDesc(userId, startDate, endDate);
    }

    @Override
    public Optional<FootPrints> save(FootPrints footPrints) {
        footPrints.setInsertTime(LocalDateTime.now());

        String date = footPrints.getInsertTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Optional<FootPrints> dataForSeq = findFirstByUserIdAndInsertTimeBetweenOrderBySeqDesc(footPrints.getUserId(),date);

        if(!dataForSeq.isPresent()){
            footPrints.setSeq(1L);
        }else{
            footPrints.setSeq(dataForSeq.get().getSeq()+1);
        }
        return Optional.of(footPrintsRepository.save(footPrints));
    }

    @Override
    public void delete(FootPrints footPrints) {
        footPrintsRepository.delete(footPrints);
    }
	
}
