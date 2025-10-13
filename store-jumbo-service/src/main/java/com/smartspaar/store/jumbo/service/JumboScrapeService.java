package com.smartspaar.store.jumbo.service;

import com.smartspaar.model.ProductDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * Jumbo scrape service.
 * It fetches products by query using Jsoup.
 */
@Service
public class JumboScrapeService {

    private static final Pattern PRICE_REGEX = Pattern.compile("€\\s*(\\d+[\\.,]\\d{2})");

    /**
     * Search products in Jumbo.
     * @param query search text
     * @return product list
     */
    public List<ProductDto> search(String query) throws Exception {
        String url = "https://www.jumbo.com/producten/?searchType=keyword&searchTerms=" + encode(query);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome Safari")
                .timeout(15000)
                .get();

        // Generic selectors (adjust with browser Inspect if needed)
        Elements cards = doc.select("article, div[class*='product'], li[class*='product']");
        List<ProductDto> list = new ArrayList<>();

        int limit = Math.min(15, cards.size());
        for (int i = 0; i < limit; i++) {
            Element c = cards.get(i);
            String name = textFirst(c, "h3, h2, .title, [class*='title']");
            if (name.isBlank()) {
                name = firstLine(c.text());
            }
            Double price = parsePrice(c.text());
            // Fallback: if no price found, skip
            if (name.isBlank() || price == null) continue;

            // Try to guess quantity/brand from text
            String quantity = findQuantity(c.text());
            String brand = findBrand(name);

            list.add(new ProductDto(
                    name.trim(),
                    brand,
                    quantity,
                    "pack",
                    price,
                    null,
                    "JUMBO"
            ));
        }
        return list;
    }

    private String encode(String q) {
        return q.replace(" ", "%20");
    }

    private String textFirst(Element e, String css) {
        Element t = e.selectFirst(css);
        return t != null ? t.text() : "";
    }

    private String firstLine(String t) {
        String[] lines = t.split("\\r?\\n");
        return lines.length > 0 ? lines[0] : "";
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
        if (name.toLowerCase().startsWith("jumbo")) return "Jumbo";
        return "Unknown";
    }
}