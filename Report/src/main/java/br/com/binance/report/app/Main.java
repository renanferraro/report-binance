package br.com.binance.report.app;

import br.com.binance.report.Report;

public class Main {

	public static void main(String[] args) {
		Report report = new Report();
		report.gerarReport(args[0], args[1]);
	}
	
}
