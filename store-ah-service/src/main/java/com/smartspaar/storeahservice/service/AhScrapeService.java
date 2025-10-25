package com.smartspaar.storeahservice.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.smartspaar.model.ProductDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AH scrape service using Playwright and Jsoup.
 */
@Service
public class AhScrapeService {

    private static final Pattern PRICE_REGEX = Pattern.compile("€\\s*(\\d+[\\.,]\\d{2})");

    public List<ProductDto> search(String query) {
        List<ProductDto> list = new ArrayList<>();
        String url = "https://www.ah.nl/zoeken?query=" + query.replace(" ", "%20");

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE); // صبر تا لود کامل صفحه

            String html = page.content();
            System.out.println("Rendered HTML:\n" + html); // لاگ برای بررسی

            Document doc = Jsoup.parse(html);
            Elements cards = doc.select("div[class*=product-card-portrait]");
            System.out.println("Found cards: " + cards.size()); // لاگ تعداد محصولات

            for (Element card : cards) {
                String name = textFirst(card, "span[class*=title]");
                String priceText = textFirst(card, "span[class*=price], div[class*=price]");
                Double price = parsePrice(priceText);

                if (name.isBlank() || price == null) continue;

                String quantity = findQuantity(card.text());
                String brand = findBrand(name);

                list.add(new ProductDto(
                        name.trim(),
                        brand,
                        quantity,
                        "pack",
                        price,
                        null,
                        "AH"
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private String textFirst(Element e, String css) {
        Element t = e.selectFirst(css);
        return t != null ? t.text() : "";
    }

    private Double parsePrice(String text) {
        Matcher m = PRICE_REGEX.matcher(text);
        if (m.find()) return Double.parseDouble(m.group(1).replace(",", "."));
        return null;
    }

    private String findQuantity(String text) {
        Matcher m = Pattern.compile("(\\d+)\\s*(stuks|stuk|x)", Pattern.CASE_INSENSITIVE).matcher(text);
        if (m.find()) return m.group(1) + " " + m.group(2);
        return "Unknown";
    }

    private String findBrand(String name) {
        if (name.toLowerCase().startsWith("ah")) return "AH";
        return "Unknown";
    }
}
