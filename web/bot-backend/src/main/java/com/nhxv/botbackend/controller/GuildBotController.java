package com.nhxv.botbackend.controller;

import com.nhxv.botbackend.dto.GuildBotDto;
import com.nhxv.botbackend.model.GuildBot;
import com.nhxv.botbackend.repo.GuildBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/guildBot")
public class GuildBotController {
    @Autowired
    private GuildBotRepository guildBotRepository;

    @GetMapping("/{guildBotId}")
    public ResponseEntity<GuildBotDto> getGuildBot(@PathVariable Long guildBotId) throws Exception {
        GuildBot guildBot = this.guildBotRepository.findById(guildBotId).orElseThrow(() -> new Exception("Guild bot not found for id: " + guildBotId));
        GuildBotDto guildBotDto = new GuildBotDto();
        guildBotDto.setId(guildBot.getId().toString());
        guildBotDto.setName(guildBot.getName());
        guildBotDto.setPrefix(guildBot.getPrefix());
        guildBotDto.setEnableLog(guildBot.isEnableLog());
        guildBotDto.setLogChannel(guildBot.getLogChannel().toString());
        guildBotDto.setEnableWelcome(guildBot.isEnableWelcome());
        guildBotDto.setWelcomeChannel(guildBot.getWelcomeChannel().toString());
        guildBotDto.setWelcomeText(guildBot.getWelcomeText());
        return ResponseEntity.ok().body(guildBotDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<GuildBotDto> saveGuildBot(@Valid @RequestBody GuildBotDto guildBotDto) {
        GuildBot guildBot = new GuildBot();
        guildBot.setId(Long.parseLong(guildBotDto.getId()));
        guildBot.setName(guildBotDto.getName());
        guildBot.setWhitelist(true);
        guildBot.setPrefix(guildBotDto.getPrefix());
        guildBot.setEnableLog(guildBotDto.isEnableLog());
        guildBot.setLogChannel(Long.parseLong(guildBotDto.getLogChannel()));
        guildBot.setEnableWelcome(guildBotDto.isEnableWelcome());
        guildBot.setWelcomeChannel(Long.parseLong(guildBotDto.getWelcomeChannel()));
        guildBot.setWelcomeText(guildBotDto.getWelcomeText());
        this.guildBotRepository.save(guildBot);
        return ResponseEntity.ok().body(guildBotDto);
    }
}
