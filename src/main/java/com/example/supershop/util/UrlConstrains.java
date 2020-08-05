package com.example.supershop.util;

public final class UrlConstrains {

    private UrlConstrains() { }

    private static final String  API = "/api";
    private static  final String  VERSION = "/v1";
    public static class AddressManagement{
        private AddressManagement(){}
        public static final String  ROOT = API+VERSION+"/address";
        public static final String  CREATE ="/create";
        public static final String FIND_BY_ID ="/{id}";
        public static final String UPDATE= "/{id}";
        public static final String ALL = "/addresses";
        public static final String PAGE ="/page";
        public static final String DELETE = "/{id}";
    }
    public static class ProductManagement{
        private ProductManagement() { }

        public static final String  ROOT = API+VERSION+"/product";
        public static final String  CREATE ="/create";
        public static final String FIND_BY_ID ="/{id}";
        public static final String FIND_BY_Name ="/{productName}";
        public static final String UPDATE= "/{id}";
        public static final String ALL = "/products";
        public static final String CATEGORY ="/category";
        public static final String DELETE = "/{id}";
    }
    public static class SalesManagement {
        public static final String FIND_BY_ID ="/{invoiceId}" ;

        private SalesManagement() { }
        public static final String  ROOT = API+VERSION+"/sale/product";
        public static final String CREATE_INVOICE="/";
    }
    public static class ProviderManagement{
        private ProviderManagement() { }
        public static final String ROOT= API+VERSION+"/provider";
        public static final String  CREATE ="/create";
        public static final String FIND_BY_ID ="/{providerId}";
        public static final String FINDBY_Name ="/{providerName}";
        public static final String UPDATE= "/{providerId}";
        public static final String ALL = "/providers";
        public static  final  String Provider_Invoices = "/{providerId}/invoices";
    }
    public static  class InvoiceManagement{
        public static final String FIND_BY_SHOP_ID = "shop/{shopId}" ;
        public static final String DELETE_INVOICE_LINE_ITEM="/{invoiceId}/lineItem/{lineItemId}";

        private InvoiceManagement(){}
        public static final  String ROOT = API+VERSION+"/invoice";
        public static  final  String FIND_BY_ID= "/{invoiceId}";
        public static final String UPDATE= "/{invoiceId}";

    }

    public static class WarehouseManagement {
        public static final String FIND_BY_NAME = "name/{name}";

        private WarehouseManagement() { }
        public static final String  ROOT = API+VERSION+"/warehouse";
        public static final String  CREATE ="";
        public static final String FIND_BY_ID ="/{id}";
        public static final String UPDATE= "/{id}";
        public static final String ALL_BY_PAGE = "/warehouses";
        public static final String DELETE = "/{id}";
        public static final String ADD_PRODUCT = "/product";

    }

    public static class WarehouseStockManagement {
        private WarehouseStockManagement() { }
        public static final String  ROOT = API+VERSION+"/warehouse-stock";
        public static final String  CREATE ="";
        public static final String FIND_BY_ID ="/{id}";
        public static final String FIND_BY_WAREHOUSE_ID_AND_PRODUCT_ID ="/{warehouseId}/product/{productId}";
        public static final String UPDATE= "/{id}";
        public static final String ALL_BY_PAGE = "/stocks/{warehouseId}";
        public static final String DELETE = "/{id}";
    }

    public static class CategoryManagement {
        private CategoryManagement() { }
        public static final String  ROOT = API+VERSION+"/category";
        public static final String  CREATE ="";
        public static final String FIND_BY_ID ="/{id}";
        public static final String FIND_BY_NAME ="/{name}";
         public static final String UPDATE= "/{id}";
        public static final String ALL_BY_PAGE = "/categories";
        public static final String DELETE = "/{id}";

    }

    public static class EmployeeManagement {
        private EmployeeManagement() { }
        public static final String  ROOT = API+VERSION+"/employee";
        public static final String  CREATE ="";
        public static final String FIND_BY_ID ="/{id}";
        public static final String FIND_BY_NAME ="/{name}";
        public static final String UPDATE= "/{id}";
        public static final String ALL_BY_PAGE = "/employees";
        public static final String DELETE = "/{id}";
    }
}
