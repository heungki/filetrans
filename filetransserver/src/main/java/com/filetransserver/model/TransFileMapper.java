package com.filetransserver.model;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransFileMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    
    // ���� ���� ����
    @Bean
    public ModelMapper transInfoMapper() {
        // ���� ���� ����
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransFileModel.class, Trans_Info.class);

        return modelMapper;
    }
    
    // ���� ���� ����
    @Bean
    public ModelMapper serverInfoMapper() {
        // ���� ���� ����
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransFileModel.class, Server_Info.class);

        return modelMapper;
    }
}
