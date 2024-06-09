package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CustomerTypeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.CustomerTypeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerTypeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.CustomerTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType}.
 */
@Service
@Transactional
public class CustomerTypeServiceImpl implements CustomerTypeService {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeServiceImpl.class);

    private final CustomerTypeRepository customerTypeRepository;

    private final CustomerTypeMapper customerTypeMapper;

    public CustomerTypeServiceImpl(CustomerTypeRepository customerTypeRepository, CustomerTypeMapper customerTypeMapper) {
        this.customerTypeRepository = customerTypeRepository;
        this.customerTypeMapper = customerTypeMapper;
    }

    @Override
    public CustomerTypeDTO save(CustomerTypeDTO customerTypeDTO) {
        log.debug("Request to save CustomerType : {}", customerTypeDTO);
        CustomerType customerType = customerTypeMapper.toEntity(customerTypeDTO);
        customerType = customerTypeRepository.save(customerType);
        return customerTypeMapper.toDto(customerType);
    }

    @Override
    public CustomerTypeDTO update(CustomerTypeDTO customerTypeDTO) {
        log.debug("Request to update CustomerType : {}", customerTypeDTO);
        CustomerType customerType = customerTypeMapper.toEntity(customerTypeDTO);
        customerType = customerTypeRepository.save(customerType);
        return customerTypeMapper.toDto(customerType);
    }

    @Override
    public Optional<CustomerTypeDTO> partialUpdate(CustomerTypeDTO customerTypeDTO) {
        log.debug("Request to partially update CustomerType : {}", customerTypeDTO);

        return customerTypeRepository
            .findById(customerTypeDTO.getId())
            .map(existingCustomerType -> {
                customerTypeMapper.partialUpdate(existingCustomerType, customerTypeDTO);

                return existingCustomerType;
            })
            .map(customerTypeRepository::save)
            .map(customerTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerTypes");
        return customerTypeRepository.findAll(pageable).map(customerTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerTypeDTO> findOne(Long id) {
        log.debug("Request to get CustomerType : {}", id);
        return customerTypeRepository.findById(id).map(customerTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerType : {}", id);
        customerTypeRepository.deleteById(id);
    }
}
