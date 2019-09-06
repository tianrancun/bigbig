/*
package com.bigbig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sams.clubops.backroom.claims.integration.liquidation.LoadLiquidationItemsResponse;
import com.sams.clubops.backroom.claims.integration.liquidation.LoadLiquidationItemsResponseHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class CreateExcel {
    public static void main(String[] args) throws IOException {}

    @Test
    public void create(){
        String ClubNbr="4969";
        //**创建工作簿
        try {
            InputStream in = new FileInputStream("C:/dajian/test.xls");
            HSSFWorkbook wb = new HSSFWorkbook(in);


            //1、创建工作表
            HSSFSheet sheet = wb.getSheet(ClubNbr);
            for(int i = 0; i < 2; i++){
                //设置列宽
                sheet.setColumnWidth(i, 5000);
            }
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);

            HSSFCellStyle cellStyle2 = wb.createCellStyle();
            cellStyle2.setWrapText(true);

            AtomicInteger index = new AtomicInteger(sheet.getLastRowNum());

            Map<String, String> headerMap = new HashMap<String, String>(){
                {
                    put("Ocp-Apim-Subscription-Key","04ae2969d2c54d20b3ca9bec86f5fe62");
                    put("client_id","d86a12fd-ea97-4e7d-9831-6ce7351ac551");
                    put("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InU0T2ZORlBId0VCb3NIanRyYXVPYlY4NExuWSIsImtpZCI6InU0T2ZORlBId0VCb3NIanRyYXVPYlY4NExuWSJ9.eyJhdWQiOiJodHRwczovL3dhbG1hcnQub25taWNyb3NvZnQuY29tL2Q4NmExMmZkLWVhOTctNGU3ZC05ODMxLTZjZTczNTFhYzU1MSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0LzNjYmNjM2QzLTA5NGQtNDAwNi05ODQ5LTBkMTFkNjFmNDg0ZC8iLCJpYXQiOjE1NjUyNTE1NjAsIm5iZiI6MTU2NTI1MTU2MCwiZXhwIjoxNTY1MjU1NDYwLCJhaW8iOiI0MkZnWUhodHFxRmFFaEJYZjZEZ2NWWCttYVVhQUE9PSIsImFwcGlkIjoiMGJjMmE4NDEtYWM0ZS00NTk2LTkxMjEtNWJlMThiMzU5YjZmIiwiYXBwaWRhY3IiOiIxIiwiaWRwIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvM2NiY2MzZDMtMDk0ZC00MDA2LTk4NDktMGQxMWQ2MWY0ODRkLyIsIm9pZCI6IjIzMzNjMjk3LWViNTYtNDhmNi1iZWUxLWFjYjM3NWQ5YmU3NiIsInN1YiI6IjIzMzNjMjk3LWViNTYtNDhmNi1iZWUxLWFjYjM3NWQ5YmU3NiIsInRpZCI6IjNjYmNjM2QzLTA5NGQtNDAwNi05ODQ5LTBkMTFkNjFmNDg0ZCIsInV0aSI6Ii1INUpxdXI1SVV5My14cGV1bVB5QUEiLCJ2ZXIiOiIxLjAifQ.nbuK0i-BIS9EH8JN3OZ7Y3YKQZJmiSzDqTI1Fv4oWXMedBKUB8Cbl4_MQXpAhDLNbari8_i6iKKWqtDEwrfv0eIsUQL5Jooqgat4SYej21S-qZZn4h8PGGfmXiUthOhRV5cBZN0HjBydTYT5Ta84JRnYtFq4SfL0BFoKi9lsF7IO-7vo_Xzoe_niS48TCCH-tIbKttTont170PzejwbyhoMvDcg2pYn0ZOJgCDKJNJjMNhi1rcw9l7ry-bcHk86NtDN_GGssNXhNJTEzFixTBEXkXxW5M-qr-H5M73bYRdNA4efLzvDq-KNY5LqQKX5SLCUfLz5bDU08WqfqZtnfww");
                }
            };
            String serviceUrl ="https://innovapi.cld.samsclub.com/SamsClubItemList?$select=item_nbr,%20upc_nbr&$filter=club_nbr%20eq%204969%20and%20Item_status_code%20eq%20%27L%27";

            HttpClient client = HttpClients.createDefault();

            HttpGet httpget = new HttpGet(serviceUrl);
            headerMap.forEach((key, value) -> httpget.addHeader(key, value));

            HttpResponse response =client.execute(httpget);

            String body = EntityUtils.toString(response.getEntity());
            LoadLiquidationItemsResponse result= new ObjectMapper().readValue(body, LoadLiquidationItemsResponse.class);

            result.getValue().stream().forEach(item->{
                HSSFRow row2 = sheet.createRow(index.incrementAndGet());

                HSSFCell itemCell = row2.createCell(0);

                HSSFCell upcCell = row2.createCell(1);

                itemCell.setCellValue(item.getItemNbr());
                upcCell.setCellValue(item.getUpcNbr());

            });
            if(StringUtils.isNotBlank(result.getNextLink())){
                getAndSave(result.getNextLink(),client,sheet,index);
//                LoadLiquidationItemsResponse rsp = httpClient.invokeGetRequest(result.getNextLink(), null, null, respHandler);
//                processLStatusItemAndSaveCatItem(rsp,clubNbr);
            }

            File file = new File("C:/dajian/abcd.xls");
            if(!file.exists()){
                file.createNewFile();
            }
            //保存Excel文件
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
            in.close();
            System.out.println("end");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void getAndSave(String getUrl, HttpClient  client,HSSFSheet sheet,AtomicInteger index ){
        LoadLiquidationItemsResponseHandler respHandler = new LoadLiquidationItemsResponseHandler();
        System.out.println("getUrl:"+getUrl);
        HttpGet httpget = new HttpGet(getUrl);
        try {
            HttpResponse response =client.execute(httpget);
            String body = EntityUtils.toString(response.getEntity());
            LoadLiquidationItemsResponse result= new ObjectMapper().readValue(body, LoadLiquidationItemsResponse.class);

            result.getValue().stream().forEach(item->{
                HSSFRow row2 = sheet.createRow(index.incrementAndGet());

                HSSFCell itemCell = row2.createCell(0);

                HSSFCell upcCell = row2.createCell(1);

                itemCell.setCellValue(item.getItemNbr());
                upcCell.setCellValue(item.getUpcNbr());
            });


            if(StringUtils.isNotBlank(result.getNextLink())){
                getAndSave(result.getNextLink(),client,sheet,index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
*/
