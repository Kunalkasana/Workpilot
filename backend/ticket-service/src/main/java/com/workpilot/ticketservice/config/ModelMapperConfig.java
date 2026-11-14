package com.workpilot.ticketservice.config;

import com.workpilot.ticketservice.dto.UpdateTicketRequest;
import com.workpilot.ticketservice.entity.Ticket;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(UpdateTicketRequest.class, Ticket.class)
                .addMappings(mapper -> {
                    mapper.skip(Ticket::setId);
                    mapper.skip(Ticket::setCreatedBy);
                    mapper.skip(Ticket::setCreatedAt);
                    mapper.skip(Ticket::setUpdatedAt);
                });

        return modelMapper;
    }
}