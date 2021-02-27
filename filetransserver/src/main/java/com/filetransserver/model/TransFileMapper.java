package com.filetransserver.model;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransFileMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    
    // 전송 정보 매핑
    @Bean
    public ModelMapper transInfoMapper() {
        // 매핑 전략 설정
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransFileModel.class, Trans_Info.class);

        return modelMapper;
    }
    
    // 서버 정보 매핑
    @Bean
    public ModelMapper serverInfoMapper() {
        // 매핑 전략 설정
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(TransFileModel.class, Server_Info.class);

        return modelMapper;
    }
}
