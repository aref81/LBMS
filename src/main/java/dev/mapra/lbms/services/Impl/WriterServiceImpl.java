package dev.mapra.lbms.services.Impl;

import dev.mapra.lbms.repository.WriterRepository;
import org.springframework.stereotype.Service;

@Service
public class WriterServiceImpl {
    private WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }
}
