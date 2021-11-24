package com.restful.api.service;

import com.restful.api.entity.Product;
import com.restful.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CsvService {
  private static final String TYPE = "text/csv";
  private final ProductRepository productRepository;

  public void registerProductFromCsv(MultipartFile multipartFile) {
    try {
      List<Product> productList = convertCsvToList(multipartFile.getInputStream());
      productRepository.saveAll(productList);
    } catch (IOException e) {
      throw new RuntimeException("CSVファイルからの保存に失敗しました。");
    }
  }

  /**
   * リクエストされたファイルがCSV形式であるか判定
   *
   * @param file ファイル
   * @return "CSV形式ファイルならtrue
   */
  public boolean isCsvFormat(MultipartFile file) {
    return TYPE.equals(file.getContentType());
  }

  /**
   * リクエストされたCSVファイルの中身を1行ずつ読み込んで、 1行を1Productインスタンスとして生成する。
   *
   * @param inputStream CSVファイルのStream
   * @return ProductのList
   */
  private List<Product> convertCsvToList(InputStream inputStream) {
    try (BufferedReader reader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()));
        CSVParser csvParser =
            new CSVParser(
                reader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()); ) {
      List<Product> productList = new ArrayList<Product>();
      Iterable<CSVRecord> csvRecordList = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecordList) {
        Product product =
            new Product(
                Integer.parseInt(csvRecord.get("id")),
                csvRecord.get("title"),
                csvRecord.get("body"),
                Integer.parseInt(csvRecord.get("price")),
                LocalDateTime.parse("2021-11-01T11:11:11"),
                LocalDateTime.parse("2021-11-02T11:11:11"));
        productList.add(product);
      }
      return productList;
    } catch (IOException exception) {
      throw new RuntimeException("CSVファイルの解析に失敗しました。");
    }
  }
}
