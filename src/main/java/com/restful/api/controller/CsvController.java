package com.restful.api.controller;

import com.restful.api.configuration.ContentTypeConfig;
import com.restful.api.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CSVファイル関連コントローラー
 *
 * @author yanaokahiroki
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/csv")
public class CsvController {
  private final CsvService csvService;
  private final ContentTypeConfig contentTypeConfig;

  /**
   * CSVファイルをリクエスト時に送信することで直接DBへ保存する
   *
   * @param multipartFile CSVファイル
   * @throws HttpMediaTypeNotSupportedException CSVファイル以外をパラメータに指定した場合の例外
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile multipartFile)
      throws HttpMediaTypeNotSupportedException {
    String message;
    if (!csvService.isCsvFormat(multipartFile)) {
      message = "CSVファイルをアップロードしてください。";
      throw new HttpMediaTypeNotSupportedException(message);
    }
    csvService.registerProductFromCsv(multipartFile);
    message = "完了";
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  /**
   * CSVファイルを現在日でダウンロードする
   *
   * @return CSVファイル
   */
  @GetMapping("/download")
  public ResponseEntity<Resource> downloadCsvFile() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String fileName = LocalDateTime.now().format(formatter) + contentTypeConfig.getExtension();
    InputStreamResource file = new InputStreamResource(csvService.load());
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
            .contentType(MediaType.parseMediaType(contentTypeConfig.getContentType()))
            .body(file);
  }
}
