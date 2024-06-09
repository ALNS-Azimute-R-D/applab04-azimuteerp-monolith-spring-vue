package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TicketPurchasedRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TicketPurchasedService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TicketPurchasedDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TicketPurchasedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased}.
 */
@Service
@Transactional
public class TicketPurchasedServiceImpl implements TicketPurchasedService {

    private final Logger log = LoggerFactory.getLogger(TicketPurchasedServiceImpl.class);

    private final TicketPurchasedRepository ticketPurchasedRepository;

    private final TicketPurchasedMapper ticketPurchasedMapper;

    public TicketPurchasedServiceImpl(TicketPurchasedRepository ticketPurchasedRepository, TicketPurchasedMapper ticketPurchasedMapper) {
        this.ticketPurchasedRepository = ticketPurchasedRepository;
        this.ticketPurchasedMapper = ticketPurchasedMapper;
    }

    @Override
    public TicketPurchasedDTO save(TicketPurchasedDTO ticketPurchasedDTO) {
        log.debug("Request to save TicketPurchased : {}", ticketPurchasedDTO);
        TicketPurchased ticketPurchased = ticketPurchasedMapper.toEntity(ticketPurchasedDTO);
        ticketPurchased = ticketPurchasedRepository.save(ticketPurchased);
        return ticketPurchasedMapper.toDto(ticketPurchased);
    }

    @Override
    public TicketPurchasedDTO update(TicketPurchasedDTO ticketPurchasedDTO) {
        log.debug("Request to update TicketPurchased : {}", ticketPurchasedDTO);
        TicketPurchased ticketPurchased = ticketPurchasedMapper.toEntity(ticketPurchasedDTO);
        ticketPurchased = ticketPurchasedRepository.save(ticketPurchased);
        return ticketPurchasedMapper.toDto(ticketPurchased);
    }

    @Override
    public Optional<TicketPurchasedDTO> partialUpdate(TicketPurchasedDTO ticketPurchasedDTO) {
        log.debug("Request to partially update TicketPurchased : {}", ticketPurchasedDTO);

        return ticketPurchasedRepository
            .findById(ticketPurchasedDTO.getId())
            .map(existingTicketPurchased -> {
                ticketPurchasedMapper.partialUpdate(existingTicketPurchased, ticketPurchasedDTO);

                return existingTicketPurchased;
            })
            .map(ticketPurchasedRepository::save)
            .map(ticketPurchasedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketPurchasedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketPurchaseds");
        return ticketPurchasedRepository.findAll(pageable).map(ticketPurchasedMapper::toDto);
    }

    public Page<TicketPurchasedDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ticketPurchasedRepository.findAllWithEagerRelationships(pageable).map(ticketPurchasedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketPurchasedDTO> findOne(Long id) {
        log.debug("Request to get TicketPurchased : {}", id);
        return ticketPurchasedRepository.findOneWithEagerRelationships(id).map(ticketPurchasedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketPurchased : {}", id);
        ticketPurchasedRepository.deleteById(id);
    }
}
