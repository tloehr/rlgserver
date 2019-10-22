package de.flashheart.rlgserver.backend.service;


import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.data.entity.CoolingDevice;
import de.flashheart.rlgserver.backend.data.repositories.CoolingDeviceRepository;
import de.flashheart.rlgserver.backend.data.repositories.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CoolingDeviceService extends CrudService<CoolingDevice> implements HasLogger {
    private final CoolingDeviceRepository coolingDeviceRepository;
    private final ReadingRepository readingRepository;
    private final BigDecimal minus100 = new BigDecimal(100).negate();

    @Autowired
    public CoolingDeviceService(CoolingDeviceRepository coolingDeviceRepository, ReadingRepository readingRepository) {
        this.coolingDeviceRepository = coolingDeviceRepository;

        this.readingRepository = readingRepository;
    }

    @Override
    protected CrudRepository<CoolingDevice, Long> getRepository() {
        return coolingDeviceRepository;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return coolingDeviceRepository.count();
    }

    @Override
    public Page<CoolingDevice> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return coolingDeviceRepository.findAll(pageable);
    }


    /**
     * Welcher Sensor welcher Truhe oder Kühlschrank zugeordnet werden sollen, weiß der Client. Bei jedem Client Start
     * sendet er dieses Wissen einmal an den Rest Server. Wenn das Gerät (Schrank oder Truhe) schon da ist, wird der
     * Datensatz nur aktualisiert (MIN; MAX usw), falls nicht, wird ein neuer Datensatz angelegt.
     */
    public CoolingDevice createOrUpdate(String uuid, String machine, BigDecimal min, BigDecimal max) {
        CoolingDevice coolingDevice = coolingDeviceRepository.findByUuid(uuid).orElse(new CoolingDevice(machine, uuid, min, max));
        coolingDevice.setMachine(machine);
        coolingDevice.setUuid(uuid);
        coolingDevice.setMin(min);
        coolingDevice.setMax(max);
        return save(coolingDevice);
    }


    public Optional<CoolingDevice> findByUuid(String uuid) {
        return coolingDeviceRepository.findByUuid(uuid);
    }

    public List<CoolingDevice> findAll() {
        return coolingDeviceRepository.findAllByOrderByMachine();
    }

//    public static boolean isInRange(BigDecimal temperature, CoolingDevice coolingDevice) {
//        return coolingDevice.getMin().compareTo(temperature) <= 0 && temperature.compareTo(coolingDevice.getMax()) <= 0;
//    }
}