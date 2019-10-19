package de.flashheart.rlgserver.backend.data.repositories;


import de.flashheart.rlgserver.backend.data.entity.CoolingDevice;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CoolingDeviceRepository extends JpaRepository<CoolingDevice, Long> {

//    @Cacheable("coolingdevices")
    Optional<CoolingDevice> findByUuid(@NotNull String uuid);
    List<CoolingDevice> findAll();
    List<CoolingDevice> findAllByOrderByMachine();

}
