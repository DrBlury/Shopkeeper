# **SHOPKEEPER**
  Shopkeeper is a multi-platform application for managing sales and allows multiple carts.  
  It can be run on all devices capable of running a JVM and a web browser. 

The software allows you to load custom Excel documents (.xlsx) and parse the products found into POJOs (Plain Old Java Objects) which then get dumped into an H2 in memory database which makes working with a few hundreds or even thousands of products **easy** and extremely **fast**.

There is also a **VueJS/Vuetify** based simple front-end which should allow you to search through your products, see the stock and also add them to your cart.
At the end of the day you are also able to print invoices or other kinds of documents written in **LaTeX**.

## **Features**

- Easy import of Excel sheets with product data
- Fast and easy to use VueJS front-end
- Carting system with multiple carts
- Custom documents written in LaTeX
- Multi-platform application
- REST API easily extensible
- Loads products into H2 in memory database for speed

## __**TODO**__
- Add a switch cart list with carts that are already stored at the backend
- Allow switching of carts
- Create LaTeX reader/compiler in the project with some templates
- Create customer form
- Create customer database for recurring customers
- Allow product images to be shown in the product list
- Dynamically load images in the list
- Refactor front-end code (split carting system from productlist)
- Add a Vuex Store to store the product data in the frontend after the first loading
- Add a reload from excel button to allow changes to the Excel sheet "on the fly"
- Add a site that allows you to create new products with image upload
