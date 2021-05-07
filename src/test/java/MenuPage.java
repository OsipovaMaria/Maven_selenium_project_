import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum MenuPage {

    APPEARANCE("app", "APPEARANCE", "appearance", true),
    APPEARANCE_TEMPLATE("doc", "APPEARANCE", "template", false),
    APPEARANCE_LOGOTYPE("doc", "APPEARANCE", "logotype", false),
    CATALOG("app", "CATALOG", "catalog", true),
    CATALOG_CATALOG("doc", "CATALOG", "catalog", false),
    CATALOG_ATTRIBUTES("doc", "CATALOG", "attribute_groups", false),
    CATALOG_MANUFACTURERS("doc", "CATALOG", "manufacturers", false),
    CATALOG_SUPPLIERS("doc", "CATALOG", "suppliers", false),
    CATALOG_DELIVERY_STATUSES("doc", "CATALOG", "delivery_statuses", false),
    CATALOG_SOLD_OUT_STATUSES("doc", "CATALOG", "sold_out_statuses", false),
    CATALOG_QUANTITY_UNITS("doc", "CATALOG", "quantity_units", false),
    CATALOG_CSV("doc", "CATALOG", "csv", false),
    COUNTRIES("app", "COUNTRIES", "countries", false),
    CURRENCIES("app", "CURRENCIES", "currencies", false),
    CUSTOMERS("app", "CUSTOMERS", "customers", true),
    CUSTOMERS_CUSTOMERS("doc", "CUSTOMERS", "customers", false),
    CUSTOMERS_CSV("doc", "CUSTOMERS", "csv", false),
    CUSTOMERS_NEWSLETTER("doc", "CUSTOMERS", "newsletter", false),
    GEO_ZONES("app", "GEO_ZONES", "geo_zones", false),
    LANGUAGES("app", "LANGUAGES", "languages", true),
    LANGUAGES_LANGUAGES("doc", "LANGUAGES", "languages", false),
    LANGUAGES_STORAGE_ENCODING("doc", "LANGUAGES", "storage_encoding", false),
    MODULES("app", "MODULES", "modules", true),
    MODULES_CUSTOMER_MODULES("doc", "MODULES", "customer", false),
    MODULES_SHIPPING_MODULES("doc", "MODULES", "shipping", false),
    MODULES_PAYMENT_MODULES("doc", "MODULES", "payment", false),
    MODULES_ORDER_MODULES("doc", "MODULES", "order", false),
    MODULES_ORDER_TOTAL_MODULES("doc", "MODULES", "order_total", false),
    MODULES_JOB_MODULES("doc", "MODULES", "jobs", false),
    ORDERS("app", "ORDERS", "orders", true),
    ORDERS_ORDERS("doc", "ORDERS", "orders", false),
    ORDERS_STATUSES("doc", "ORDERS", "order_statuses", false),
    PAGES("app", "PAGES", "pages", true),
    PAGES_PAGES("doc", "PAGES", "pages", false),
    PAGES_CSV("doc", "PAGES", "csv", false),
    REPORTS("app", "REPORTS", "reports", true),
    REPORTS_MONTHLY_SALES("doc", "REPORTS", "monthly_sales", false),
    REPORTS_MOST_SOLD_PRODUCTS("doc", "REPORTS", "most_sold_products", false),
    REPORTS_MOST_SHOPPING_CUSTOMERS("doc", "REPORTS", "most_shopping_customers", false),
    SETTINGS("app", "SETTINGS", "settings", true),
    SETTINGS_STORE_INFO("doc", "SETTINGS", "store_info", false),
    SETTINGS_DEFAULTS("doc", "SETTINGS", "defaults", false),
    SETTINGS_EMAIL("doc", "SETTINGS", "email", false),
    SETTINGS_LISTINGS("doc", "SETTINGS", "listings", false),
    SETTINGS_CUSTOMER_DETAILS("doc", "SETTINGS", "customer_details", false),
    SETTINGS_LEGAL("doc", "SETTINGS", "legal", false),
    SETTINGS_IMAGES("doc", "SETTINGS", "images", false),
    SETTINGS_CHECKOUT("doc", "SETTINGS", "checkout", false),
    SETTINGS_ADVANCED("doc", "SETTINGS", "advanced", false),
    SETTINGS_SECURITY("doc", "SETTINGS", "security", false),
    SLIDES("app", "SLIDES", "slides", false),
    TAX("app", "TAX", "tax", true),
    TAX_RATES("doc", "TAX", "tax_rates", false),
    TAX_CLASSES("doc", "TAX", "tax_classes", false),
    TRANSLATIONS("app", "TRANSLATIONS", "translations", true),
    TRANSLATIONS_SEARCH("doc", "TRANSLATIONS", "search", false),
    TRANSLATIONS_SCAN("doc", "TRANSLATIONS", "scan", false),
    TRANSLATIONS_CSV("doc", "TRANSLATIONS", "csv", false),
    USERS("app", "USERS", "users", false),
    VQMODS("app", "VQMODS", "vqmods", false);

    private final String linkClass;
    private final String menuItem;
    private final String dataCode;
    private final boolean isSubMenuPresent;

    MenuPage(String linkClass, String menuItem, String dataCode, boolean isSubMenuPresent) {
        this.linkClass = linkClass;
        this.menuItem = menuItem;
        this.dataCode = dataCode;
        this.isSubMenuPresent = isSubMenuPresent;
    }

    private static final MenuPage[] menuItems = {APPEARANCE, CATALOG, COUNTRIES, CURRENCIES, CUSTOMERS,
            GEO_ZONES, LANGUAGES, MODULES, ORDERS, PAGES, REPORTS, SETTINGS, SLIDES, TAX, TRANSLATIONS, USERS, VQMODS};

    public static List<MenuPage> menuItemsList() {
        ArrayList<MenuPage> list = new ArrayList<>();
        Collections.addAll(list, menuItems);
        return list;
    }

    public static List<MenuPage> allValuesItemList() {
        ArrayList<MenuPage> list = new ArrayList<>();
        Collections.addAll(list, values());
        return list;
    }

    public String getLinkClass() {
        return this.linkClass;
    }

    public String getMenuItem() {
        return this.menuItem;
    }

    public String getDataCode() {
        return this.dataCode;
    }

    public boolean isSubMenuPresent() {
        return this.isSubMenuPresent;
    }

    public static List<MenuPage> getSubMenuItems(MenuPage menuItem) {
        List<MenuPage> listSubMenu = new ArrayList<>();
        allValuesItemList().forEach(item -> {
                    if (item.getMenuItem().equals(menuItem.getMenuItem()) && !item.getLinkClass().equals("app")) {
                        listSubMenu.add(item);
                    }
                }
        );
        return listSubMenu;
    }

}
