package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.PaymentGatewayRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.PaymentGatewayService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PaymentGatewayDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.PaymentGatewayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway}.
 */
@Service
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayServiceImpl.class);

    private final PaymentGatewayRepository paymentGatewayRepository;

    private final PaymentGatewayMapper paymentGatewayMapper;

    public PaymentGatewayServiceImpl(PaymentGatewayRepository paymentGatewayRepository, PaymentGatewayMapper paymentGatewayMapper) {
        this.paymentGatewayRepository = paymentGatewayRepository;
        this.paymentGatewayMapper = paymentGatewayMapper;
    }

    @Override
    public PaymentGatewayDTO save(PaymentGatewayDTO paymentGatewayDTO) {
        log.debug("Request to save PaymentGateway : {}", paymentGatewayDTO);
        PaymentGateway paymentGateway = paymentGatewayMapper.toEntity(paymentGatewayDTO);
        paymentGateway = paymentGatewayRepository.save(paymentGateway);
        return paymentGatewayMapper.toDto(paymentGateway);
    }

    @Override
    public PaymentGatewayDTO update(PaymentGatewayDTO paymentGatewayDTO) {
        log.debug("Request to update PaymentGateway : {}", paymentGatewayDTO);
        PaymentGateway paymentGateway = paymentGatewayMapper.toEntity(paymentGatewayDTO);
        paymentGateway = paymentGatewayRepository.save(paymentGateway);
        return paymentGatewayMapper.toDto(paymentGateway);
    }

    @Override
    public Optional<PaymentGatewayDTO> partialUpdate(PaymentGatewayDTO paymentGatewayDTO) {
        log.debug("Request to partially update PaymentGateway : {}", paymentGatewayDTO);

        return paymentGatewayRepository
            .findById(paymentGatewayDTO.getId())
            .map(existingPaymentGateway -> {
                paymentGatewayMapper.partialUpdate(existingPaymentGateway, paymentGatewayDTO);

                return existingPaymentGateway;
            })
            .map(paymentGatewayRepository::save)
            .map(paymentGatewayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentGatewayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentGateways");
        return paymentGatewayRepository.findAll(pageable).map(paymentGatewayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentGatewayDTO> findOne(Long id) {
        log.debug("Request to get PaymentGateway : {}", id);
        return paymentGatewayRepository.findById(id).map(paymentGatewayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentGateway : {}", id);
        paymentGatewayRepository.deleteById(id);
    }
}
