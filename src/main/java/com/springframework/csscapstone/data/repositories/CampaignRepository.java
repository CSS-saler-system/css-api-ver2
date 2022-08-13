package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, UUID>, JpaSpecificationExecutor<Campaign> {

    @Query("SELECT _campaign FROM Campaign _campaign " +
            "LEFT JOIN FETCH _campaign.products " +
            "LEFT JOIN FETCH _campaign.prizes " +
            "WHERE NOT _campaign.campaignStatus = 'FINISHED'" +
            "AND _campaign.id = :id")
    Optional<Campaign> loadFetchOnProducts(@Param("id") UUID id);

    @Query("SELECT _camp FROM Campaign _camp " +
            "WHERE _camp.startDate <= CURRENT_TIMESTAMP " +
            "AND _camp.campaignStatus = 'SENT'")
    List<Campaign> getAllCampaignInDate();
//
//    @Query("SELECT _camp FROM Campaign _camp " +
//            "WHERE _camp.campaignStatus = 'APPROVAL' " +
//            "ORDER BY _camp.startDate DESC")
//    Page<Campaign> findCampaignForCollaborator();
//
//    @Query("SELECT c FROM Campaign c JOIN c.prizes p WHERE c.")
//    List<Tuple> getCamapaignFetchJoinAccountAndPrizeSortByPrice();
//

    @Query("SELECT a FROM Campaign c " +
            "JOIN c.prizes p " +
            "JOIN p.recipients a " +
            "WHERE c.id =:campaignId ")
    List<Map<UUID, Long>> getAccountInClosedCampaign(@Param("campaignId") UUID campaignId);

}