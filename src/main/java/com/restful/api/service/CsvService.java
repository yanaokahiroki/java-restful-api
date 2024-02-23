package com.restful.api.service;

import com.restful.api.configuration.ContentTypeConfig;
import com.restful.api.dto.ProductDto;
import com.restful.api.entity.Product;
import com.restful.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class CsvService {
  private final ProductRepository productRepository;
  private final ContentTypeConfig contentTypeConfig;
  private final ProductService productService;
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
    return contentTypeConfig.getContentType().equals(file.getContentType());
  }

  /**
   * リクエストされたCSVファイルの中身を1行ずつ読み込んで、 1行を1Productインスタンスとして生成する。
   *
   * @param inputStream CSVファイルのStream
   * @return ProductのList
   */
  private List<Product> convertCsvToList(InputStream inputStream) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
        CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)) {
      List<Product> productList = new ArrayList<>();
      Iterable<CSVRecord> csvRecordList = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecordList) {
        productList.add(
            Product.builder()
                .id(Integer.parseInt(csvRecord.get("id")))
                .title(csvRecord.get("title"))
                .body(csvRecord.get("body"))
                .price(Integer.parseInt(csvRecord.get("price")))
                .build());
      }
      return productList;
    } catch (IOException | NumberFormatException exception) {
      throw new IllegalArgumentException("CSVファイルの解析に失敗しました。");
    }
  }

  /** DBから取得したレコードをリストにして別メソッドに渡す */
  public ByteArrayInputStream load() {
    List<ProductDto> productList = productService.getAllProduct();
    return convertListToCsv(productList);
  }

  /**
   * 商品情報リストをCSV形式にする
   *
   * @param productList 商品情報リスト
   */
  private ByteArrayInputStream convertListToCsv(List<ProductDto> productList) {
    CSVFormat format = CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL).build();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), format)) {
      for (ProductDto product : productList) {
        List<String> list =
            Arrays.asList(
                String.valueOf(product.getId()),
                product.getTitle(),
                product.getBody(),
                String.valueOf(product.getPrice()),
                product.getCreatedAt().format(formatter),
                product.getUpdatedAt().format(formatter));
        csvPrinter.printRecord(list);
      }
      csvPrinter.flush();
      return new ByteArrayInputStream(outputStream.toByteArray());
    } catch (IOException exception) {
      throw new IllegalArgumentException("失敗しました。");
    }
  }
}
