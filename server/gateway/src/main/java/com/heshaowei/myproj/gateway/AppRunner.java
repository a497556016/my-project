package com.heshaowei.myproj.gateway;

import com.heshaowei.myproj.gateway.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AppRunner implements CommandLineRunner {

    private final AccountService accountService;

    public AppRunner(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("app runner...");

        log.info(accountService.findByUsername("admin"));

        log.info(accountService.findByUsername("admin"));

        log.info(accountService.findByUsername("admin"));

        log.info(System.getProperty("java.io.tmpdir"));
    }
}
