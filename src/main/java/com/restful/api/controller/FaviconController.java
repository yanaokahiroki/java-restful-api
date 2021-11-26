package com.restful.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ブラウザのコンソールに表示されるfaviconがないという404エラーを回避するためのコントローラー
 *
 * @author yanaokahiroki
 */
@Controller
public class FaviconController {

  @GetMapping("favicon.ico")
  @ResponseBody
  public void returnNoFavicon() {
    // faviconがないために表示される404エラーを回避する
  }
}
