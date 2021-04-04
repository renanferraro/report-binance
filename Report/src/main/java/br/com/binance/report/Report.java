package br.com.binance.report;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Order;

public class Report {

	private static final String FILE_NAME = "result.xlsx";
	
	 
	public void gerarReport(String apiKey, String secret) {
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, secret);
		BinanceApiRestClient client = factory.newRestClient();
		List<Order> allOrders = client.getAllOrders();
		gerarArquivoExcel(allOrders);
	}

	private void gerarArquivoExcel(List<Order> orders) {
		Object[][] datatypes = new Object[orders.size()][16];
		int i=0;
		SimpleDateFormat sd = new SimpleDateFormat("ddMMyyyy HH:mm:ss");
		for (Order order : orders) {
			datatypes[i][0]=order.getOrderId().toString();
			datatypes[i][1]=order.getSymbol();
			datatypes[i][2]=order.getClientOrderId();		
			datatypes[i][3]=order.getPrice();
			datatypes[i][4]=sd.format(new Date(order.getTime()));
			datatypes[i][5]=order.getStatus().toString();
			datatypes[i][6]=order.getCummulativeQuoteQty();
			datatypes[i][7]=order.getExecutedQty();
			datatypes[i][8]=order.getIcebergQty();
			datatypes[i][9]=order.getOrigQty();
			datatypes[i][10]=order.getOrigQuoteOrderQty();
			datatypes[i][11]=order.getSide().toString();
			datatypes[i][12]=order.getStopPrice();
			datatypes[i][13]=order.getTimeInForce().toString();
			datatypes[i][14]=order.getType().toString();
			datatypes[i][15]=sd.format(new Date(order.getUpdateTime()));
			i++;
		}
		datatypes[0][0]="orderId";
		datatypes[0][1]="symbol";
		datatypes[0][2]="clientOrderId";		
		datatypes[0][3]="price";
		datatypes[0][4]="time";
		datatypes[0][5]="status";
		datatypes[0][6]="cummulativeQuoteQty";
		datatypes[0][7]="executedQty";
		datatypes[0][8]="icebergQty";
		datatypes[0][9]="origQty";
		datatypes[0][10]="origQuoteOrderQty";
		datatypes[0][11]="side";
		datatypes[0][12]="stopPrice";
		datatypes[0][13]="timeInForce";
		datatypes[0][14]="type";
		datatypes[0][15]="updateTime";
		

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Report");


		int rowNum = 0;
		System.out.println("Creating excel");

		for (Object[] datatype : datatypes) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

}
