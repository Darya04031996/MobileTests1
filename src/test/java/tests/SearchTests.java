package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

public class SearchTests extends TestBase{
    @Test
    @DisplayName("Добавление статьи в 'Сохраненное' и проверка наличия в списке")
    void saveArticleAndCheckSavedListTest() {
        step("Открываем поиск", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("BrowserStack");
        });

        step("Открываем первую найденную статью", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title")).first().click()
        );

        step("Добавляем статью в 'Сохраненное'", () -> {
            $(id("org.wikipedia.alpha:id/article_menu_bookmark")).click();
        });

        step("Открываем список сохраненных статей", () -> {
            $(accessibilityId("Saved")).click();
        });

        step("Проверяем, что статья есть в списке", () -> {
            $$(id("org.wikipedia.alpha:id/item_title")).shouldHave(sizeGreaterThan(0));
        });
    }
    @Test
    @DisplayName("Проверка поиска без результатов")
    void emptySearchResultsTest() {
        step("Поиск по несуществующему запросу", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("wegaergqergqergqer");
        });

        step("Проверяем, что нет найденных статей", () ->
                $(id("org.wikipedia.alpha:id/page_list_item_description")).shouldHave(text("No results")));

    }
    @Test
    @DisplayName("Открытие первой найденной статьи и проверка заголовка")
    void openFirstArticleAndCheckTitleTest() {
        step("Выполняем поиск", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Selenium");
        });

        step("Открываем первую найденную статью", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title")).first().click()
        );

        step("Проверяем, что заголовок статьи содержит нужное слово", () ->
                $(className("android.widget.TextView")).shouldHave(text("Selenium"))
        );
    }

}
