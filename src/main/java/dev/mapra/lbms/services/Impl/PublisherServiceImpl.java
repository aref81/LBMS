package dev.mapra.lbms.services.Impl;

import dev.mapra.lbms.repository.PublisherRepository;
import dev.mapra.lbms.services.PublisherService;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl {
    private PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }
}
