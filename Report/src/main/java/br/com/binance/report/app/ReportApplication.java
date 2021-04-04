package br.com.binance.report.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.binance.report.Report;

@SpringBootApplication
public class ReportApplication implements CommandLineRunner {

	private Report report = new Report();
	
	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	@Override
	public void run(String... args) {
		report.gerarReport(args[0], args[1]);
		System.exit(0);
	}

}
