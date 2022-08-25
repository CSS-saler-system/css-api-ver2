package com.springframework.csscapstone.services;

import java.util.UUID;

public interface EnterpriseInformationService {
    long countCollaborator(UUID enterpriseId);

    long countRequestSeliingProduct(UUID enterpriseId);

    Double getPoint(UUID enterpriseId);
}
