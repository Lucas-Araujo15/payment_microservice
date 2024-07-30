package br.com.alurafood.payments.service;

import br.com.alurafood.payments.dto.PaymentDTO;
import br.com.alurafood.payments.model.Payment;
import br.com.alurafood.payments.model.Status;
import br.com.alurafood.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDTO> findAll(Pageable pagination) {
        return repository.findAll(pagination).map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO findById(Long id) {
        Payment payment = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO create(PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO update(Long id, PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        repository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
