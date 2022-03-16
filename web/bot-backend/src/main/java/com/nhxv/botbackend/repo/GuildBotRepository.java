package com.nhxv.botbackend.repo;

import com.nhxv.botbackend.model.GuildBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildBotRepository extends JpaRepository<GuildBot, Long> {
}
