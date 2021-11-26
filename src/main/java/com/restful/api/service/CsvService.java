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
  private final ProductRepository productRepository;
  private static final String TYPE = "text/csv";
  private static final String UTF_8 = StandardCharsets.UTF_8.name();
  private static final CSVFormat CSV_FORMAT =
      CSVFormat.DEFAULT
          .builder()
          .setHeader()
          .setSkipHeaderRecord(true)
          .setIgnoreHeaderCase(true)
          .setTrim(true)
          .build();

  /**
   * CSVファイルから商品情報を読み込みDBへ保存する
   *
   * @param multipartFile CSVファイル
   */
  public void registerProductFromCsv(MultipartFile multipartFile) {
    try {
      List<Product> productList = convertCsvToList(multipartFile.getInputStream());
      productRepository.saveAll(productList);
    } catch (IOException e) {
      throw new IllegalArgumentException("CSVファイルからの保存に失敗しました。");
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
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
         CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)
    ) {
      List<Product> productList = new ArrayList<>();
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
      throw new IllegalArgumentException("CSVファイルの解析に失敗しました。");
    }
  }
}
