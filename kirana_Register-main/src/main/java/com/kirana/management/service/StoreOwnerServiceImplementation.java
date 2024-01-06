package com.kirana.management.service;

import com.kirana.management.dto.StoreOwnerDTO;
import com.kirana.management.model.StoreOwner;
import com.kirana.management.repository.StoreOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreOwnerServiceImplementation implements StoreOwnerService {
    private final StoreOwnerRepository storeOwnerRepository;
    @Autowired
    public StoreOwnerServiceImplementation(StoreOwnerRepository storeOwnerRepository){
        this.storeOwnerRepository = storeOwnerRepository;
    }

//     Creates a new store owner based on the provided {@code StoreOwnerDTO}.

    @Override
    public StoreOwnerDTO createStoreOwner(StoreOwnerDTO storeOwnerDTO) {
        StoreOwner storeOwner = new StoreOwner();
        storeOwner.setOwnerName(storeOwnerDTO.getOwnerName());
        storeOwner.setStoreAddress(storeOwnerDTO.getStoreAddress());
        storeOwner.setBalanceINR(storeOwnerDTO.getBalanceINR());

        StoreOwner newStoreOwner = storeOwnerRepository.save(storeOwner);

        StoreOwnerDTO storeOwnerResponse = new StoreOwnerDTO();
        storeOwnerResponse.setId(newStoreOwner.getId());
        storeOwnerResponse.setOwnerName(newStoreOwner.getOwnerName());
        storeOwnerResponse.setStoreAddress(newStoreOwner.getStoreAddress());
        storeOwnerResponse.setBalanceINR(newStoreOwner.getBalanceINR());

        return storeOwnerResponse;
    }
}
